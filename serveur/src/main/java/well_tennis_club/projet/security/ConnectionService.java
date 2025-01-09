package well_tennis_club.projet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import well_tennis_club.projet.coach.CoachDto;
import well_tennis_club.projet.coach.CoachMapper;
import well_tennis_club.projet.coach.CoachService;

import java.util.Collections;


@Service
public class ConnectionService implements UserDetailsService {
    private final CoachService coachService;
    @Autowired
    public ConnectionService(CoachService coachService){this.coachService = coachService;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CoachDto coachDto = CoachMapper.INSTANCE.mapToDTO(coachService.getCoachByEmail(email));
        if (coachDto == null){
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
        String role;
        if(coachDto.isAdmin()){
            role = "ADMIN";
        }else{
            role = "COACH";
        }
        return new User(coachDto.getEmail(),coachDto.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
