// src/composables/useDisponibility.js
import { ref } from "vue";
import availabilitiesService from "../services/DisponibilityService.js";

export default function useDisponibility() {
    const availabilities = ref([]);

    // Fetch toutes les disponibilités
    const fetchAvailabilities = async () => {
        try {
            const response = await availabilitiesService.getAllAvailabilities();
            availabilities.value = response.data;
        } catch (error) {
        }
    };

    // Crée une nouvelle disponibilité
    const createAvailability = async (availability) => {
        try {
            const response = await availabilitiesService.createAvailability(availability);
            availabilities.value.push(response.data);
            return response.data;
        } catch (error) {
            throw error;
        }
    };

    // Mise à jour d'une disponibilité
    const updateAvailability = async (availability) => {
        try {
            const response = await availabilitiesService.updateAvailability(availability.id, availability);
            const index = availabilities.value.findIndex((a) => a.id === availability.id);
            if (index !== -1) {
                availabilities.value[index] = response.data;
            }
            return response.data;
        } catch (error) {
            throw error;
        }
    };

    // Suppression d'une disponibilité
    const deleteAvailability = async (id) => {
        try {
            await availabilitiesService.deleteAvailability(id);
            availabilities.value = availabilities.value.filter((a) => a.id !== id);
        } catch (error) {
        }
    };

    return {
        availabilities,
        fetchAvailabilities,
        createAvailability,
        updateAvailability,
        deleteAvailability,
    };
}
