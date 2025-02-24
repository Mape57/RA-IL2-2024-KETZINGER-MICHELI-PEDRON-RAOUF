import {createRouter, createWebHistory} from 'vue-router';
import LoginPage from '../views/LoginPage.vue';
import AdminPage from '../views/AdminPage.vue';
import ForgotPassword from "../views/ForgotPassword.vue";
import Register from "../views/Register.vue";
import {accountService as authService} from "../services/authService.js";
import TrainerPage from '../views/TrainerPage.vue';

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
        path: '/trainer', //route pour la page de l'entraineur
        name: 'Trainer',
        component: TrainerPage,
        meta: {
            requiresAuth: true,
        },

    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = authService.isLogged();
    const userRole = authService.getUserRole() || ""; // Récupération du rôle

    if (to.meta.requiresAuth && !isAuthenticated) {
        next("/"); // Redirige vers la page de connexion si non authentifié
    } else if (to.name === "Trainer" && userRole !== "TRAINER") {
        next("/"); // Redirige vers la page d'accueil si ce n'est pas un trainer
    } else {
        next(); // Continue la navigation
    }
});




export default router;



