package well_tennis_club.projet.timeCourt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TimeCourtService {
    private final TimeCourtRepository timeCourtRepository;
    @Autowired
    public TimeCourtService(TimeCourtRepository timeCourtRepository){this.timeCourtRepository = timeCourtRepository;}

    public List<TimeCourtEntity> getAllTimeCourt(){return timeCourtRepository.findAll();}

    public TimeCourtEntity createTimeCourt(TimeCourtEntity entity){return timeCourtRepository.save(entity);}
    public void deleteTimeCourt(TimeCourtEntity entity){timeCourtRepository.delete(entity);}
    public List<TimeCourtEntity> getTimeCourtForCourt(UUID id){return timeCourtRepository.findByTimeCourtKeyIdCourt(id);}
}
