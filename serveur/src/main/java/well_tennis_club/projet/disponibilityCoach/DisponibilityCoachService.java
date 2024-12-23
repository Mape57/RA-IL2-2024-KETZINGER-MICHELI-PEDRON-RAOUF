package well_tennis_club.projet.disponibilityCoach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibilityCoachService {
    private final DisponibilityCoachRepository disponibilityCoachRepository;
    @Autowired
    public DisponibilityCoachService(DisponibilityCoachRepository disponibilityCoachRepository){this.disponibilityCoachRepository = disponibilityCoachRepository;}

    public List<DisponibilityCoachEntity> getAllDisponibilitiesCoach(){return disponibilityCoachRepository.findAll();}

    public DisponibilityCoachEntity createDisponibilityCoach(DisponibilityCoachEntity entity){return disponibilityCoachRepository.save(entity);}

    public void deleteDisponibilityCoach(DisponibilityCoachEntity entity){disponibilityCoachRepository.delete(entity);}

    public List<DisponibilityCoachEntity> getDisponibilityForCoach(Long id){return disponibilityCoachRepository.findByDisponibilityCoachKeyIdCoach(id);}
}
