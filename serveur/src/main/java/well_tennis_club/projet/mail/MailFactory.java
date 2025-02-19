package well_tennis_club.projet.mail;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

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

	private SimpleMailMessage constructMail(String subject, String body, String mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailMessage.setTo(mail);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		return mailMessage;
	}
}
