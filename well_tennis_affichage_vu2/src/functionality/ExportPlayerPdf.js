import jsPDF from "jspdf";
import "jspdf-autotable";
import sessionsService from "../services/SessionService.js";

class ExportPlayerPdf {
    static async generatePlayerPdf(player) {
        try {
            const [sessionsResponse] = await Promise.all([
                sessionsService.getAllSessions()
            ]);
            const sessions = sessionsResponse.data;

            // Sessions du joueur uniquement
            const playerSessions = sessions.filter(session =>
                session.players.some(p => p.id === player.id)
            );

            if (!playerSessions.length) {
                alert(`Aucune session trouvée pour ${player.name} ${player.surname}`);
                return;
            }

            const doc = new jsPDF("landscape", "mm", "a4");
            const pageWidth = doc.internal.pageSize.width;
            const margin = 8;
            const colWidth = (pageWidth - 2 * margin) / 6;
            const startX = margin;

            // Grouper par jour
            const jours = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"];
            const getJourLabel = (dayWeek) => {
                const map = {
                    1: "Lundi", 2: "Mardi", 3: "Mercredi", 4: "Jeudi", 5: "Vendredi", 6: "Samedi"
                };
                return map[dayWeek] || "Jour inconnu";
            };

            const coachColors = {};
            const getCoachColor = (coachId) => {
                if (!coachColors[coachId]) {
                    coachColors[coachId] = [
                        Math.floor(Math.random() * 200),
                        Math.floor(Math.random() * 200),
                        Math.floor(Math.random() * 200)
                    ];
                }
                return coachColors[coachId];
            };

            // Titre principal
            doc.setFontSize(18);
            doc.setTextColor(34, 85, 34);
            doc.text(`Emploi du Temps - ${player.name} ${player.surname}`, pageWidth / 2, 15, { align: "center" });

            let startY = 25;

            // En-tête des jours
            doc.setFontSize(12);
            doc.setTextColor(0);
            jours.forEach((jour, i) => {
                const centerX = startX + i * colWidth + colWidth / 2;
                doc.text(jour, centerX, startY, { align: "center" });
            });

            doc.setDrawColor(82, 131, 89);
            doc.setLineWidth(0.7);
            doc.line(startX, startY + 2, pageWidth - margin, startY + 2);
            startY += 8;

            // Sessions du joueur groupées par jour
            const sessionsByDay = {};
            for (const session of playerSessions) {
                const jour = getJourLabel(session.dayWeek);
                if (!sessionsByDay[jour]) sessionsByDay[jour] = [];
                sessionsByDay[jour].push(session);
            }

            const columnPositions = jours.reduce((acc, jour) => {
                acc[jour] = startY;
                return acc;
            }, {});

            jours.forEach((jour, index) => {
                const sessions = sessionsByDay[jour] || [];
                sessions.sort((a, b) => a.start.localeCompare(b.start));

                sessions.forEach((session) => {
                    columnPositions[jour] = afficherSession(
                        doc,
                        session,
                        startX + index * colWidth,
                        columnPositions[jour],
                        colWidth,
                        getCoachColor,
                        player
                    );
                });
            });

            doc.save(`emploi_du_temps_${player.name}_${player.surname}.pdf`);
        } catch (error) {
            console.error("Erreur lors de la génération du PDF :", error);
        }
    }
}

function afficherSession(doc, session, x, y, colWidth, getCoachColor, currentPlayer) {
    const coach = session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : "Aucun entraîneur";
    const time = `${session.start} - ${session.stop}`;
    const terrain = session.idCourt?.name || "Terrain inconnu";

    const otherPlayers = session.players
        .filter(p => p.id !== currentPlayer.id)
        .map(p => `${p.name} ${p.surname}`)
        .join(", ") || "Aucun autre joueur";

    const color = session.idTrainer ? getCoachColor(session.idTrainer.id) : [150, 150, 150];

    // Nom de l'entraîneur + heure
    doc.setFont("helvetica", "bold");
    doc.setFontSize(8);
    doc.text(coach, x, y);
    doc.text(time, x + colWidth - 1, y, { align: "right" });

    // Nom du terrain
    const y2 = y + 5;
    doc.setFont("helvetica", "normal");
    doc.text(`${terrain}`, x, y2);

    // Ligne colorée
    const y3 = y2 + 2;
    doc.setDrawColor(...color);
    doc.setLineWidth(0.5);
    doc.line(x, y3, x + colWidth - 1, y3);

    // Liste des autres joueurs
    const y4 = y3 + 5;
    const splitPlayers = doc.splitTextToSize(otherPlayers, colWidth - 2);
    doc.setFontSize(8);
    doc.text(splitPlayers, x, y4);

    return y4 + splitPlayers.length * 2.5 + 5;
}


export default ExportPlayerPdf;
