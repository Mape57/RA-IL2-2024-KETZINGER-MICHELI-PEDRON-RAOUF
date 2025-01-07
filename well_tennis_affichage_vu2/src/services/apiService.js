// src/services/apiService.js
import axios from 'axios';
import UNIQUE_TOKEN from '../config/token.js'; // Import du token

const apiClient = axios.create({
	baseURL: 'http://localhost:8080/', // L'URL de votre API
	headers: {
		'Content-Type': 'application/json',

	},
});

// Ajouter un intercepteur pour inclure le token dans les requêtes
apiClient.interceptors.request.use(
	(config) => {
		config.headers['X-API-KEY'] = UNIQUE_TOKEN; // Ajout du token unique
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

// Gestion des erreurs de réponse
apiClient.interceptors.response.use(
	(response) => response,
	(error) => {
		console.error('Erreur API :', error.response?.data || error.message);
		return Promise.reject(error);
	}
);


export default {
	getData(url = '') {
		return apiClient.get(url);
	},

	post(url, data) {
		return apiClient.post(url, data);
	},

	patch(url, data) {
		return apiClient.patch(url, data);
	},

	delete(url) {
		return apiClient.delete(url);
	},
};