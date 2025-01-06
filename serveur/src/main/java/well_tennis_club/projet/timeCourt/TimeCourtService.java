package well_tennis_club.projet.timeCourt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeCourtService {
    private final TimeCourtRepository timeCourtRepository;
    @Autowired
    public TimeCourtService(TimeCourtRepository timeCourtRepository){this.timeCourtRepository = timeCourtRepository;}

    public List<TimeCourtEntity> getAllTimeCourt(){return timeCourtRepository.findAll();}

    public TimeCourtEntity createTimeCourt(TimeCourtEntity entity){return timeCourtRepository.save(entity);}
    public void deleteTimeCourt(TimeCourtEntity entity){timeCourtRepository.delete(entity);}
    public List<TimeCourtEntity> getTimeCourtForCourt(Long id){return timeCourtRepository.findByTimeCourtKeyIdCourt(id);}
}
