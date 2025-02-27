// src/composables/useSessionConstraint.js
import { ref } from "vue";
import sessionConstraintService from "../services/sessionConstraintService.js";

export default function useSessionConstraint() {
    const sessionConstraints = ref([]);

    const fetchSessionConstraints = async () => {
        try {
            const response = await sessionConstraintService.getSessionConstraints();
            sessionConstraints.value = response.data;
        } catch (error) {
            console.error("Erreur lors de la récupération des contraintes de session :", error);
        }
    };

    const createSessionConstraint = async (constraint) => {
        try {
            const response = await sessionConstraintService.createSessionConstraint(constraint);
            sessionConstraints.value.push(response.data);
            return response.data;
        } catch (error) {
            console.error("Erreur lors de la création de la contrainte de session :", error);
            throw error;
        }
    };

    const updateSessionConstraint = async (id, constraint) => {
        try {
            const response = await sessionConstraintService.putSessionConstraint(id, constraint);
            const index = sessionConstraints.value.findIndex((c) => c.id === id);
            if (index !== -1) {
                sessionConstraints.value[index] = response.data;
            }
            return response.data;
        } catch (error) {
            console.error("Erreur lors de la mise à jour de la contrainte de session :", error);
            throw error;
        }
    };

    const deleteSessionConstraint = async (id) => {
        try {
            await sessionConstraintService.deleteSessionConstraint(id);
            sessionConstraints.value = sessionConstraints.value.filter((c) => c.id !== id);
        } catch (error) {
            console.error("Erreur lors de la suppression de la contrainte de session :", error);
            throw error;
        }
    };

    return {
        sessionConstraints,
        fetchSessionConstraints,
        createSessionConstraint,
        updateSessionConstraint,
        deleteSessionConstraint
    };
}