import jsPDF from "jspdf";
import "jspdf-autotable";
import sessionsService from "../services/SessionService.js";
import terrainService from "../services/TerrainService.js";

class ExportPdf {
    static async generateSessionsPdf() {
        try {
            // Fetch data
            const [sessionsResponse, terrainsResponse] = await Promise.all([
                sessionsService.getAllSessions(),
                terrainService.getAllTerrain(),
            ]);

            const sessions = sessionsResponse.data;
            const terrains = terrainsResponse.data;

            if (!sessions.length || !terrains.length) {
                console.error("No data available for export.");
                return;
            }

            const doc = new jsPDF("p", "mm", "a4");
            doc.setFont("helvetica");

            terrains.forEach((terrain, index) => {
                if (index !== 0) {
                    doc.addPage();
                }

                // Title
                doc.setFontSize(18);
                doc.setTextColor(34, 85, 34);
                doc.text(`Emploi du Temps - ${terrain.name}`, 105, 15, { align: "center" });

                let currentY = 25;
                const columnWidth = 180;

                // Table Headers
                doc.setFontSize(12);
                doc.setTextColor(0);
                doc.text("Heure", 20, currentY);
                doc.text("Entraîneur", 50, currentY);
                doc.text("Joueurs", 100, currentY);
                currentY += 5;
                doc.line(20, currentY, 190, currentY);
                currentY += 5;

                // Get sessions for the terrain
                const terrainSessions = sessions.filter(session => session.idCourt?.id === terrain.id);

                terrainSessions.forEach(session => {
                    const coach = session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : "Aucun entraîneur";
                    const players = session.players.map(p => `${p.name} ${p.surname}`).join(", ");

                    doc.setFontSize(10);
                    doc.setTextColor(0);
                    doc.text(session.start + " - " + session.stop, 20, currentY);
                    doc.text(coach, 50, currentY);

                    // Wrap text for players if too long
                    let splitPlayers = doc.splitTextToSize(players, columnWidth - 80);
                    doc.text(splitPlayers, 100, currentY);
                    currentY += 8;

                    if (currentY > 280) {
                        doc.addPage();
                        currentY = 20;
                    }
                });
            });

            // Save PDF
            doc.save("emploi_du_temps.pdf");
        } catch (error) {
            console.error("Error generating PDF:", error);
        }
    }
}

export default ExportPdf;
