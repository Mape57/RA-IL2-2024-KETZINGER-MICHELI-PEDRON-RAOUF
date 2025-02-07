// src/services/apiService.js
import axios from 'axios';

const apiClient = axios.create({
	baseURL: 'http://localhost:8080/', // L'URL de votre API
	headers: {
		'Content-Type': 'application/json'
	},
});


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