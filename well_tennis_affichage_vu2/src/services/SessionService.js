import apiService from "./apiService";

export default {
    getAllSessions() {
        return apiService.getData("/sessions");
    },
    createSession(session) {
        return apiService.post("/sessions", session);
    },
    updateSession(id, data) {
        return apiService.put(`/sessions/${id}`, data);
    },
    deleteSession(id) {
        return apiService.delete(`/sessions/${id}`);
    },
};
