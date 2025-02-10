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
			console.log("🔐 Token envoyé :", config.headers.Authorization);
			console.log("🛠️ Headers envoyés :", config.headers);
			//console.log("confirmation du token");
			//console.log(token);
		}
		//console.log("Tentative de requête API avec Axios :", config.url, config);
		//console.log("Requête envoyée avec ce header :", config.headers);
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
		//console.log("Requête envoyée :", url);
		//console.log("Données envoyées :", JSON.stringify(data, null, 2));
		//console.log("Headers envoyés :", this.apiClient.defaults.headers);
		return apiClient.post(url, data);
	},

	patch(url, data) {
		return apiClient.patch(url, data);
	},

	delete(url) {
		return apiClient.delete(url);
	},
};