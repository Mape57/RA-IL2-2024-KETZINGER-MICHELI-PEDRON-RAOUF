import axios from 'axios';
import UNIQUE_TOKEN from '../config/token.js'; // Import du token

// Création du client Axios
const apiClient = axios.create({
	baseURL: 'http://localhost:8080/', // URL de votre API
	headers: {
		'Content-Type': 'application/json',
	},
	timeout: 10000, // Délai maximum pour les requêtes (10 secondes)
});

// Ajouter un intercepteur pour inclure le token dans les requêtes
apiClient.interceptors.request.use(
	(config) => {
		if (UNIQUE_TOKEN) {
			config.headers['Authorization'] = `Bearer ${UNIQUE_TOKEN}`;
		}
		return config;
	},
	(error) => {
		console.error('Erreur dans l’intercepteur de requête :', error.message);
		return Promise.reject(error);
	}
);

// Gestion des erreurs de réponse
apiClient.interceptors.response.use(
	(response) => {
		// Retourne directement la réponse si tout va bien
		return response;
	},
);

export default {
	// Méthode GET
	getData(url = '') {
		return apiClient.get(url);
	},

	// Méthode POST
	post(url, data) {
		return apiClient.post(url, data);
	},

	// Méthode PATCH
	patch(url, data) {
		return apiClient.patch(url, data);
	},

	// Méthode DELETE
	delete(url) {
		return apiClient.delete(url);
	},
};
