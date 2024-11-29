package well_tennis_club.projet.court;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtService {
    private final CourtRepository courtRepository;
    @Autowired
    public CourtService(CourtRepository courtRepository){this.courtRepository = courtRepository;}

    public List<CourtEntity> getAllCourts(){return courtRepository.findAll();}

    public CourtEntity createCourt(CourtEntity entity){return courtRepository.save(entity);}

    public CourtEntity updateCourt(CourtEntity entity){return courtRepository.save(entity);}

    public void deleteCourt(CourtEntity entity){courtRepository.delete(entity);}
}
