package well_tennis_club.projet.core.trainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.core.trainer.dto.TrainerDto;
import well_tennis_club.projet.core.trainer.mapper.TrainerMapper;

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
		TrainerDto trainerDto = TrainerMapper.INSTANCE.mapToDTO(trainerService.getTrainerByEmail(email));
		if (trainerDto == null) {
			throw new UsernameNotFoundException("Error");
		}
		String role;
		if (trainerDto.isAdmin()) {
			role = "ADMIN";
		} else {
			role = "TRAINER";
		}
		return new User(trainerDto.getEmail(), trainerDto.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(role)));
	}
}
