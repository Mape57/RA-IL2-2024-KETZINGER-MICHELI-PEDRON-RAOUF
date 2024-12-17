import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../views/LoginPage.vue';
import AdminPage from '../views/AdminPage.vue';
import ForgotPassword from "../views/ForgotPassword.vue";

const routes = [
	{
		path: '/admin', // Route pour la page de connexion
		name: 'Admin',
		component: AdminPage,
	},
	{
		path: '/', // Route pour la page administrateur
		name: 'Login',
		component: LoginPage,
	},
	{
		path: '/forgot-password',
		name: 'ForgotPassword',
		component: ForgotPassword,
	}
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;



