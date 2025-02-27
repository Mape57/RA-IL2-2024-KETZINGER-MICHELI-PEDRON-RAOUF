import PlayersService from "../services/PlayersService.js";
import * as XLSX from "xlsx";

class ImportService {
    static async importExcel(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.onload = async (event) => {
                try {
                    const binaryStr = event.target.result;
                    const workbook = XLSX.read(binaryStr, { type: "binary" });
                    const players = ImportService.parsePlayers(workbook);

                    if (players.length === 0) {
                        return reject(new Error("Aucun joueur valide trouvé dans le fichier."));
                    }

                    await ImportService.savePlayersToAPI(players);
                    alert("Importation réussie !");
                    resolve(players);
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
        if (!workbook.Sheets[sheetName]) return [];

        const rawData = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], { header: 1 });
        if (rawData.length < 3) return [];

        const headers = rawData[1];
        const dataRows = rawData.slice(2);

        return dataRows
            .filter(row => row[headers.indexOf("Nom")] && row[headers.indexOf("Prénom")])
            .map((row) => ({
                name: row[headers.indexOf("Nom")] || "Inconnu",
                surname: row[headers.indexOf("Prénom")] || "Inconnu",
                birthday: ImportService.formatDate(row[headers.indexOf("Date de naissance")]) || "0000-00-00",
                email: ImportService.validateEmail(row[headers.indexOf("Email")]) ? row[headers.indexOf("Email")] : `default_${row[0] || "unknown"}@example.com`,
                level: parseInt(row[headers.indexOf("Niveau")] || "0", 10),
                courses: parseInt(row[headers.indexOf("Cours par semaine")] || "0", 10),
                validate: true,
                disponibilities: ImportService.parseDisponibilities(row, headers),
            }))
            .filter(player => player.name !== "Inconnu" && player.surname !== "Inconnu");
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

        return Object.keys(dayMapping).flatMap((day) => {
            const dayIndex = headers.indexOf(day);
            if (dayIndex !== -1 && row[dayIndex]) {
                return row[dayIndex].split(",").map((timeRange) => {
                    let [open, close] = timeRange.trim().split("-");
                    open = ImportService.formatHour(open);
                    close = ImportService.formatHour(close);
                    return {
                        dayWeek: dayMapping[day],
                        open,
                        close,
                    };
                });
            }
            return [];
        });
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
}

export default ImportService;
