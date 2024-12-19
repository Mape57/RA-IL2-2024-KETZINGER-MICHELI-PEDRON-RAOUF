import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../views/LoginPage.vue';
import AdminPage from '../views/AdminPage.vue';
import ForgotPassword from "../views/ForgotPassword.vue";
import Register from "../views/Register.vue";

const routes = [
	{
		path: '/admin', // Route pour la page administrateur
		name: 'Admin',
		component: AdminPage,
	},
	{
		path: '/', // Route pour la page de connexion
		name: 'Login',
		component: LoginPage,
	},
	{
		path: '/forgot-password', // Route pour la page de mot de passe oublié
		name: 'ForgotPassword',
		component: ForgotPassword,
	},
	{
		path: '/register', // Route pour la page d'inscription
		name: 'Register',
		component: Register,
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;
