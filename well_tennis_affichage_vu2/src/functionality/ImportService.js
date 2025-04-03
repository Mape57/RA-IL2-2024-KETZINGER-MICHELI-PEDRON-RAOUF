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

                    // 1. Extraction des données
                    const { terrains, errors: terrainErrors } = ImportService.parseTerrains(workbook);
                    const sessions = ImportService.parseSessionConstraints(workbook);
                    const players = ImportService.parsePlayers(workbook);
                    const { trainers, errors: trainerErrors } = ImportService.parseTrainers(workbook);

                    // 2. Si des erreurs critiques sont présentes, on bloque tout
                    const totalErrors = [...terrainErrors, ...trainerErrors];

                    if (totalErrors.length > 0) {
                        reject(new Error("Des erreurs ont été détectées dans le fichier :\n\n" + totalErrors.join("\n")));
                        return;
                    }

                    // 3. Aucun problème → on sauvegarde toutes les données
                    if (terrains.length > 0) {
                        await ImportService.saveTerrainsToAPI(terrains);
                    }

                    if (sessions.length > 0) {
                        await ImportService.saveSessionConstraintsToAPI(sessions);
                    }

                    if (players.length > 0) {
                        console.log("Joueurs extraits :", players); // 🔍 Debug ici
                        await ImportService.savePlayersToAPI(players);
                    } else {
                        console.warn("Aucun joueur trouvé, mais l'import continue.");
                    }


                    if (trainers.length > 0) {
                        await ImportService.saveTrainersToAPI(trainers);
                    }

                    if (totalErrors.length > 0) {
                        reject(new Error("Des erreurs ont été détectées dans le fichier :\n\n" + totalErrors.join("\n")));
                        return;
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
        if (!sheet) {
            console.warn("Feuille 'Joueurs' introuvable.");
            return [];
        }

        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        if (rawData.length < 3) {
            console.warn("Pas assez de lignes dans la feuille 'Joueurs'.");
            return [];
        }

        const headers = rawData[0];
        if (!headers.includes("Nom") || !headers.includes("Prénom")) {
            console.warn("Les colonnes 'Nom' et 'Prénom' sont obligatoires dans la feuille 'Joueurs'.");
            return [];
        }

        const dataRows = rawData.slice(1);

        return dataRows
            .filter(row => row[headers.indexOf("Nom")] && row[headers.indexOf("Prénom")])
            .map((row) => {
                const email = row[headers.indexOf("Email")];
                const validEmail = ImportService.validateEmail(email);
                const phone = row[headers.indexOf("Numero 1")] || "";
                const phone2 = row[headers.indexOf("Numero 2")] || "";
                const photoRaw = row[headers.indexOf("Photo")];
                const hasPhoto = (photoRaw || "").toString().trim().toLowerCase() === "oui";

                return {
                    name: row[headers.indexOf("Nom")] || "Inconnu",
                    surname: row[headers.indexOf("Prénom")] || "Inconnu",
                    birthday: ImportService.formatDate(row[headers.indexOf("Date de naissance")]) || "0000-00-00",
                    email: validEmail ? email : `default_${row[0] || "unknown"}@example.com`,
                    level: parseInt(row[headers.indexOf("Niveau")] || "0", 10),
                    courses: parseInt(row[headers.indexOf("Cours par semaine")] || "0", 10),
                    phone,
                    phone2: phone2 || undefined,
                    photo: hasPhoto,
                    validate: true,
                    disponibilities: ImportService.parseDisponibilities(row, headers),
                };
            })
            .filter(player => player.name !== "Inconnu" && player.surname !== "Inconnu");

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
            if (!nom || !prenom) {
                errors.push(`Ligne ${i + 2} : Nom ou prénom manquant.`);
                return null;
            }

            return {
                name: nom,
                surname: prenom,
                infLevel: parseInt(get("Niveau Min") || 0, 10),
                supLevel: parseInt(get("Niveau Max") || 0, 10),
                infAge: parseInt(get("Âge Min") || 0, 10),
                supAge: parseInt(get("Âge Max") || 0, 10),
                minWeeklyMinutes: parseInt(get("Minutes Hebdo Min") || 0, 10),
                maxWeeklyMinutes: parseInt(get("Minutes Hebdo Max") || 0, 10),
                email: get("Email") || "",
                isPartTime: (get("Vacataire") || "").toString().toLowerCase() === "oui",
                isAdmin: (get("Admin") || "").toString().toLowerCase() === "oui",
            };
        }).filter(Boolean);

        return { trainers, errors };
    }



    static parseDisponibilities(row, headers) {
        const dayMapping = {
            "Lundi": 1,
            "Mardi": 2,
            "Mercredi": 3,
            "Jeudi": 4,
            "Vendredi": 5,
            "Samedi": 6,
            "Dimanche": 7
        };

        return Object.entries(dayMapping).flatMap(([day, dayIndex]) => {
            const colIndex = headers.indexOf(day);
            if (colIndex !== -1 && row[colIndex]) {
                return row[colIndex].toString().split(",").map((timeRange) => {
                    const [start, stop] = timeRange.trim().split("-").map(t => t.trim());
                    const open = ImportService.formatHour(start);
                    const close = ImportService.formatHour(stop);
                    if (!ImportService.isValidSlot(open) || !ImportService.isValidSlot(close)) {
                        console.warn(`Disponibilité invalide : ${day} -> "${timeRange}" ignorée`);
                        return null;
                    }
                    return {
                        dayWeek: dayIndex,
                        open,
                        close,
                    };
                }).filter(Boolean);
            }
            return [];
        });
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
                ageDiff: getValue(row, "Diff. d'âge m", 0),
                levelDiff: getValue(row, "Diff. de niveau", 0),
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

        const regexDate = /^(\d{2})[\/-](\d{2})[\/-](\d{4})$/;
        const match = date.match(regexDate);

        return match ? `${match[3]}-${match[2]}-${match[1]}` : null;
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
