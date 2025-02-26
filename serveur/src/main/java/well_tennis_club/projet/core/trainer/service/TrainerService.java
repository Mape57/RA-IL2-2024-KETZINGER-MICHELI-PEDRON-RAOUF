package well_tennis_club.projet.core.trainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;
import well_tennis_club.projet.core.trainer.repository.TrainerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TrainerService {
	private final TrainerRepository trainerRepository;

	@Autowired
	public TrainerService(TrainerRepository trainerRepository) {
		this.trainerRepository = trainerRepository;
	}

	public List<TrainerEntity> getAllTrainers() {
		return trainerRepository.findAll();
	}

	public TrainerEntity createTrainer(TrainerEntity entity) {
		return trainerRepository.save(entity);
	}

	public TrainerEntity updateTrainer(TrainerEntity entity) {
		return trainerRepository.save(entity);
	}

	public int deleteById(UUID id) {
		return trainerRepository.deleteTrainerEntityById(id);
	}

	public TrainerEntity getTrainerById(UUID id) {
		return trainerRepository.findById(id).orElse(null);
	}

	public String getPassword(String email) {
		TrainerEntity trainer = trainerRepository.findByEmail(email);
		return trainer != null ? trainer.getPassword() : null;
	}

	public TrainerEntity getTrainerByEmail(String email) {
		return trainerRepository.findByEmail(email);
	}
}