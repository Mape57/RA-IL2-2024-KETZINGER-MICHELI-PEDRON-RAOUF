import jsPDF from "jspdf";
import "jspdf-autotable";
import sessionsService from "../services/SessionService.js";
import terrainService from "../services/TerrainService.js";
import {getGroupAge, getGroupLevel} from "./conversionUtils.js";

class ExportPdf {
    static async generateSessionsPdf() {
        try {
            // Récupération des données
            const [sessionsResponse, terrainsResponse] = await Promise.all([
                sessionsService.getAllSessions(),
                terrainService.getAllTerrain(),
            ]);

            const sessions = sessionsResponse.data;
            const terrains = terrainsResponse.data;

            if (!sessions.length || !terrains.length) {
                console.error("Aucune donnée disponible pour l'export.");
                return;
            }

            // Création du PDF en mode paysage (A4 horizontal)
            const doc = new jsPDF("landscape", "mm", "a4");
            doc.setFont("helvetica");

            const pageWidth = doc.internal.pageSize.width;
            const margin = 8;
            const colWidth = (pageWidth - 2 * margin) / 6;
            const startX = margin;
            const maxY = 190; // Hauteur max avant nouvelle page
            const overflowStartY = 25; // Point de départ des sessions overflow

            // Générer une couleur unique pour chaque entraîneur
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

            terrains.forEach((terrain, index) => {
                if (index !== 0) doc.addPage();

                // Titre principal
                doc.setFontSize(18);
                doc.setTextColor(34, 85, 34);
                doc.text(`Emploi du Temps - ${terrain.name}`, pageWidth / 2, 15, { align: "center" });

                let startY = 25;
                const jours = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"];

                // en-tête des jours
                doc.setFontSize(12);
                doc.setTextColor(0);
                jours.forEach((jour, i) => {
                    const centerX = startX + i * colWidth + colWidth / 2; // Calcul du centre de la colonne
                    doc.text(jour, centerX, startY, { align: "center" });
                });
                doc.setDrawColor(82, 131, 89);
                doc.setLineWidth(0.7);
                doc.line(startX, startY + 2, pageWidth - margin, startY + 2);
                startY += 8;

                // Regrouper les sessions par jour
                const sessionsByDay = sessions
                    .filter(session => session.idCourt?.id === terrain.id)
                    .reduce((acc, session) => {
                        const dayLabel = ExportPdf.getJourLabel(session.dayWeek);
                        if (!acc[dayLabel]) acc[dayLabel] = [];
                        acc[dayLabel].push(session);
                        return acc;
                    }, {});

                Object.keys(sessionsByDay).forEach(day => {
                    sessionsByDay[day].sort((a, b) => a.start.localeCompare(b.start));
                });

                let columnPositions = { ...jours.reduce((acc, jour) => ({ ...acc, [jour]: startY }), {}) };
                let overflowSessions = [];

                // Vérification des dépassements
                jours.forEach((jour, i) => {
                    let tempColumnY = columnPositions[jour];

                    (sessionsByDay[jour] || []).forEach((session) => {
                        const hauteur = calculerHauteurSession(doc, session, colWidth);
                        if (tempColumnY + hauteur > maxY) {
                            overflowSessions.push({ jour, session, index: i });
                        } else {
                            tempColumnY = afficherSession(doc, session, startX + i * colWidth, tempColumnY, colWidth, getCoachColor);
                        }
                    });

                    columnPositions[jour] = tempColumnY;
                });

                // Traitement des sessions en overflow
                if (overflowSessions.length > 0) {
                    doc.addPage();
                    let overflowColumnPositions = { ...jours.reduce((acc, jour) => ({ ...acc, [jour]: overflowStartY }), {}) };

                    // Réaffichage du titre et des en-têtes des jours
                    doc.setFontSize(18);
                    doc.setTextColor(34, 85, 34);
                    doc.text(`Emploi du Temps - ${terrain.name}`, pageWidth / 2, 15, { align: "center" });

                    doc.setFontSize(12);
                    doc.setTextColor(0);
                    jours.forEach((j, idx) => {
                        doc.text(j, startX + idx * colWidth, overflowStartY);
                    });
                    doc.setDrawColor(82, 131, 89);
                    doc.setLineWidth(0.7);
                    doc.line(startX, overflowStartY + 2, pageWidth - margin, overflowStartY + 2);


                    // Position de départ pour chaque colonne
                    jours.forEach(jour => {
                        overflowColumnPositions[jour] += 10;
                    });

                    // Affichage des sessions en overflow
                    overflowSessions.forEach(({ jour, session, index }) => {
                        overflowColumnPositions[jour] = afficherSession(
                            doc, session, startX + index * colWidth, overflowColumnPositions[jour], colWidth, getCoachColor
                        );
                    });
                }
            });

            doc.save("emploi_du_temps.pdf");
        } catch (error) {
            console.error("Erreur lors de la génération du PDF :", error);
        }
    }
    static getJourLabel(dayWeek) {
        const jours = {
            1: "Lundi",
            2: "Mardi",
            3: "Mercredi",
            4: "Jeudi",
            5: "Vendredi",
            6: "Samedi",
        };
        return jours[dayWeek] || "Jour inconnu";
    }
}

function calculerHauteurSession(doc, session, colWidth) {
    let players = session.players.map(p => `${p.name} ${p.surname}`).join(", ");
    const splitPlayers = doc.splitTextToSize(players, colWidth - 2);
    const lineHeight = 3.5;
    const totalHeight = splitPlayers.length * lineHeight;
    return 5 + 5 + 1.5 + totalHeight + 3; // coach + age + ligne + joueurs + padding
}


function afficherSession(doc, session, x, y, colWidth, getCoachColor) {
    const coach = session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : "Aucun entraîneur";
    const ageGroup = session.players ? `${getGroupAge(session.players)} ans` : "Non spécifié";
    const skillLevel = session.players ? `Niveau: ${getGroupLevel(session.players)}` : "Non spécifié";
    const ageLevel = `${ageGroup}, ${skillLevel}`;
    const time = `${session.start} - ${session.stop}`;
    let players = session.players.map(p => `${p.name} ${p.surname}`).join(", ");

    const lineColor = session.idTrainer ? getCoachColor(session.idTrainer.id) : [150, 150, 150];

    doc.setFont("helvetica", "bold");
    doc.setFontSize(8);
    doc.text(coach, x, y);
    doc.text(time, x + colWidth - 1, y, { align: "right" });

    const ageY = y + 5;
    doc.setFont("helvetica", "bold");
    doc.setFontSize(8);
    doc.text(ageLevel, x, ageY);

    const lineY = ageY + 1.5;
    doc.setDrawColor(...lineColor);
    doc.setLineWidth(0.5);
    doc.line(x, lineY, x + colWidth - 1, lineY);

    const playersY = lineY + 5;
    const splitPlayers = doc.splitTextToSize(players, colWidth - 2);
    doc.setFont("helvetica", "normal");
    doc.setFontSize(8);
    doc.text(splitPlayers, x, playersY);

    const lineHeight = 3.5;
    const totalHeight = splitPlayers.length * lineHeight;

    return playersY + totalHeight + 3;

}

export default ExportPdf;
