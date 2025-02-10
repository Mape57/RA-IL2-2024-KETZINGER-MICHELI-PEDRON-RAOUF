// src/services/apiService.js
import { accountService } from './authService';
import axios from "axios";

const apiClient = axios.create({
	baseURL: 'http://localhost:8080', // L'URL de votre API
	headers: {
		'Content-Type': 'application/json'
	},
});


// Ajouter le token JWT à toutes les requêtes
apiClient.interceptors.request.use(
	(config) => {
		const token = accountService.getToken();
		if (token) {
			config.url = config.baseURL + config.url;
			config.headers.Authorization = `Bearer ${token}`;
			console.log(token);
		}
		return config;
	},
	(error) => Promise.reject(error)
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
	apiClient,
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