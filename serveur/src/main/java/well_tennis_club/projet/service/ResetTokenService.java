package well_tennis_club.projet.service;

import org.springframework.stereotype.Service;
import well_tennis_club.projet.entity.ResetTokenEntity;
import well_tennis_club.projet.entity.TrainerEntity;
import well_tennis_club.projet.repository.ResetTokenRepository;

import java.util.Date;

@Service
public class ResetTokenService {
	private ResetTokenRepository resetTokenRepository;

	public ResetTokenService(ResetTokenRepository resetTokenRepository) {
		this.resetTokenRepository = resetTokenRepository;
	}

	public TrainerEntity getTrainerByToken(String token) {
		ResetTokenEntity resetToken = resetTokenRepository.findByToken(token);
		return resetToken.getTrainer();
	}

	public ResetTokenEntity createResetTokenForTrainer(TrainerEntity trainer, String token) {
		ResetTokenEntity resetToken = new ResetTokenEntity(trainer, token);
		resetTokenRepository.save(resetToken);
		return resetToken;
	}

	public boolean isResetTokenValid(String token) {
		ResetTokenEntity resetToken = resetTokenRepository.findByToken(token);
		return resetToken != null && !resetToken.getExpirationDate().before(new Date());
	}

	public void deleteByToken(String token) {
		resetTokenRepository.deleteByToken(token);
	}
}
