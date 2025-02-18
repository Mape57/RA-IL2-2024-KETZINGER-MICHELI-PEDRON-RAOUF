package well_tennis_club.projet.disponibilityTrainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DisponibilityTrainerService {
    private final DisponibilityTrainerRepository disponibilityTrainerRepository;
    @Autowired
    public DisponibilityTrainerService(DisponibilityTrainerRepository disponibilityTrainerRepository){this.disponibilityTrainerRepository = disponibilityTrainerRepository;}

    public List<DisponibilityTrainerEntity> getAllDisponibilitiesTrainer(){return disponibilityTrainerRepository.findAll();}

    public DisponibilityTrainerEntity createDisponibilityTrainer(DisponibilityTrainerEntity entity){return disponibilityTrainerRepository.save(entity);}

    public void deleteDisponibilityTrainer(DisponibilityTrainerEntity entity){disponibilityTrainerRepository.delete(entity);}

    public List<DisponibilityTrainerEntity> getDisponibilityForTrainer(UUID id){return disponibilityTrainerRepository.findByDisponibilityTrainerKeyIdTrainer(id);}
}
