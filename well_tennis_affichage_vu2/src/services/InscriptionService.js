// src/services/InscriptionService.js
import apiService from './apiService';

export default {
    createInscription(inscriptionData) {
        return apiService.post('/inscription/verify', inscriptionData);
    },

    validateEmail(token, playerData) {
        return apiService.post('/inscription', playerData, {
            headers: {
                Authorization: token,
            },
        });
    },
};
