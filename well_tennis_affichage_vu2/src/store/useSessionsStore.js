import { defineStore } from "pinia";
import { ref } from "vue";
import sessionsService from "../services/SessionService";

export const useSessionsStore = defineStore("sessions", () => {
	const sessions = ref([]);

	const fetchSessions = async () => {
		try {
			const response = await sessionsService.getAllSessions();
			sessions.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des sessions :", error);
		}
	};

	const refreshSessions = async () => {
		try {
			console.log("Rechargement des sessions...");
			const response = await sessionsService.getAllSessions();
			sessions.value = [...response.data]; // 🔹 Forcer Vue à détecter le changement
		} catch (error) {
			console.error("Erreur lors du rechargement des sessions :", error);
		}
	};

	const updateSession = async (sessionData) => {
		try {
			if (!sessionData) {
				console.error("❌ Erreur: session est null ou undefined");
				return null;
			}
			if (!sessionData.id) {
				console.error("❌ Erreur: session.id est null ou undefined");
				return null;
			}
			// Ensure players array is never null
			const playersArray = sessionData.players || [];
			// Extract UUIDs from player objects
			const playerIds = playersArray
				.map(p => {
					// Si p est un objet avec un ID
					if (typeof p === 'object' && p !== null && p.id) {
						return p.id;
					}
					// Si p est déjà un UUID (string)
					else if (typeof p === 'string') {
						return p;
					}
					return null;
				})
				.filter(id => id !== null); // Éliminer les valeurs null
			// Nettoyage des données envoyées
			const cleanedSession = {
				id: sessionData.id,
				dayWeek: sessionData.dayWeek,
				start: sessionData.start,
				stop: sessionData.stop,
				idCourt: typeof sessionData.idCourt === 'object' ? sessionData.idCourt.id : sessionData.idCourt,
				idTrainer: sessionData.idTrainer ? (typeof sessionData.idTrainer === 'object' ? sessionData.idTrainer.id : sessionData.idTrainer) : null,
				playerIds: playerIds // IMPORTANT: Utiliser playerIds (et non players)
			};
			const response = await sessionsService.updateSession(cleanedSession.id, cleanedSession);
			const index = sessions.value.findIndex((s) => s.id === sessionData.id);
			if (index !== -1) {
				sessions.value[index] = response.data;
			}
			console.log("✅ Session mise à jour :", response.data);
			return response.data;
		} catch (error) {
				console.error("❌ Erreur lors de la mise à jour :", error.message);
		}
	};


	const deleteSession = async (sessionId) => {
		try {
			await sessionsService.deleteSession(sessionId);
			// Remove session from local state
			sessions.value = sessions.value.filter(s => s.id !== sessionId);
			return true;
		} catch (error) {
			console.error("Erreur lors de la suppression de la session:", error);
			throw error;
		}
	};

	const sendSessionMails = async () => {
		try {
			const response = await sessionsService.getSessionSendMail();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de l'envoi des mails :", error);
			throw error;
		}
	};


	return {
		sessions,
		fetchSessions,
		refreshSessions,
		updateSession,
		deleteSession,
		sendSessionMails
	};
});

