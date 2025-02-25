import { ref } from "vue";
import inscriptionService from "../services/InscriptionService";

export default function useInscription() {
    const createInscription = async (inscription) => {
        try {
            console.log("Json :", JSON.stringify(inscription, null, 2));
            const response = await inscriptionService.createInscription(inscription);
            return response.data;
        } catch (error) {
            console.error("Erreur lors de l'inscription :", error.response?.data || error.message);
            throw error;
        }
    };

    return {
        createInscription,
    };
}
