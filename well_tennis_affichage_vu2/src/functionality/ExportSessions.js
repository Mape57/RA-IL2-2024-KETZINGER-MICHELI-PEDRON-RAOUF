import * as XLSX from "xlsx";

class ExportSessions {
    static getDayLabel(dayNumber) {
        const dayMapping = {
            1: "Lundi",
            2: "Mardi",
            3: "Mercredi",
            4: "Jeudi",
            5: "Vendredi",
            6: "Samedi",
            7: "Dimanche"
        };
        return dayMapping[dayNumber] || "Inconnu";
    }

    static formatSession(session) {
        const courtName = session.idCourt?.name || "Terrain inconnu";
        const trainer = session.idTrainer || {};
        const trainerFullName = `${trainer.surname || ""} ${trainer.name || ""}`.trim() || "N/A";

        const playersFormatted = Array.isArray(session.players)
            ? session.players.map(p => `${p.surname || ""} ${p.name || ""}`.trim()).join(", ")
            : "N/A";

        return {
            "Terrain": courtName,
            "Jour": this.getDayLabel(session.dayWeek),
            "Début": session.start,
            "Fin": session.stop,
            "Entraîneur": trainerFullName,
            "Joueurs": playersFormatted
        };
    }

    static async export(sessions = []) {
        try {
            if (!Array.isArray(sessions) || sessions.length === 0) {
                console.warn("Aucune session à exporter.");
                return;
            }

            const sessionsByCourt = {};

            // Regrouper les sessions par terrain
            sessions.forEach((session) => {
                const courtName = session.idCourt?.name || "Terrain inconnu";
                if (!sessionsByCourt[courtName]) {
                    sessionsByCourt[courtName] = [];
                }
                sessionsByCourt[courtName].push(session);
            });

            const workbook = XLSX.utils.book_new();

            // Tri alphabétique des noms de terrains
            const sortedCourtNames = Object.keys(sessionsByCourt).sort((a, b) =>
                a.localeCompare(b, 'fr', { sensitivity: 'base' })
            );

            for (const courtName of sortedCourtNames) {
                const rawSessions = sessionsByCourt[courtName];

                // Trier les sessions par jour, puis heure
                const sortedSessions = rawSessions.sort((a, b) => {
                    if (a.dayWeek === b.dayWeek) {
                        return a.start.localeCompare(b.start);
                    }
                    return a.dayWeek - b.dayWeek;
                });

                const formattedSessions = sortedSessions.map(this.formatSession.bind(this));
                const sheet = XLSX.utils.json_to_sheet(formattedSessions);
                const sheetName = courtName.length > 31 ? courtName.substring(0, 28) + "..." : courtName;

                XLSX.utils.book_append_sheet(workbook, sheet, sheetName);
            }

            XLSX.writeFile(workbook, "sessions_par_terrain.xlsx");
        } catch (error) {
            console.error("Erreur lors de l'exportation des sessions :", error);
        }
    }
}

export default ExportSessions;
