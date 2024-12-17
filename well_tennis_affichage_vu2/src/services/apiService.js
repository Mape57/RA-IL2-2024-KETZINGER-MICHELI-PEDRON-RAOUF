// src/services/apiService.js
import axios from 'axios';

const apiClient = axios.create({
	baseURL: 'http://localhost:8080/', // Remplacez par l'URL de votre API
	headers: {
		'Content-Type': 'application/json',
	},
});

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