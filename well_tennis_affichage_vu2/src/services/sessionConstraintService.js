import apiService from "./apiService.js";

export default {
    getSessionConstraints() {
        return apiService.getData('/constraints');
    },

    putSessionConstraint(id, sessionConstraintData) {
        return apiService.put(`/constraints/${id}`, sessionConstraintData);
    },
    
    createSessionConstraint(sessionConstraintData) {
        return apiService.post('/constraints', sessionConstraintData);
    },

    deleteSessionConstraint(id) {
        return apiService.delete(`/constraints/${id}`);
    },

}