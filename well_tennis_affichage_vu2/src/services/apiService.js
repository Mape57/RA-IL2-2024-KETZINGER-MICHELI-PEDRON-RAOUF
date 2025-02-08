import axios from 'axios';

// Récupération de l'URL de l'API depuis les variables d'environnement
const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/';

// Création du client Axios
const apiClient = axios.create({
	baseURL: BASE_URL,
	headers: {
		'Content-Type': 'application/json',
	},
	timeout: 10000, // Timeout de 10 secondes
});

// Ajouter un intercepteur pour inclure le token dans les requêtes
apiClient.interceptors.request.use(
	(config) => {
		const token = localStorage.getItem('authToken');
		if (token) {
			config.headers['Authorization'] = `Bearer ${token}`;
		}
		return config;
	},
	(error) => {
		console.error('Erreur dans l’intercepteur de requête :', error.message);
		return Promise.reject(error);
	}
);

// Gestion des erreurs API
apiClient.interceptors.response.use(
	(response) => response,
	(error) => {
		if (error.response) {
			console.error(
				`Erreur API (${error.response.status}): ${error.response.data?.message || 'Problème inconnu'}`
			);
		} else if (error.request) {
			console.error('Aucune réponse du serveur. Vérifiez votre connexion.');
		} else {
			console.error('Erreur Axios:', error.message);
		}
		return Promise.reject(error);
	}
);

// Exportation correcte du service avec `getData`
const apiService = {
	getData(url) {
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

export default apiService;
