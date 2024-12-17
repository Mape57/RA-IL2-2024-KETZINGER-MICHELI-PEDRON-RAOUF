package well_tennis_club.projet.disponibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibilityService {
    private final DisponibilityRepository disponibilityRepository;
    @Autowired
    public DisponibilityService(DisponibilityRepository repository){this.disponibilityRepository = repository;}

    public List<DisponibilityEntity> getAllDisponibilities(){return disponibilityRepository.findAll();}

    public DisponibilityEntity createDisponibility(DisponibilityEntity entity){return disponibilityRepository.save(entity);}

    public DisponibilityEntity updateDisponibility(DisponibilityEntity entity){return disponibilityRepository.save(entity);}

    public void deleteDisponibility(DisponibilityEntity entity){disponibilityRepository.delete(entity);}

    public DisponibilityEntity getDisponibilityById(Long id){return disponibilityRepository.findById(id).orElse(null);}
}
