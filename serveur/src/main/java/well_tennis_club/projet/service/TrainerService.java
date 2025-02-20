package well_tennis_club.projet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.dto.TrainerDto;
import well_tennis_club.projet.entity.TrainerEntity;
import well_tennis_club.projet.mapper.TrainerMapper;
import well_tennis_club.projet.repository.TrainerRepository;

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

	public void deleteTrainer(TrainerEntity entity) {
		trainerRepository.delete(entity);
	}

	public TrainerEntity getTrainerById(UUID id) {
		return trainerRepository.findById(id).orElse(null);
	}

	public String getPassword(String email) {
		TrainerDto trainerDto = TrainerMapper.INSTANCE.mapToDTO(trainerRepository.findByEmail(email));
		return trainerDto != null ? trainerDto.getPassword() : null;
	}

	public TrainerEntity getTrainerByEmail(String email) {
		return trainerRepository.findByEmail(email);
	}
}