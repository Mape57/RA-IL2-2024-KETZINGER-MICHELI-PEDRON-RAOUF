package well_tennis_club.projet.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import well_tennis_club.projet.core.disponibility.dto.CreateDisponibilityDto;
import well_tennis_club.projet.core.player.dto.PlayerInscriptionDto;
import well_tennis_club.projet.core.session.dto.SessionDto;
import well_tennis_club.projet.core.session.dto.SessionPlayerDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MailFactory {
	private final Environment environment;
	private final JavaMailSender mailSender;

	public MailFactory(Environment environment, JavaMailSender mailSender) {
		this.environment = environment;
		this.mailSender = mailSender;
	}

	public MimeMessage constructResetTokenMail(String token, String mail) {
		String subject = "Réinitialisation de votre mot de passe";
		String htmlBody = """
				<div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
				    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #61815d; padding: 20px;">
				        <tr>
				            <td align="center">
				                <img src="cid:logo" alt="WTC Logo" style="max-width: 100px; height: auto; vertical-align: middle; margin-right: 30px;">
				                <h1 style="color: white; margin: 0; font-size: 32px; font-weight: bold; display: inline-block; vertical-align: middle;">Well Tennis Club</h1>
				            </td>
				        </tr>
				    </table>
				    <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px; margin-top: 20px;">
				        <h2 style="color: #2c3e50;">Bonjour,</h2>
				        <p>Vous avez demandé la réinitialisation de votre mot de passe.</p>
				        <p>Pour procéder au changement, veuillez cliquer sur le bouton ci-dessous :</p>
				        <div style="text-align: center; margin: 30px 0;">
				            <a href="%s/change-password?token=%s"
				               style="background-color: #61815d; color: white; padding: 12px 30px;
				                      text-decoration: none; border-radius: 5px; display: inline-block;">
				                Réinitialiser mon mot de passe
				            </a>
				        </div>
				        <p style="color: #666; font-size: 0.9em;">Si vous n'êtes pas à l'origine de cette demande, veuillez ignorer ce message.</p>
				        <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
				        <p style="color: #666;">Cordialement,<br>L'équipe du Well Tennis Club</p>
				    </div>
				</div>
				""".formatted(environment.getProperty("base_url"), token);
		return constructHtmlMail(subject, htmlBody, mail);
	}

	public MimeMessage constructInscriptionMail(String token, PlayerInscriptionDto playerInscriptionDto) {
		String subject = "Confirmation de votre inscription";
		ObjectMapper objectMapper = new ObjectMapper();
		String pi;
		try {
			pi = objectMapper.writeValueAsString(playerInscriptionDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		String encodedPlayer = URLEncoder.encode(pi, StandardCharsets.UTF_8);
		LocalDate birthday = LocalDate.parse(playerInscriptionDto.getBirthday());
		String formattedBirthday = birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		// Construction du tableau des disponibilités
		StringBuilder disponibilitiesHtml = new StringBuilder();
		disponibilitiesHtml.append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>");
		disponibilitiesHtml.append("<tr style='background-color: #61815d; color: white;'>");
		disponibilitiesHtml.append("<th style='padding: 8px; text-align: left;'>Jour</th>");
		disponibilitiesHtml.append("<th style='padding: 8px; text-align: left;'>De</th>");
		disponibilitiesHtml.append("<th style='padding: 8px; text-align: left;'>À</th></tr>");

		String[] joursSemaine = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

		List<CreateDisponibilityDto> sortedDisponibilities = new ArrayList<>(playerInscriptionDto.getDisponibilities());
		sortedDisponibilities.sort(Comparator.comparingInt(CreateDisponibilityDto::getDayWeek)
				.thenComparing(CreateDisponibilityDto::getOpen));

		for (CreateDisponibilityDto dispo : sortedDisponibilities) {
			disponibilitiesHtml.append("<tr style='background-color: #ffffff;'>");
			disponibilitiesHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(joursSemaine[dispo.getDayWeek() - 1]).append("</td>");
			disponibilitiesHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(dispo.getOpen()).append("</td>");
			disponibilitiesHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(dispo.getClose()).append("</td></tr>");
		}
		disponibilitiesHtml.append("</table>");

		String htmlBody = """
				<div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
				    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #61815d; padding: 20px;">
				        <tr>
				            <td align="center">
				                <img src="cid:logo" alt="WTC Logo" style="max-width: 100px; height: auto; vertical-align: middle; margin-right: 30px;">
				                <h1 style="color: white; margin: 0; font-size: 32px; font-weight: bold; display: inline-block; vertical-align: middle;">Well Tennis Club</h1>
				            </td>
				        </tr>
				    </table>
				    <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px; margin-top: 20px;">
				        <h2 style="color: #2c3e50;">Bonjour %s,</h2>
				        <p>Merci de votre inscription au Well Tennis Club !</p>
				        <div style="background-color: #fff; padding: 15px; border-radius: 5px; margin: 20px 0;">
				            <h3 style="color: #61815d; margin-top: 0;">Récapitulatif de votre inscription</h3>
				            <p><strong>Nom :</strong> %s</p>
				            <p><strong>Prénom :</strong> %s</p>
				            <p><strong>Date de naissance :</strong> %s</p>
				            <p><strong>Email :</strong> %s</p>
				            <p><strong>Nombre de cours souhaités :</strong> %d</p>
				            <h4 style="color: #61815d; margin-top: 20px;">Vos disponibilités</h4>
				            %s
				        </div>
				        <p>Pour confirmer votre inscription, veuillez cliquer sur le bouton ci-dessous :</p>
				        <div style="text-align: center; margin: 30px 0;">
				            <a href="%s/email-validation?token=%s&player=%s"
				               style="background-color: #61815d; color: white; padding: 12px 30px;
				                      text-decoration: none; border-radius: 5px; display: inline-block;">
				                Confirmer mon inscription
				            </a>
				        </div>
				        <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
				        <p style="color: #666;">Cordialement,<br>L'équipe du Well Tennis Club</p>
				    </div>
				</div>
				""".formatted(
				playerInscriptionDto.getSurname(),
				playerInscriptionDto.getName(),
				playerInscriptionDto.getSurname(),
				formattedBirthday,
				playerInscriptionDto.getEmail(),
				playerInscriptionDto.getCourses(),
				disponibilitiesHtml,
				environment.getProperty("base_url"),
				token,
				encodedPlayer
		);
		return constructHtmlMail(subject, htmlBody, playerInscriptionDto.getEmail());
	}

	private MimeMessage constructHtmlMail(String subject, String htmlBody, String mail) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setText(htmlBody, true);
			helper.setTo(mail);
			helper.setFrom(environment.getProperty("spring.mail.username"));
			helper.addInline("logo", new ClassPathResource("images/wtc.png"));
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return message;
	}

	public MimeMessage constructPlanningMail(SessionPlayerDto sessionPlayerDto) {
		String player = sessionPlayerDto.getPlayer().getSurname() + " " + sessionPlayerDto.getPlayer().getName();
		String subject = "Emploi du temps de " + player;

		StringBuilder sessionsHtml = new StringBuilder();
		sessionsHtml.append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>");
		sessionsHtml.append("<tr style='background-color: #61815d; color: white;'>");
		sessionsHtml.append("<th style='padding: 8px; text-align: left;'>Date</th>");
		sessionsHtml.append("<th style='padding: 8px; text-align: left;'>Heure début</th>");
		sessionsHtml.append("<th style='padding: 8px; text-align: left;'>Heure fin</th>");
		sessionsHtml.append("<th style='padding: 8px; text-align: left;'>Terrain</th></tr>");

		String[] joursSemaine = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

		for (SessionDto session : sessionPlayerDto.getSessions()) {
			sessionsHtml.append("<tr style='background-color: #ffffff;'>");
			sessionsHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(joursSemaine[session.getDayWeek()-1]).append("</td>");
			sessionsHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(session.getStart()).append("</td>");
			sessionsHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(session.getStop()).append("</td>");
			sessionsHtml.append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
					.append(session.getIdCourt().getName()).append("</td></tr>");
		}
		sessionsHtml.append("</table>");

		String htmlBody = String.format("""
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #61815d; padding: 20px;">
                <tr>
                    <td align="center">
                        <img src="cid:logo" alt="WTC Logo" style="max-width: 100px; height: auto; vertical-align: middle; margin-right: 30px;">
                        <h1 style="color: white; margin: 0; font-size: 32px; font-weight: bold; display: inline-block; vertical-align: middle;">Well Tennis Club</h1>
                    </td>
                </tr>
            </table>
            <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px; margin-top: 20px;">
                <h2 style="color: #2c3e50;">Bonjour %s,</h2>
                <p>Voici votre emploi du temps des sessions de tennis :</p>
                <div style="background-color: #fff; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    %s
                </div>
                <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                <p style="color: #666;">Cordialement,<br>L'équipe du Well Tennis Club</p>
            </div>
        </div>
        """, sessionPlayerDto.getPlayer().getName(), sessionsHtml);

		MimeMessage message = mailSender.createMimeMessage();
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setText(htmlBody, true);
			helper.setTo(sessionPlayerDto.getPlayer().getEmail());
			helper.setFrom(environment.getProperty("spring.mail.username"));
			helper.addInline("logo", new ClassPathResource("images/wtc.png"));
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return message;
	}
}