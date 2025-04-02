package well_tennis_club.timefold.data_structure;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * Classe représentant une plage de valeur
 */
@Getter
public class ValueRange implements Serializable {
	private final Integer min;
	private final Integer max;

	/**
	 * Constructeur de la classe ValueRange
	 * Plage de valeur par défaut : min = Integer.MIN_VALUE et max = Integer.MAX_VALUE
	 */
	public ValueRange() {
		this.min = Integer.MIN_VALUE;
		this.max = Integer.MAX_VALUE;
	}

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
		return this.min <= value && value <= this.max;
	}

	/**
	 * Méthode permettant de savoir si une plage est contenue dans la plage
	 *
	 * @param valueRange plage à tester
	 * @return true si la plage est contenue dans la plage, false sinon
	 */
	// TODO tester la méthode
	public boolean contains(ValueRange valueRange) {
		return this.min <= valueRange.min && valueRange.max <= this.max;
	}

	/**
	 * Méthode permettant de savoir si une liste de valeurs est contenue dans la plage
	 *
	 * @param values liste de valeurs à tester
	 * @return true si toutes les valeurs sont contenues dans la plage, false sinon
	 */
	// TODO tester la méthode
	public boolean contains(List<Integer> values) {
		return values.stream().allMatch(this::contains);
	}

	/**
	 * Méthode permettant de connaître la différence entre une valeur et la plage
	 *
	 * @param value valeur à comparer
	 * @return 0 : si la valeur est dans la plage<br>
	 * x : la différence entre la valeur et la plage
	 */
	public int difference(Integer value) {
		if (value < this.min) return this.min - value;
		else if (value > this.max) return value - this.max;
		else return 0;
	}

	/**
	 * Méthode permettant de connaître la taille de la plage
	 *
	 * @return la taille de la plage
	 */
	public Integer size() {
		return this.max - this.min + 1;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ValueRange valueRange = (ValueRange) o;
		return min.equals(valueRange.min) && max.equals(valueRange.max);
	}

	@Override
	public String toString() {
		return "[" + min + ", " + max + "]";
	}
}
