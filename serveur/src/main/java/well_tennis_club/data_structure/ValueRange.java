package well_tennis_club.data_structure;

import lombok.Getter;

import java.util.List;

/**
 * Classe représentant une plage de valeur
 */
@Getter
public class ValueRange {
	private final Integer min;
	private final Integer max;

	/**
	 * Constructeur de la classe ValueRange
	 *
	 * @param min minimum de la plage
	 * @param max maximum de la plage
	 */
	public ValueRange(Integer min, Integer max) {
		if (min == null || max == null) {
			throw new IllegalArgumentException("Les valeurs ne peuvent pas être nulles");
		}
		if (min > max) {
			throw new IllegalArgumentException("La première valeur doit être inférieure ou égale à la deuxième valeur");
		}
		this.min = min;
		this.max = max;
	}

	/**
	 * Constructeur de la classe ValueRange
	 * Calcul le minimum et le maximum de la liste de valeurs
	 * En cas de liste vide, min = Integer.MAX_VALUE et max = Integer.MIN_VALUE
	 *
	 * @param values liste de valeurs
	 */
	public ValueRange(List<Integer> values) {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (Integer value : values) {
			if (value < min) min = value;
			if (value > max) max = value;
		}
		this.min = min;
		this.max = max;
	}

	/**
	 * Méthode permettant de savoir si une valeur est contenue dans la plage
	 *
	 * @param value valeur à tester
	 * @return true si la valeur est contenue dans la plage, false sinon
	 */
	public boolean contains(Integer value) {
		return min <= value && value <= max;
	}

	/**
	 * Méthode permettant de connaître la taille de la plage
	 *
	 * @return la taille de la plage
	 */
	public Integer size() {
		return max - min + 1;
	}
}
