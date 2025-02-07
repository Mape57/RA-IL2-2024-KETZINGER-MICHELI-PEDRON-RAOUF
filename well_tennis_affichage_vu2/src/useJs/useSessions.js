import { ref } from "vue";
import sessionsService from "../services/SessionService.js";

export default function useSessions() {
    const sessions = ref([]);

    // Récupérer toutes les sessions
    const fetchSessions = async () => {
        try {
            const response = await sessionsService.getAllSessions();
            sessions.value = response.data;
        } catch (error) {
            console.error("Erreur lors de la récupération des sessions :", error);
        }
    };

    // Supprimer une session par ID
    const deleteSession = async (id) => {
        try {
            await sessionsService.deleteSession(id);
            sessions.value = sessions.value.filter((s) => s.id !== id);
        } catch (error) {
            console.error("Erreur lors de la suppression :", error);
        }
    };

    // Mettre à jour une session existante
    const updateSession = async (session) => {
        try {
            console.log("Données envoyées au backend pour mise à jour :", session);
            const response = await sessionsService.updateSession(session.id, session);
            const index = sessions.value.findIndex((s) => s.id === session.id);
            if (index !== -1) {
                sessions.value[index] = response.data;
            }
            console.log("Session mise à jour :", response.data);
            return response.data;
        } catch (error) {
            if (error.response) {
                console.error("Erreur de réponse du backend :", error.response.data);
            } else {
                console.error("Erreur lors de la mise à jour :", error.message);
            }
            throw error;
        }
    };


    // Créer une nouvelle session
    const createSession = async (session) => {
        try {
            const response = await sessionsService.createSession(session);
            sessions.value.push(response.data);
            console.log("Session créée :", response.data);
            return response.data;
        } catch (error) {
            console.error("Erreur lors de la création :", error);
            throw error;
        }
    };

    // Calcul de la durée de la session
    const computeDuration = (start, stop) => {
        if (!start || !stop) return "N/A";

        const startTime = new Date(`1970-01-01T${start}:00`);
        const stopTime = new Date(`1970-01-01T${stop}:00`);

        const durationInMinutes = (stopTime - startTime) / (1000 * 60);

        const hours = Math.floor(durationInMinutes / 60);
        const minutes = durationInMinutes % 60;

        return `${hours}h ${minutes}min`;
    };

    return {
        computeDuration,
        createSession,
        deleteSession,
        fetchSessions,
        sessions,
        updateSession,
    };
}
