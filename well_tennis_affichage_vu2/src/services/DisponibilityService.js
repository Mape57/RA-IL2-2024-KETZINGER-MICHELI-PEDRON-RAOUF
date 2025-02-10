// src/services/DisponibilityService.js
import apiService from './apiService';

export default {
    getAllAvailabilities() {
        return apiService.getData('/disponibility');
    },

    getAvailabilityById(id) {
        return apiService.getData(`/disponibility/${id}`);
    },

    createAvailability(availabilityData) {
        return apiService.post('/disponibility', availabilityData);
    },

    updateAvailability(id, availabilityData) {
        return apiService.patch(`/disponibility/${id}`, availabilityData);
    },

    deleteAvailability(id) {
        return apiService.delete(`/disponibility/${id}`);
    },
};
