package well_tennis_club.timefold.tools;

public class TimetableConstraintProperties {
	// =============== CONTRAINTES DES GROUPES =============== //
	public static final String GROUP_AGE_DIFFERENCE = "Difference d'âge du groupe";
	public static final String GROUP_LEVEL_DIFFERENCE = "Difference de niveau du groupe";
	public static final String GROUP_SIZE = "Taille du groupe";
	public static final String GROUP_SESSION_CONSTRAINT = "Même contraintes de session pour un groupe";


	// =============== CONTRAINTES DES SESSIONS =============== //
	public static final String SESSION_TRAINER_WITHOUT_PLAYERS = "Présence d'un entraineur pour une session vide";
	public static final String SESSION_WITH_PLAYERS_NEED_TRAINER = "Présence de joueurs sans entraineur";
	public static final String SESSION_OVERLAPPING = "Chevauchement de sessions";

	// =============== CONTRAINTES DES JOUEURS =============== //
	public static final String PLAYER_AVAILABILITY = "Disponibilité du joueur";
	public static final String PLAYER_SINGLE_SESSION_PER_DAY = "Une seule session par jour pour un joueur";



	// =============== CONTRAINTES DES ENTRAINEURS =============== //



}
