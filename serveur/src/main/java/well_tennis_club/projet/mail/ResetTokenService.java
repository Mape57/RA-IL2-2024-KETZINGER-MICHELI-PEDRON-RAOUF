package well_tennis_club.projet.mail;

import org.springframework.stereotype.Service;
import well_tennis_club.projet.trainer.TrainerEntity;

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
		return resetToken != null && !resetToken.getExpiryDate().before(new Date());
	}
}
