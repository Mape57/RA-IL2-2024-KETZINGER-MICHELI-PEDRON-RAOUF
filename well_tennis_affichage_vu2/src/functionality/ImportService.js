import playersService from "../services/PlayersService.js";
import trainersService from "../services/trainersService";
import * as XLSX from "xlsx";

class ImportService {
    static async importExcel(file) {
        try {
            const reader = new FileReader();

            return new Promise((resolve, reject) => {
                reader.onload = (e) => {
                    try {
                        const binaryString = e.target.result;
                        const workbook = XLSX.read(binaryString, { type: "binary" });

                        // Extraction des feuilles
                        const playersSheet = XLSX.utils.sheet_to_json(
                            workbook.Sheets["Joueurs"] || {},
                            { defval: "" }
                        );
                        const trainersSheet = XLSX.utils.sheet_to_json(
                            workbook.Sheets["Entraîneurs"] || {},
                            { defval: "" }
                        );

                        const players = playersSheet.map((row) => {
                            // Traitement des disponibilités par jour
                            const disponibilities = [];
                            ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"].forEach((day) => {
                                if (row[day]) {
                                    const slots = row[day]
                                        .split(",") // Séparer les différentes plages horaires par des virgules
                                        .map((slot) => {
                                            const [open, close] = slot.trim().split("-");
                                            return {
                                                day,
                                                open: this.formatHour(open),
                                                close: this.formatHour(close),
                                            };
                                        });
                                    disponibilities.push(...slots);
                                }
                            });

                            return {
                                name: row["Nom"] || "",
                                surname: row["Prénom"] || "",
                                birthday: this.formatDate(row["Date de naissance"]),
                                email: row["Email"] || "",
                                level: row["Niveau"] || "",
                                courses: row["Cours par semaine"] || "",
                                disponibilities,
                            };
                        });

                        const trainers = trainersSheet.map((row) => ({
                            name: row["Nom"] || "",
                            surname: row["Prénom"] || "",
                            levels: row["Niveau Min - Max"] || "1-10",
                            ages: row["Age Min - Max"] || "1-50", // Par défaut
                            email: row["Email"] || `${row["Nom"].toLowerCase()}.${row["Prénom"].toLowerCase()}@example.com`,
                            status: row["Status"] === "employé" ? "employee" : "temporary",
                            disponibilities: [],
                        }));

                        resolve({ players, trainers });
                    } catch (error) {
                        reject(`Erreur lors de la lecture du fichier : ${error.message}`);
                    }
                };

                reader.onerror = (error) => {
                    reject(`Erreur de lecture du fichier : ${error.message}`);
                };

                reader.readAsBinaryString(file);
            });
        } catch (error) {
            console.error("Erreur lors de l'importation du fichier Excel :", error);
            throw error;
        }
    }

    static async processImport(file) {
        try {
            const { players, trainers } = await this.importExcel(file);

            console.log("Données des joueurs importées :", players);
            console.log("Données des entraîneurs importées :", trainers);

            // Filtrer et ajouter les joueurs
            const validPlayers = players.filter(
                (player) =>
                    player.name &&
                    player.surname &&
                    player.email &&
                    player.level
            );

            for (const player of validPlayers) {
                try {
                    const formattedPlayer = {
                        ...player,
                        birthday: player.birthday || null,
                    };
                    console.log("Envoi au backend du joueur :", formattedPlayer);
                    const response = await playersService.createPlayer(formattedPlayer);
                    console.log("Joueur ajouté :", response.data);
                } catch (error) {
                    console.error("Erreur lors de l'ajout d'un joueur :", error.response?.data || error.message);
                }
            }

            // Filtrer et ajouter les entraîneurs
            const validTrainers = trainers.filter(
                (trainer) => trainer.name && trainer.surname
            );

            for (const trainer of validTrainers) {
                try {
                    const formattedTrainer = {
                        name: trainer.name,
                        surname: trainer.surname,
                        levels: trainer.levels || "1-10",
                        ages: trainer.ages || "10-50",
                        email: trainer.email || `${trainer.name}.${trainer.surname}@example.com`.toLowerCase(),
                        password: trainer.password || "", // Mot de passe par défaut
                        status: trainer.status || "employee",
                        disponibilities: Array.isArray(trainer.disponibilities) ? trainer.disponibilities : [],
                    };

                    console.log("Envoi au backend de l'entraîneur :", formattedTrainer);
                    const response = await trainersService.createTrainer(formattedTrainer);
                    console.log("Entraîneur ajouté :", response.data);
                } catch (error) {
                    console.error("Erreur lors de l'ajout d'un entraîneur :", error.response?.data || error.message);
                }
            }

            alert("Importation terminée avec succès !");
        } catch (error) {
            console.error("Erreur lors du traitement du fichier :", error);
            alert("Une erreur est survenue lors de l'importation.");
        }
    }

    // Fonction pour convertir une heure au format HH:mm
    static formatHour(hour) {
        if (!hour) return null;

        if (hour.includes(":")) {
            const [hh, mm] = hour.split(":");
            return `${hh.padStart(2, "0")}:${mm.padStart(2, "0")}`;
        }

        return `${hour.padStart(2, "0")}:00`;
    }

    static formatDate(date) {
        if (!date) return null;

        if (typeof date === "string") {
            const [day, month, year] = date.split("/");
            if (!day || !month || !year) return null;
            return `${year.padStart(4, "0")}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
        }

        if (typeof date === "number") {
            const excelDate = new Date((date - 25569) * 86400 * 1000);
            return excelDate.toISOString().split("T")[0];
        }

        return null;
    }
}

export default ImportService;