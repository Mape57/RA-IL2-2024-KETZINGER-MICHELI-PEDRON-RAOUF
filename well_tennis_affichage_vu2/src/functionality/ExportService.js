import * as XLSX from "xlsx";
import { toRaw } from "vue";

class ExportService {
    static computeAge(birthday) {
        if (!birthday) return null;
        const birthDate = new Date(birthday);
        const today = new Date();
        return today.getFullYear() - birthDate.getFullYear();
    }

    static async downloadCSV(players, trainers, terrains, sessions) {
        try {
            const dayMapping = {
                lundi: "monday",
                mardi: "tuesday",
                mercredi: "wednesday",
                jeudi: "thursday",
                vendredi: "friday",
                samedi: "saturday",
            };

            // Préparation des données des joueurs avec leurs disponibilités
            const formattedPlayers = players.map((player) => {
                console.log("Player:", toRaw(player)); // Affiche les données brutes du joueur

                // Initialisation des disponibilités par défaut pour chaque jour
                const availability = {
                    monday: "",
                    tuesday: "",
                    wednesday: "",
                    thursday: "",
                    friday: "",
                    saturday: "",
                };

                // Parcourir les disponibilités et les mapper
                if (player.disponibilities && player.disponibilities.length > 0) {
                    console.log(
                        "Disponibilités avant mapping (brutes):",
                        toRaw(player.disponibilities)
                    ); // Données brutes

                    player.disponibilities.forEach((d) => {
                        const day = dayMapping[d.day.toLowerCase()]; // Transforme le jour en anglais
                        console.log("Day après mapping:", day, "Availability:", d);

                        if (day && availability[day] !== undefined) {
                            availability[day] = `${d.open} - ${d.close}`; // Ajoute la plage horaire
                        } else {
                            console.warn("Jour non valide détecté :", d.day); // Avertit si le jour est absent du mapping
                        }
                    });

                    console.log("Disponibilités après mapping:", availability); // Vérifiez les disponibilités après le mapping
                } else {
                    console.warn("Aucune disponibilité trouvée pour ce joueur :", player.name);
                }

                // Retourner les données formatées pour l'export Excel
                return {
                    Nom: player.name || "N/A",
                    Prénom: player.surname || "N/A",
                    "Date de naissance": player.birthday || "N/A",
                    Âge: ExportService.computeAge(player.birthday) || "N/A",
                    Email: player.email || "N/A",
                    Niveau: player.level || "N/A",
                    "Cours par semaine": player.courses || "N/A",
                    Lundi: availability.monday,
                    Mardi: availability.tuesday,
                    Mercredi: availability.wednesday,
                    Jeudi: availability.thursday,
                    Vendredi: availability.friday,
                    Samedi: availability.saturday,
                };
            });

            // Création de la feuille Excel pour les joueurs
            const playersSheet = XLSX.utils.json_to_sheet(formattedPlayers, { origin: "A2" });

            // Fusion des colonnes pour "Disponibilités" (G1 à M1, en fonction des colonnes des jours de la semaine)
            playersSheet["!merges"] = [
                { s: { r: 0, c: 7 }, e: { r: 0, c: 12 } }, // Fusionne les colonnes G (colonne 6) à M (colonne 12)
            ];
            // Centrer le texte "Disponibilités"
            playersSheet["H1"] = {
                t: "s",
                v: "Disponibilités",
                s: { alignment: { horizontal: "center", vertical: "center" } },
            };

            // Préparation des autres données (entraîneurs, terrains, séances)
            const formattedTrainers = trainers.map((trainer) => {
                const [levelMin, levelMax] = (trainer.levels || "N/A - N/A").split(" - ");
                return {
                    Nom: trainer.name || "N/A",
                    Prénom: trainer.surname || "N/A",
                    "Niveau Min": levelMin || "N/A",
                    "Niveau Max": levelMax || "N/A",
                };
            });

            const formattedTerrains = terrains.map((terrain) => ({
                Terrain: terrain.court_name || "N/A",
                Horaire:
                    terrain.schedule
                        .map((schedule) => `${schedule.day}: ${schedule.open} - ${schedule.close}`)
                        .join(" | ") || "N/A",
            }));

            const formattedSessions = sessions.map((session) => ({
                Titre: session.title || "N/A",
                Âge: session.age || "N/A",
                Effectif: session.effective || "N/A",
                Durée: `${session.duration || 0}h`,
                "Diff. Niveau": session.sessions_level || "N/A",
            }));

            // Création des autres feuilles Excel
            const trainersSheet = XLSX.utils.json_to_sheet(formattedTrainers);
            const terrainsSheet = XLSX.utils.json_to_sheet(formattedTerrains);
            const sessionsSheet = XLSX.utils.json_to_sheet(formattedSessions);

            // Création du classeur Excel
            const workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, playersSheet, "Joueurs");
            XLSX.utils.book_append_sheet(workbook, trainersSheet, "Entraîneurs");
            XLSX.utils.book_append_sheet(workbook, terrainsSheet, "Terrains");
            XLSX.utils.book_append_sheet(workbook, sessionsSheet, "Séances");

            // Exportation du fichier Excel
            XLSX.writeFile(workbook, "donnees.xlsx");
        } catch (error) {
            console.error("Erreur lors de l'exportation :", error);
        }
    }
}

export default ExportService;
