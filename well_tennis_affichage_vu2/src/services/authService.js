import apiClient from "./apiService";

let login =(email, password) => {
	return apiClient.post('/auth/login',{ email, password })
}

let logout = () => {
	sessionStorage.removeItem('token');
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


