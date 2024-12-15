package well_tennis_club.projet.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    @Autowired
    public CoachService(CoachRepository coachRepository){this.coachRepository = coachRepository;}

    public List<CoachEntity> getAllCoachs(){return coachRepository.findAll();}

    public CoachEntity createCoach(CoachEntity entity){return coachRepository.save(entity);}

    public CoachEntity updateCoach(CoachEntity entity){return coachRepository.save(entity);}

    public void deleteCoach(CoachEntity entity){coachRepository.delete(entity);}
}