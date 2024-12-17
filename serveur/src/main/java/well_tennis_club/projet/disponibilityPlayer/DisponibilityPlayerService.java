package well_tennis_club.projet.disponibilityPlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibilityPlayerService {
    private final DisponibilityPlayerRepository disponibilityPlayerRepository;
    @Autowired
    public DisponibilityPlayerService(DisponibilityPlayerRepository disponibilityPlayerRepository){this.disponibilityPlayerRepository = disponibilityPlayerRepository;}

    public List<DisponibilityPlayerEntity> getAllDisponibilitiesPlayer(){return disponibilityPlayerRepository.findAll();}

    public DisponibilityPlayerEntity createDisponibilityPlayer(DisponibilityPlayerEntity entity){return disponibilityPlayerRepository.save(entity);}
    public void deleteDisponibilityPlayer(DisponibilityPlayerEntity entity){disponibilityPlayerRepository.delete(entity);}

    public List<DisponibilityPlayerEntity> getDisponibilitiesForPlayer(Long id){return disponibilityPlayerRepository.findByDisponibilityPlayerKeyIdPlayer(id);}
}
