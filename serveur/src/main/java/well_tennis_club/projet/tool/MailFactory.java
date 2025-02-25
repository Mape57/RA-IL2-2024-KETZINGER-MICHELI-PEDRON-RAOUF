package well_tennis_club.projet.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.player.dto.PlayerInscriptionDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class MailFactory {
	private final Environment environment;

	public MailFactory(Environment environment) {
		this.environment = environment;
	}

	public SimpleMailMessage constructResetTokenMail(String token, String mail) {
		String subject = "Password Reset Request";
		String body = "To reset your password, click the link below:\n" +
				"http://localhost:8080/trainers/changePassword?token=" + token;
		return constructMail(subject, body, mail);
	}

	public SimpleMailMessage constructInscriptionMail(String token, PlayerInscriptionDto playerInscriptionDto) {
		String subject = "Inscription Request";
		ObjectMapper objectMapper = new ObjectMapper();
		String pi;
		try {
			pi = objectMapper.writeValueAsString(playerInscriptionDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		String body = "To confirm your inscription, click the link below:\n" +
				"http://localhost:8080/inscription?token=" + token + "&player=" + URLEncoder.encode(pi, StandardCharsets.UTF_8);
		return constructMail(subject, body, playerInscriptionDto.getEmail());
	}

	private SimpleMailMessage constructMail(String subject, String body, String mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailMessage.setTo(mail);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		return mailMessage;
	}
}
