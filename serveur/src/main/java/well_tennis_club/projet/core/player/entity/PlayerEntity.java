package well_tennis_club.projet.core.player.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import well_tennis_club.projet.core.disponibility.entity.DisponibilityEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "player")
public class PlayerEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 2405172041950251807L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "birthday")
	private String birthday;

	@Column(name = "courses")
	private Long courses;

	@Column(name = "level")
	private Long level;

	@Column(name = "email")
	private String email;

	@Column(name = "validate")
	private Boolean validate;

	@Column(name = "phone")
	private String phone;

	@Column(name = "phone2")
	private String phone2;

	@Column(name = "photo")
	private boolean photo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@JoinTable(
			name = "disponibility_player",
			joinColumns = @JoinColumn(name = "id_player", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_disponibility", referencedColumnName = "id")
	)
	private List<DisponibilityEntity> disponibilities = new ArrayList<>();
}
