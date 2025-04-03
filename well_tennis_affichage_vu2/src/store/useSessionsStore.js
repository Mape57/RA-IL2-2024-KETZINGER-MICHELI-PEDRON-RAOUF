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
				console.error("Erreur: session est null ou undefined");
				return null;
			}
			if (!sessionData.id) {
				console.error("Erreur: session.id est null ou undefined");
				return null;
			}
			// Ensure players array is never null
			const playersArray = sessionData.players || [];
			// Extract UUIDs from player objects and ensure uniqueness
			const playerIds = [...new Set(
				playersArray
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
					.filter(id => id !== null) // Éliminer les valeurs null
			)]; // Utilisation de Set pour éliminer les doublons d'IDs

			console.log("PlayerIds après déduplication:", playerIds);
			
			// Traitement spécial pour l'entraîneur (idTrainer)
			let trainerId = null;
			if (sessionData.idTrainer) {
				// Si idTrainer est un objet, extraire son ID
				if (typeof sessionData.idTrainer === 'object') {
					trainerId = sessionData.idTrainer.id || sessionData.idTrainer.idtrainer;
					console.log("Trainer ID extrait de l'objet:", trainerId);
				} else {
					// Si c'est déjà un ID (string/number)
					trainerId = sessionData.idTrainer;
					console.log("Trainer ID déjà sous forme primitive:", trainerId);
				}
			}
			
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
			
			console.log("Envoi de la session mise à jour au backend:", cleanedSession);
			const response = await sessionsService.updateSession(cleanedSession.id, cleanedSession);

			// Forcer un rechargement complet depuis le backend pour s'assurer d'avoir les données à jour
			await fetchSessions();

			console.log("✅ Session mise à jour :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la mise à jour :", error.message);
			// En cas d'erreur, recharger quand même les données pour rester cohérent
			await fetchSessions();
			return null;
		}
	};


	const createSession = async (sessionData) => {
		try {
			// Ensure there is a valid court ID
			if (!sessionData.idCourt) {
				console.error("Erreur: idCourt est requis pour créer une session");
				return null;
			}

			console.log("Création d'une nouvelle session:", sessionData);
			const response = await sessionsService.createSession(sessionData);
			
			// Refresh the sessions list
			await fetchSessions();
			
			console.log("✅ Session créée :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création de la session:", error.message);
			return null;
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
	createSession,
	deleteSession,
	sendSessionMails
};
});


