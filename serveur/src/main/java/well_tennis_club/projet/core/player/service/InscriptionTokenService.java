package well_tennis_club.projet.core.player.service;

import org.springframework.stereotype.Service;
import well_tennis_club.projet.core.player.entity.InscriptionTokenEntity;
import well_tennis_club.projet.core.player.repository.InscriptionTokenRepository;

@Service
public class InscriptionTokenService {
	private final InscriptionTokenRepository inscriptionTokenRepository;

	public InscriptionTokenService(InscriptionTokenRepository inscriptionTokenRepository) {
		this.inscriptionTokenRepository = inscriptionTokenRepository;
	}

	public InscriptionTokenEntity createInscriptionTokenForEmail(String email, String token) {
		InscriptionTokenEntity inscriptionToken = new InscriptionTokenEntity(email, token);
		inscriptionTokenRepository.save(inscriptionToken);
		return inscriptionToken;
	}

	public boolean isTokenValid(String token) {
		InscriptionTokenEntity inscriptionToken = inscriptionTokenRepository.findByToken(token);
		return inscriptionToken != null;
	}

	public void deleteByToken(String token) {
		inscriptionTokenRepository.deleteByToken(token);
	}
}
