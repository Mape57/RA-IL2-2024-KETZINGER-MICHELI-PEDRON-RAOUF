import apiClient from "./apiService";

let login =(email, password) => {
	return apiClient.post('/auth/login',{ email, password })
}

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

export const accountService = {
	login,
	logout,
	saveToken,
	isLogged,
	getToken
}


