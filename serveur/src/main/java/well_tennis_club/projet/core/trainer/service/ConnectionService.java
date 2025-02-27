package well_tennis_club.projet.core.trainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.core.trainer.entity.TrainerEntity;

import java.util.Collections;


@Service
public class ConnectionService implements UserDetailsService {
	private final TrainerService trainerService;

	@Autowired
	public ConnectionService(TrainerService trainerService) {
		this.trainerService = trainerService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TrainerEntity trainer = trainerService.getTrainerByEmail(email);
		if (trainer == null) {
			throw new UsernameNotFoundException("Error");
		}

		String role;
		if (trainer.isAdmin()) {
			role = "ADMIN";
		} else {
			role = "TRAINER";
		}
		return new User(trainer.getEmail(), trainer.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(role)));
	}
}
