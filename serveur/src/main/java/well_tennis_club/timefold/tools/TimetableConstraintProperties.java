package well_tennis_club.timefold.tools;

public class TimetableConstraintProperties {
	// =============== CONTRAINTES DES GROUPES =============== //
	public static final String GROUP_AGE_DIFFERENCE = "Difference d'âge du groupe";										// SOFT 10
	public static final String GROUP_LEVEL_DIFFERENCE = "Difference de niveau du groupe";								// SOFT 10
	public static final String GROUP_SIZE = "Taille du groupe";															// SOFT 50
	public static final String GROUP_SESSION_CONSTRAINT = "Même contraintes de session pour un groupe";					// HARD 100


	// =============== CONTRAINTES DES SESSIONS =============== //
	public static final String SESSION_TRAINER_WITHOUT_PLAYERS = "Présence d'un entraineur pour une session vide";		// SOFT (facilement annulable)
	public static final String SESSION_WITH_PLAYERS_NEED_TRAINER = "Présence de joueurs sans entraineur";				// HARD 1000
	public static final String SESSION_OVERLAPPING = "Chevauchement de sessions";										// HARD 1000


	// =============== CONTRAINTES DES JOUEURS =============== //
	public static final String PLAYER_AVAILABILITY = "Disponibilité du joueur";											// HARD 100
	public static final String PLAYER_SINGLE_SESSION_PER_DAY = "Une seule session par jour pour un joueur";				// SOFT 100


	// =============== CONTRAINTES DES ENTRAINEURS =============== //
	public static final String TRAINER_AVAILABILITY = "Disponibilité de l'entraineur";									// HARD 10
	public static final String TRAINER_PREFERED_AGE = "Age préféré de l'entraineur";									// SOFT 10
	public static final String TRAINER_PREFERED_LEVEL = "Niveau préféré de l'entraineur";								// SOFT 10
	public static final String TRAINER_WEEKLY_HOURS = "Heures hebdomadaires de l'entraineur";							// HARD 100
	public static final String TRAINER_WEEKLY_HOURS_WITH_NO_PLANIFICATION = "Heures hebdomadaires non planifiées de l'entraineur";
	public static final String TRAINER_SESSION_OVERLAPPING = "Chevauchement de sessions de l'entraineur";				// HARD 100
}
