import * as XLSX from "xlsx";

class ExportService {
    static computeAge(birthday) {
        if (!birthday) return null;
        const birthDate = new Date(birthday);
        const today = new Date();
        return today.getFullYear() - birthDate.getFullYear();
    }

    static async downloadCSV(players, trainers, terrains = [], constraints = []) {
        try {
            const dayMapping = {
                1: "Lundi",
                2: "Mardi",
                3: "Mercredi",
                4: "Jeudi",
                5: "Vendredi",
                6: "Samedi",
                7: "Dimanche"
            };

            const formattedPlayers = players.map((player) => {
                const availability = {
                    Lundi: "",
                    Mardi: "",
                    Mercredi: "",
                    Jeudi: "",
                    Vendredi: "",
                    Samedi: "",
                };

                if (player.disponibilities && player.disponibilities.length > 0) {
                    player.disponibilities.forEach((d) => {
                        const day = dayMapping[d.dayWeek];
                        if (day && availability[day] !== undefined) {
                            availability[day] += availability[day]
                                ? `, ${d.open} - ${d.close}`
                                : `${d.open} - ${d.close}`;
                        }
                    });
                }

                return {
                    Nom: player.name || "N/A",
                    Prénom: player.surname || "N/A",
                    "Date de naissance": player.birthday || "N/A",
                    Âge: ExportService.computeAge(player.birthday) || "N/A",
                    Email: player.email || "N/A",
                    "Numero 1": player.phone || "Non renseigné",
                    "Numero 2": player.phone2 || "Non renseigné",
                    Niveau: (player.level >= 0 && player.level <= 50) ? player.level : "N/A",
                    "Cours par semaine": player.courses || "N/A",
                    Photo: player.photo ? "Oui" : "Non",
                    ...availability,
                };
            });

            const playersSheet = XLSX.utils.json_to_sheet(formattedPlayers);

            const safeValue = (val) => (val !== null && val !== undefined) ? val : "N/A";

            const formattedTrainers = trainers.map((trainer) => {
                const availability = {
                    Lundi: "",
                    Mardi: "",
                    Mercredi: "",
                    Jeudi: "",
                    Vendredi: "",
                    Samedi: "",
                };

                if (trainer.disponibilities && trainer.disponibilities.length > 0) {
                    trainer.disponibilities.forEach((d) => {
                        const day = dayMapping[d.dayWeek];
                        if (day && availability[day] !== undefined) {
                            availability[day] += availability[day]
                                ? `, ${d.open} - ${d.close}`
                                : `${d.open} - ${d.close}`;
                        }
                    });
                }

                return {
                    Nom: trainer.name || "N/A",
                    Prénom: trainer.surname || "N/A",
                    "Niveau Min": (trainer.infLevel >= 0 && trainer.infLevel <= 50) ? trainer.infLevel : "N/A",
                    "Niveau Max": (trainer.supLevel >= 0 && trainer.supLevel <= 50) ? trainer.supLevel : "N/A",
                    "Âge Min": trainer.infAge || "N/A",
                    "Âge Max": trainer.supAge || "N/A",
                    "Minutes Hebdo Min": safeValue(trainer.infWeeklyMinutes),
                    "Minutes Hebdo Max": safeValue(trainer.supWeeklyMinutes),
                    Email: trainer.email || "N/A",
                    "Vacataire": trainer.partTime ? "Oui" : "Non",
                    Admin: trainer.admin ? "Oui" : "Non",
                    ...availability,
                };
            });


            const trainersSheet = XLSX.utils.json_to_sheet(formattedTrainers);

            const formattedTerrains = terrains.length > 0 ? terrains.map((terrain) => {
                const schedule = {};
                terrain.times.forEach((time) => {
                    const day = dayMapping[time.dayWeek];
                    if (day) {
                        schedule[day] = `${time.start} - ${time.stop}`;
                    }
                });
                return {
                    Terrain: terrain.name || "N/A",
                    ...schedule,
                };
            }) : [{ Terrain: "Aucune donnée disponible" }];

            const formattedSessions = constraints.length > 0 ? constraints.map((session) => ({
                "Contraintes": `${session.infAge}-${session.supAge} ans`,
                "Âge Min": session.infAge !== null && session.infAge !== undefined ? session.infAge : "N/A",
                "Âge Max": session.supAge !== null && session.supAge !== undefined ? session.supAge : "N/A",
                "Niveau Min": session.infLevel !== null && session.infLevel !== undefined ? session.infLevel : "N/A",
                "Niveau Max": session.supLevel !== null && session.supLevel !== undefined ? session.supLevel : "N/A",
                "Groupe Min": session.infGroup !== null && session.infGroup !== undefined ? session.infGroup : "N/A",
                "Groupe Max": session.supGroup !== null && session.supGroup !== undefined ? session.supGroup : "N/A",
                "Diff. d'âge max": session.ageDiff !== null && session.ageDiff !== undefined ? session.ageDiff : "N/A",
                "Diff. de niveau max": session.levelDiff !== null && session.levelDiff !== undefined ? session.levelDiff : "N/A",
                "Durée": session.duration ? `${(session.duration / 60).toFixed(1)}h` : "N/A" // Conversion en heures
            })) : [{ "Contraintes": "Aucune donnée disponible" }];


            const terrainsSheet = XLSX.utils.json_to_sheet(formattedTerrains);
            const sessionsSheet = XLSX.utils.json_to_sheet(formattedSessions);

            const workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, playersSheet, "Joueurs");
            XLSX.utils.book_append_sheet(workbook, trainersSheet, "Entraîneurs");
            XLSX.utils.book_append_sheet(workbook, terrainsSheet, "Terrains");
            XLSX.utils.book_append_sheet(workbook, sessionsSheet, "Contraintes de session");

            XLSX.writeFile(workbook, "donnees.xlsx");
        } catch (error) {
            console.error("Erreur lors de l'exportation :", error);
        }
    }
}

export default ExportService;
