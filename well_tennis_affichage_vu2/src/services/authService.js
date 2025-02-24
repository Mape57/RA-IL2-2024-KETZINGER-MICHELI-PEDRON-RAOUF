import apiClient from "./apiService";

let login = async (email, password) => {
	try {
		const response = await apiClient.post('/auth/login', { email, password });

		console.log("Réponse API :", response.data);

		if (response.data.token && response.data.role) {
			saveToken(response.data.token);
			sessionStorage.setItem('role', response.data.role);
		}

		return response.data;
	} catch (error) {
		console.error("Erreur API :", error.response?.data || error.message);
		throw error;
	}
};



let logout = () => {
	console.warn("Déconnexion automatique...");
	sessionStorage.removeItem('token');
	window.location.href = "/";
}

let saveToken = (token) => {
	sessionStorage.setItem('token', token);
}

let isLogged = () => {
	let token = sessionStorage.getItem('token')
	return !! token
}

let getToken = () => {
	return sessionStorage.getItem('token')
}

let getUserRole = () => {
	const role = sessionStorage.getItem('role');
	console.log("Rôle récupéré depuis le stockage :", role);
	return role ? role.toUpperCase() : null;
};




export const accountService = {
	login,
	logout,
	saveToken,
	isLogged,
	getToken,
	getUserRole,
}


