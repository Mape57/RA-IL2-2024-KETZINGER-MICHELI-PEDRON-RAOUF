import { ref } from "vue";
import terrainService from "../services/TerrainService.js";

export default function useTerrain() {
    const terrains = ref([]);

    const fetchTerrains = async () => {
        try {
            const response = await terrainService.getAllTerrain();
            terrains.value = response.data;
        } catch (error) {
            console.error("Erreur lors de la récupération des terrains :", error);
        }
    };

    const updateTerrain = async (terrainId, updatedData) => {
        try {

            const response = await terrainService.updateTerrain(terrainId, updatedData);

            const index = terrains.value.findIndex((t) => t.id === terrainId);
            if (index !== -1) {
                terrains.value[index] = response.data;
            }

            console.log("Terrain mis à jour :", response.data);
            return response.data;
        } catch (error) {
            console.error("Erreur lors de la mise à jour du terrain :", error);
            throw error;
        }
    };

    const createTerrain = async (newTerrainData) => {
        try {
            const response = await terrainService.createTerrain(newTerrainData);
            console.log("Terrain créé :", response.data);
            return response.data;
        } catch (error) {
            console.error("Erreur lors de la création du terrain :", error);
            throw error;
        }
    };

    const deleteTerrain = async (terrainId) => {
        try {
            const response = await terrainService.deleteTerrain(terrainId);
            console.log("Terrain supprimé :", response.data);

            const index = terrains.value.findIndex((t) => t.id === terrainId);
            if (index !== -1) {
                terrains.value.splice(index, 1);
            }

            return response.data;
        } catch (error) {
            console.error("Erreur lors de la suppression du terrain :", error);
            throw error;
        }
    }

    return {
        terrains,
        fetchTerrains,
        updateTerrain,
        createTerrain,
        deleteTerrain,
    };
}
