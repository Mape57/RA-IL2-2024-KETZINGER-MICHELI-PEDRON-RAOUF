import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../views/LoginPage.vue';
import AdminPage from '../views/AdminPage.vue';
import ForgotPassword from "../views/ForgotPassword.vue";
import Register from "../views/Register.vue";
import {accountService as authService} from "../services/authService.js";
import ValidateEmail from "../components/register/ValidateEmail.vue";

const routes = [
	{
		path: '/admin', // Route pour la page administrateur
		name: 'Admin',
		component: AdminPage,
		meta: {
			requiresAuth: true,
		},
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
	{
		path: '/email-validation', // Route pour la page de validation de l'email
		name: 'ValidateEmail',
		component: ValidateEmail,
	}
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

router.beforeEach((to, from, next) => {
	const isAuthenticated = authService.isLogged();

	if (to.meta.requiresAuth && !isAuthenticated) {
		next("/"); // Redirige vers la page de connexion si non authentifié
	} else {
		next(); // Continue la navigation
	}
});

export default router;



