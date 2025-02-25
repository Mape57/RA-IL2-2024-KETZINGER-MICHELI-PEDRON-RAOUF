import * as XLSX from "xlsx";

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
                1: "monday",
                2: "tuesday",
                3: "wednesday",
                4: "thursday",
                5: "friday",
                6: "saturday",
                7: "sunday"
            };


            const formattedPlayers = players.map((player) => {
                // Initialisation des disponibilités par défaut pour chaque jour
                const availability = {
                    monday: "",
                    tuesday: "",
                    wednesday: "",
                    thursday: "",
                    friday: "",
                    saturday: "",
                };

                if (player.disponibilities && player.disponibilities.length > 0) {

                    player.disponibilities.forEach((d) => {
                        const day = dayMapping[d.dayWeek];
                        if (day && availability[day] !== undefined) {
                            availability[day] += availability[day]
                                ? `, ${d.open} - ${d.close}`
                                : `${d.open} - ${d.close}`;
                        } else {
                            console.warn("Jour non valide détecté :", d.day);
                        }
                    });


                    console.log("Disponibilités après mapping:", availability);
                } else {
                    console.warn("Aucune disponibilité trouvée pour ce joueur :", player.name);
                }

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

            playersSheet["!merges"] = [
                { s: { r: 0, c: 7 }, e: { r: 0, c: 12 } },
            ];
            playersSheet["H1"] = {
                t: "s",
                v: "Disponibilités",
                s: { alignment: { horizontal: "center", vertical: "center" } },
            };

            const formattedTrainers = trainers.map((trainer) => {
                return {
                    Nom: trainer.name || "N/A",
                    Prénom: trainer.surname || "N/A",
                    "Niveau Min": trainer.infLevel || "N/A",
                    "Niveau Max": trainer.supLevel || "N/A",
                    "Âge Min": trainer.infAge || "N/A",
                    "Âge Max": trainer.supAge || "N/A",
                    "Minutes Hebdo Min": trainer.infWeeklyMinutes || "N/A",
                    "Minutes Hebdo Max": trainer.supWeeklyMinutes || "N/A",
                    Email: trainer.email || "N/A",
                    "Temps partiel": trainer.partTime ? "Oui" : "Non",
                    Admin: trainer.admin ? "Oui" : "Non",
                };
            });

            const dayMapping1 = {
                Monday: "Lundi",
                Tuesday: "Mardi",
                Wednesday: "Mercredi",
                Thursday: "Jeudi",
                Friday: "Vendredi",
                Saturday: "Samedi",
                Sunday: "Dimanche",
            };

            const formattedTerrains = terrains.map((terrain) => {
                // Initialisation des horaires par défaut pour chaque jour
                const schedule = {
                    Lundi: "",
                    Mardi: "",
                    Mercredi: "",
                    Jeudi: "",
                    Vendredi: "",
                    Samedi: "",
                    Dimanche: "",
                };

                terrain.times.forEach((time) => {
                    const day = dayMapping1[time.day];
                    if (day) {
                        schedule[day] = `${time.start} - ${time.stop}`;
                    }
                });

                return {
                    Terrain: terrain.name || "N/A",
                    ...schedule,
                };
            });


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
