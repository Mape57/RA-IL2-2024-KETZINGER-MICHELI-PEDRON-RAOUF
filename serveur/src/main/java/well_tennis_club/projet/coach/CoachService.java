package well_tennis_club.projet.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.disponibility.DisponibilityEntity;
import well_tennis_club.projet.disponibility.DisponibilityRepository;
import well_tennis_club.projet.disponibilityCoach.DisponibilityCoachEntity;
import well_tennis_club.projet.disponibilityCoach.DisponibilityCoachRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    @Autowired
    public CoachService(CoachRepository coachRepository){this.coachRepository = coachRepository;}

    public List<CoachEntity> getAllCoachs(){return coachRepository.findAll();}

    public CoachEntity createCoach(CoachEntity entity){return coachRepository.save(entity);}

    public CoachEntity updateCoach(CoachEntity entity){return coachRepository.save(entity);}

    public void deleteCoach(CoachEntity entity){coachRepository.delete(entity);}

    public CoachEntity getCoachById(UUID id){return coachRepository.findById(id).orElse(null);}
}