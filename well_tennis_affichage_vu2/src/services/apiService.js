// src/services/apiService.js
import axios from 'axios';

const apiClient = axios.create({
	baseURL: 'http://localhost:8080/',
	headers: {
		'Content-Type': 'application/json',
	},
});

// Gestion des erreurs globales
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

	put(url, data) {
		return apiClient.put(url, data);
	},

	delete(url) {
		return apiClient.delete(url);
	},
};
