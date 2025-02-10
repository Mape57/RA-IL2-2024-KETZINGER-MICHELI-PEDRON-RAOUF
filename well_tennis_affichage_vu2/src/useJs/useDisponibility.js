// src/composables/useDisponibility.js
import {ref} from "vue";
import DisponibilityService from "../services/DisponibilityService.js";


export default function useDisponibility() {

	const Disponibility = ref([]);

	// Fetch toutes les disponibilités
	const fetchDisponibility = async () => {
		try {
			const response = await DisponibilityService.getAllDisponibility();
			Disponibility.value = response.data;
		} catch (error) {
		}
	};

	// Crée une nouvelle disponibilité
	const createDisponibility = async (availability) => {
		try {
			const response = await DisponibilityService.createDisponibility(availability);
			Disponibility.value.push(response.data);
			return response.data;
		} catch (error) {
			throw error;
		}
	};
	// Mise à jour d'une disponibilité
	const updateDisponibility = async (availability) => {
		try {
			const response = await DisponibilityService.updateDisponibility(availability.id, availability);
			const index = Disponibility.value.findIndex((a) => a.id === availability.id);
			if (index !== -1) {
				Disponibility.value[index] = response.data;
			}
			return response.data;
		} catch (error) {
			throw error;
		}
	};
	// Suppression d'une disponibilité
	const deleteDisponibility = async (id) => {
		try {
			await DisponibilityService.deleteDisponibility(id);
			Disponibility.value = Disponibility.value.filter((a) => a.id !== id);
		} catch (error) {
		}
	};
	return {
		Disponibility,
		fetchDisponibility,
		createDisponibility,
		updateDisponibility,
		deleteDisponibility,
	};
}