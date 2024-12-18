package well_tennis_club.projet.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    @Autowired
    public TimeService(TimeRepository timeRepository){this.timeRepository = timeRepository;}

    public List<TimeEntity> getAllTimes(){return timeRepository.findAll();}
    public TimeEntity createTime(TimeEntity time){return timeRepository.save(time);}
    public TimeEntity updateTime(TimeEntity time){return timeRepository.save(time);}
    public void deleteTime(TimeEntity time){timeRepository.delete(time);}
    public TimeEntity getTimeById(Long id){return timeRepository.findById(id).orElse(null);}
}
