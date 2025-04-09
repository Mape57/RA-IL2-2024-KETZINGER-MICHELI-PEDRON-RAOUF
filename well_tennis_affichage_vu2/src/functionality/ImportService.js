import PlayersService from "../services/PlayersService.js";
import * as XLSX from "xlsx";
import TerrainService from "../services/TerrainService.js";
import SessionConstraintService from "../services/sessionConstraintService.js";
import TrainersService from "../services/TrainersService.js";

class ImportService {
    static errors;
    static async importExcel(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.onload = async (event) => {
                try {
                    const binaryStr = event.target.result;
                    const workbook = XLSX.read(binaryStr, { type: "binary" });

                    const { terrains, errors: terrainErrors } = ImportService.parseTerrains(workbook);
                    const sessions = ImportService.parseSessionConstraints(workbook);
                    const { players, errors: playerErrors } = ImportService.parsePlayers(workbook);
                    const { trainers, errors: trainerErrors } = ImportService.parseTrainers(workbook);

                    // Récupération des erreurs individuelles
                    let totalErrors = [
                        ...terrainErrors,
                        ...playerErrors,
                        ...(trainers.length > 0 ? trainerErrors : [])
                    ];

                    const allEmails = [
                        ...players.map(p => (p.email || "").toLowerCase()),
                        ...trainers.map(t => (t.email || "").toLowerCase())
                    ];

                    const emailCounts = {};
                    const emailDupErrors = [];

                    allEmails.forEach(email => {
                        if (!email) return;
                        emailCounts[email] = (emailCounts[email] || 0) + 1;
                    });

                    Object.entries(emailCounts).forEach(([email, count]) => {
                        if (count > 1) {
                            emailDupErrors.push(`Email dupliqué détecté : ${email}`);
                        }
                    });

                    totalErrors = [...totalErrors, ...emailDupErrors];


                    if (totalErrors.length > 0) {
                        return reject(new Error([
                            "Des erreurs ont été détectées dans le fichier :",
                            ...totalErrors.map(e => `- ${e}`)
                        ].join("\n")));
                    }

                    if (terrains.length > 0) {
                        await ImportService.saveTerrainsToAPI(terrains);
                    }

                    if (sessions.length > 0) {
                        await ImportService.saveSessionConstraintsToAPI(sessions);
                    }

                    if (players.length > 0) {
                        await ImportService.savePlayersToAPI(players);
                    }

                    if (trainers.length > 0) {
                        await ImportService.saveTrainersToAPI(trainers);
                    }

                    resolve({ players, terrains, terrainErrors: [] });

                } catch (error) {
                    reject(new Error(`Erreur lors de l'importation : ${error.message}`));
                }
            };

            reader.onerror = () => {
                reject(new Error("Erreur de lecture du fichier."));
            };

            reader.readAsBinaryString(file);
        });
    }

    static parsePlayers(workbook) {
        const sheetName = "Joueurs";
        const sheet = workbook.Sheets[sheetName];
        const errors = [];

        if (!sheet) {
            errors.push("Feuille 'Joueurs' introuvable.");
            return { players: [], errors };
        }

        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        if (rawData.length < 3) {
            errors.push("Pas assez de lignes dans la feuille 'Joueurs'.");
            return { players: [], errors };
        }

        const headers = rawData[0];
        if (!headers.includes("Nom") || !headers.includes("Prénom")) {
            errors.push("Les colonnes 'Nom' et 'Prénom' sont obligatoires dans la feuille 'Joueurs'.");
            return { players: [], errors };
        }

        const dataRows = rawData.slice(1);
        const players = [];
        const seenKeys = new Set();

        dataRows.forEach((row, i) => {
            const ligne = i + 2;
            const nom = row[headers.indexOf("Nom")];
            const prenom = row[headers.indexOf("Prénom")];

            // Si toute la ligne est vide, on ignore silencieusement
            const isRowEmpty = row.every(cell => cell === undefined || cell.toString().trim() === "");
            if (isRowEmpty) {
                return;
            }

            // Si nom ou prénom manquant
            if (!nom || !prenom) {
                errors.push(`Ligne ${ligne} : nom ou prénom manquant.`);
                return;
            }

            if (!ImportService.isValidName(nom)) {
                errors.push(`Ligne ${ligne} : nom invalide "${nom}". Uniquement lettres, tirets, apostrophes autorisés.`);
            }

            if (!ImportService.isValidName(prenom)) {
                errors.push(`Ligne ${ligne} : prénom invalide "${prenom}". Uniquement lettres, tirets, apostrophes autorisés.`);
            }


            const email = row[headers.indexOf("Email")];
            const validEmail = ImportService.validateEmail(email);
            const phone = ImportService.normalizePhoneNumber(row[headers.indexOf("Numero 1")]);
            const phone2 = ImportService.normalizePhoneNumber(row[headers.indexOf("Numero 2")]);
            const photoRaw = row[headers.indexOf("Photo")];
            let hasPhoto = false;

            if (ImportService.isYes(photoRaw)) {
                hasPhoto = true;
            } else if (ImportService.isNo(photoRaw)) {
                hasPhoto = false;
            } else {
                errors.push(`Ligne ${ligne} : valeur invalide dans la colonne "Photo" (${photoRaw}). Mettre "oui" ou "non".`);
            }



            const rawDate = row[headers.indexOf("Date de naissance")];
            const birthday = ImportService.formatDate(rawDate) || "0000-00-00";


            if (birthday === "0000-00-00") {
                errors.push(`Ligne ${ligne} : date de naissance non reconnue ou au mauvais format`);
            }

            if (birthday !== "0000-00-00") {
                const birthDate = new Date(birthday);
                const currentYear = new Date().getFullYear();
                const minYear = currentYear - 110;

                if (birthDate > new Date()) {
                    errors.push(`Ligne ${ligne} : date de naissance future invalide (${birthday})`);
                } else if (birthDate.getFullYear() < minYear) {
                    errors.push(`Ligne ${ligne} : date de naissance trop ancienne (${birthday}), minimum accepté : ${minYear}`);
                }
            }


            const key = `${nom.toLowerCase()}|${prenom.toLowerCase()}|${(email || "").toLowerCase()}`;
            if (seenKeys.has(key)) {
                errors.push(`Ligne ${ligne} : doublon détecté pour ${nom} ${prenom} (${email})`);
            } else {
                seenKeys.add(key);
            }

            const { disponibilites, errors: dispoErrors } = ImportService.parseDisponibilities(row, headers, i);
            errors.push(...dispoErrors);

            const courses = parseInt(row[headers.indexOf("Cours par semaine")] || "0", 10);
            if (isNaN(courses) || courses < 1) {
                errors.push(`Ligne ${ligne} : le nombre de cours doit être supérieur ou égal à 1`);
            } else if (disponibilites.length < courses) {
                errors.push(`Ligne ${ligne} : ${courses} cours demandés mais seulement ${disponibilites.length} dispo(s).`);
            }

            const level = parseInt(row[headers.indexOf("Niveau")] || "0", 10);
            if (isNaN(level) || level < 0 || level > 30) {
                errors.push(`Ligne ${ligne} : niveau invalide (${row[headers.indexOf("Niveau")]}) - doit être entre 0 et 30`);
            }


            players.push({
                name: nom,
                surname: prenom,
                birthday,
                email: validEmail ? email : `default_${row[0] || "unknown"}@example.com`,
                level,
                courses,
                phone,
                phone2: phone2 || undefined,
                photo: hasPhoto,
                validate: true,
                disponibilities: disponibilites,
            });
        });

        return { players, errors };
    }

    static parseTrainers(workbook) {
        const sheetName = "Entraîneurs";
        const sheet = workbook.Sheets[sheetName];
        if (!sheet) {
            return { trainers: [], errors: ["Feuille 'Entraineurs' introuvable."] };
        }

        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        if (rawData.length < 2) {
            return { trainers: [], errors: ["La feuille 'Entraineurs' est vide."] };
        }

        const headers = rawData[0];
        const dataRows = rawData.slice(1);
        const errors = [];

        const trainers = dataRows.map((row, i) => {
            const get = (label) => row[headers.indexOf(label)];

            const nom = get("Nom");
            const prenom = get("Prénom");
            const ligne = i + 2;

            if (!nom || !prenom) {
                errors.push(`Ligne ${ligne} : nom ou prénom manquant.`);
                return null;
            }

            if (!ImportService.isValidName(nom)) {
                errors.push(`Ligne ${ligne} : nom invalide "${nom}". Uniquement lettres, tirets, apostrophes autorisés.`);
            }

            if (!ImportService.isValidName(prenom)) {
                errors.push(`Ligne ${ligne} : prénom invalide "${prenom}". Uniquement lettres, tirets, apostrophes autorisés.`);
            }

            const infLevel = parseInt(get("Niveau Min") || 0, 10);
            const supLevel = parseInt(get("Niveau Max") || 0, 10);
            if (isNaN(infLevel) || infLevel < 0 || infLevel > 30) {
                errors.push(`Ligne ${ligne} : Niveau Min invalide (${infLevel})`);
            }
            if (isNaN(supLevel) || supLevel < 0 || supLevel > 30) {
                errors.push(`Ligne ${ligne} : Niveau Max invalide (${supLevel})`);
            }

            const cleanNumber = (val) => {
                if (val === undefined || val === null || val === "") return 0;
                return parseInt(val.toString().replace(/\s/g, ""), 10);
            };

            const { disponibilites, errors: dispoErrors } = ImportService.parseDisponibilities(row, headers, i);
            errors.push(...dispoErrors);

            let partTime = false;
            let admin = false;

            const rawPartTime = get("Vacataire");
            if (ImportService.isYes(rawPartTime)) {
                partTime = true;
            } else if (ImportService.isNo(rawPartTime)) {
                partTime = false;
            } else {
                errors.push(`Ligne ${ligne} : valeur invalide pour "Vacataire" (${rawPartTime}). Utilisez "oui" ou "non".`);
            }

            const rawAdmin = get("Admin");
            if (ImportService.isYes(rawAdmin)) {
                admin = true;
            } else if (ImportService.isNo(rawAdmin)) {
                admin = false;
            } else {
                errors.push(`Ligne ${ligne} : valeur invalide pour "Admin" (${rawAdmin}). Utilisez "oui" ou "non".`);
            }
            return {
                name: nom,
                surname: prenom,
                infLevel,
                supLevel,
                infAge: parseInt(get("Âge Min") || 0, 10),
                supAge: parseInt(get("Âge Max") || 0, 10),
                infWeeklyMinutes: cleanNumber(get("Minutes Hebdo Min")),
                supWeeklyMinutes: cleanNumber(get("Minutes Hebdo Max")),
                email: get("Email") || "",
                partTime,
                admin,
                disponibilities: disponibilites,
            };
        }).filter(Boolean);

        return { trainers, errors };
    }

    static parseDisponibilities(row, headers, rowIndex = 0) {
        const dayMapping = {
            "Lundi": 1,
            "Mardi": 2,
            "Mercredi": 3,
            "Jeudi": 4,
            "Vendredi": 5,
            "Samedi": 6,
            "Dimanche": 7
        };

        const errors = [];
        const disponibilites = [];

        const columnLetter = (index) => {
            let col = "";
            while (index >= 0) {
                col = String.fromCharCode((index % 26) + 65) + col;
                index = Math.floor(index / 26) - 1;
            }
            return col;
        };

        const isValidTimeString = (time) => {
            if (!time) return false;
            if (/^\d{1,2}:\d{2}$/.test(time)) {
                const [h, m] = time.split(":").map(Number);
                return h >= 0 && h <= 23 && m >= 0 && m <= 59;
            }
            if (/^\d{1,2}$/.test(time)) {
                const h = parseInt(time);
                return h >= 0 && h <= 23;
            }
            return false;
        };

        const toMinutes = (time) => {
            const [h, m] = time.split(":").map(Number);
            return h * 60 + m;
        };

        Object.entries(dayMapping).forEach(([day, dayIndex]) => {
            const colIndex = headers.indexOf(day);
            if (colIndex !== -1 && row[colIndex]) {
                const slots = row[colIndex].toString().split(",");
                const daySlots = [];

                slots.forEach((timeRange) => {
                    const cell = columnLetter(colIndex) + (rowIndex + 2);
                    const [startRaw, stopRaw] = timeRange.trim().split("-").map(t => t.trim());

                    if (!startRaw || !stopRaw) {
                        errors.push(`Erreur dans ${cell} : horaire incomplet "${timeRange}"`);
                        return;
                    }

                    if (!isValidTimeString(startRaw) || !isValidTimeString(stopRaw)) {
                        errors.push(`Erreur dans ${cell} : horaire invalide "${timeRange}"`);
                        return;
                    }

                    const open = ImportService.formatHour(startRaw);
                    const close = ImportService.formatHour(stopRaw);

                    if (!ImportService.isValidSlot(open) || !ImportService.isValidSlot(close)) {
                        errors.push(`Erreur dans ${cell} : horaire invalide "${timeRange}". Format attendu : HH:00 ou HH:30`);
                        return;
                    }

                    const startMin = toMinutes(open);
                    const endMin = toMinutes(close);

                    const isOverlapping = daySlots.some(({ start, end }) =>
                        Math.max(start, startMin) < Math.min(end, endMin)
                    );

                    if (isOverlapping) {
                        errors.push(`Chevauchement détecté dans ${cell} pour "${open} - ${close}"`);
                        return;
                    }

                    daySlots.push({ start: startMin, end: endMin });

                    disponibilites.push({
                        dayWeek: dayIndex,
                        open,
                        close,
                    });
                });
            }
        });

        return { disponibilites, errors };
    }

    static isValidName(name) {
        return typeof name === "string" && /^[A-Za-zÀ-ÖØ-öø-ÿ\s\-']+$/.test(name.trim());
    }

    static normalizePhoneNumber(number) {
        if (!number) return "";

        let num = number.toString().replace(/\D/g, "");

        if (num.length === 9 && (num.startsWith("6") || num.startsWith("7"))) {
            num = "0" + num;
        }

        if (num.length === 10) {
            return num;
        }

        console.warn(`Numéro invalide ignoré : ${number}`);
        return "";
    }

    static parseTerrains(workbook) {
        const sheetName = "Terrains";
        const sheet = workbook.Sheets[sheetName];
        if (!sheet) {
            return { terrains: [], errors: ["Feuille 'Terrains' introuvable."] };
        }

        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        if (rawData.length < 2) {
            return { terrains: [], errors: ["La feuille 'Terrains' est vide."] };
        }

        const headers = rawData[0];
        const dayMapping = {
            "Lundi": 1, "Mardi": 2, "Mercredi": 3,
            "Jeudi": 4, "Vendredi": 5, "Samedi": 6, "Dimanche": 7
        };

        const terrains = [];
        const errors = [];

        rawData.slice(1).forEach((row, rowIndex) => {
            const name = row[0];
            if (!name || name.toString().trim() === "") {
                errors.push(`Ligne ${rowIndex + 2} ignorée : nom du terrain vide.`);
                return;
            }

            const times = [];

            Object.entries(dayMapping).forEach(([dayName, dayWeek]) => {
                const colIndex = headers.indexOf(dayName);
                if (colIndex !== -1 && row[colIndex]) {
                    const slots = row[colIndex].toString().split(",");

                    slots.forEach((slotRaw) => {
                        const [startRaw, stopRaw] = slotRaw.trim().split("-").map(t => t.trim());
                        const start = ImportService.formatHour(startRaw);
                        const stop = ImportService.formatHour(stopRaw);

                        if (!ImportService.isValidSlot(start) || !ImportService.isValidSlot(stop)) {
                            errors.push(`Erreur horaire dans "${name}" (${dayName}) ligne ${rowIndex + 2} : "${slotRaw}". Format attendu : HH:00 ou HH:30.`);
                            return;
                        }

                        times.push({ dayWeek, start, stop });
                    });
                }
            });
            terrains.push({ name, times });
        });

        const displayErrors = errors.filter(
            (err) => !err.includes("ignorée : nom du terrain vide")
        );

        return { terrains, errors: displayErrors };

    }

    static parseSessionConstraints(workbook) {
        const sheetName = "Contraintes de session";
        const sheet = workbook.Sheets[sheetName];
        if (!sheet) {
            console.warn("Feuille 'Contraintes de session' introuvable.");
            return [];
        }

        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        if (rawData.length < 2) return [];

        const headers = rawData[0];
        const dataRows = rawData.slice(1);

        const getValue = (row, title, fallback = null) => {
            const index = headers.indexOf(title);
            const value = index !== -1 ? row[index] : fallback;
            return value !== undefined && value !== "" ? parseInt(value, 10) : fallback;
        };

        return dataRows.map((row, i) => {
            const constraint = {
                infAge: getValue(row, "Âge Min", 1),
                supAge: getValue(row, "Âge Max", 1),
                infLevel: getValue(row, "Niveau Min", 0),
                supLevel: getValue(row, "Niveau Max", 0),
                infGroup: getValue(row, "Groupe Min", 1),
                supGroup: getValue(row, "Groupe Max", 1),
                ageDiff: getValue(row, "Diff. d'âge max", 0),
                levelDiff: getValue(row, "Diff. de niveau max", 0),
                duration: ImportService.parseDuration(row[headers.indexOf("Durée")])
            };

            const isValid =
                constraint.infAge >= 1 &&
                constraint.supAge >= 1 &&
                constraint.infGroup >= 1 &&
                constraint.supGroup >= 1;

            if (!isValid) {
                console.warn(`Contrainte invalide à la ligne ${i + 2} ignorée`);
                return null;
            }

            return constraint;
        }).filter(Boolean);
    }

    static parseDuration(value) {
        if (!value) return 0;
        const match = value.toString().match(/^(\d+(?:\.\d+)?)h$/i);
        if (!match) return 0;
        return Math.round(parseFloat(match[1]) * 60);
    }

    static formatHour(hour) {
        if (!hour) return "00:00";
        if (/^\d{1,2}$/.test(hour)) return hour.padStart(2, "0") + ":00";
        if (/^\d{1,2}:\d{2}$/.test(hour)) return hour;
        if (/^\d{1,2}-\d{1,2}$/.test(hour)) return hour.split("-").map(h => h.padStart(2, "0") + ":00").join("-");
        return "00:00";
    }

    static formatDate(date) {
        if (!date) return null;

        if (typeof date === "number") {
            const excelDate = new Date((date - 25569) * 86400000);
            return excelDate.toISOString().split("T")[0];
        }

        if (typeof date !== "string") return null;

        if (/^\d{4}-\d{2}-\d{2}$/.test(date)) {
            return date;
        }

        const regexDate = /^(\d{2})[\/-](\d{2})[\/-](\d{4})$/;
        const match = date.match(regexDate);

        return match ? `${match[3]}-${match[2]}-${match[1]}` : null;
    }

    static isYes(value) {
        return typeof value === "string" && /^[oO][uU][iI]$/.test(value.trim());
    }

    static isNo(value) {
        return typeof value === "string" && /^[nN][oO][nN]$/.test(value.trim());
    }


    static isValidSlot(time) {
        // Format attendu : HH:00 ou HH:30
        return /^([01]\d|2[0-3]):(00|30)$/.test(time);
    }

    static validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    static async savePlayersToAPI(players) {
        if (players.length === 0) return;

        for (const player of players) {
            try {
                await PlayersService.createPlayer(player);
                console.log(`Joueur ajouté : ${player.name} ${player.surname}`);
            } catch (error) {
                if (error.response?.status === 409) {
                    console.error(`Le joueur ${player.name} existe déjà.`);
                } else if (error.response?.status === 403) {
                    console.error(`Erreur 403 : Accès refusé pour ${player.name}. Vérifiez les permissions.`);
                } else {
                    console.error(`Erreur lors de l'envoi du joueur ${player.name} :`, error.response?.data || error.message);
                }
            }
        }
    }

    static async saveTrainersToAPI(trainers) {
        if (!trainers.length) return;

        for (const trainer of trainers) {
            try {
                await TrainersService.createTrainer(trainer);
                console.log(`Entraîneur ajouté : ${trainer.name} ${trainer.surname}`);
            } catch (error) {
                console.error(`Erreur pour l'entraîneur ${trainer.name} :`, error.response?.data || error.message);
            }
        }
    }

    static async saveTerrainsToAPI(terrains) {
        if (terrains.length === 0) return;

        for (const terrain of terrains) {
            try {
                await TerrainService.createTerrain(terrain);
            } catch (error) {
                if (error.response?.status === 409) {
                } else {
                    console.error(`Erreur lors de l'envoi du terrain ${terrain.name} :`, error.response?.data || error.message);
                }
            }
        }
    }

    static async saveSessionConstraintsToAPI(sessionConstraints) {
        for (const constraint of sessionConstraints) {
            try {
                await SessionConstraintService.createSessionConstraint(constraint);
            } catch (error) {
                console.error("Erreur lors de l'ajout :", error.response?.data || error.message);
            }
        }
    }

}

export default ImportService;
