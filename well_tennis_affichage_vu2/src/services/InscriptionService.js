// src/services/InscriptionService.js
import apiService from './apiService';

export default {
    createInscription(inscriptionData) {
        return apiService.post('/inscription', inscriptionData);
    },

};
