// src/services/apiService.js
import { accountService } from './authService';
import axios from "axios";

const apiClient = axios.create({
	baseURL: 'http://localhost:8080', // L'URL de votre API
	headers: {
		'Content-Type': 'application/json'
	},
});


// // Ajouter le token JWT à toutes les requêtes
// apiClient.interceptors.request.use(
// 	(config) => {
// 		const token = accountService.getToken();
// 		if (token) {
// 			config.url = config.baseURL + config.url;
// 			config.headers.Authorization = `Bearer ${token}`;
// 			console.log(token);
// 		}
// 		return config;
// 	},
// 	(error) => Promise.reject(error)
// );

apiClient.interceptors.request.use(
	(config) => {
		const token = accountService.getToken();

		// Liste des routes qui NE nécessitent PAS de token
		const noAuthRoutes = ['/inscription'];

		// Vérifier si l'URL actuelle est dans la liste des routes publiques
		const isPublicRoute = noAuthRoutes.some(route => config.url.includes(route));

		// Ajouter le token SEULEMENT si la route N'EST PAS dans la liste des routes publiques
		if (!isPublicRoute && token) {
			config.headers.Authorization = `Bearer ${token}`;
			console.log("🔑 Token ajouté pour :", config.url);
		} else {
			console.log("🚫 Pas de token pour :", config.url);
		}

		return config;
	},
	(error) => Promise.reject(error)
);



// Gestion des erreurs de réponse
apiClient.interceptors.response.use(
	(response) => response,
	(error) => {

		// Si le token est expiré (Erreur 401)
		if (error.response && error.response.status === 401) {
			console.warn("Token expiré, déconnexion et redirection vers /login");
			accountService.logout();
			router.push("/").catch(() => {}); // Redirection vers la page de connexion
		}


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