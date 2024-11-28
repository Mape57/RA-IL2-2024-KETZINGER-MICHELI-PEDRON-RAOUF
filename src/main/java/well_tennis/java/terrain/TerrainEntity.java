package well_tennis.java.terrain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import well_tennis.java.horaire.HoraireEntity;
import well_tennis.java.seance.SeanceEntity;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "terrain")
public class TerrainEntity implements Serializable {
    @Id
    private String id;
    private String nom;
    private List<HoraireEntity> horaires;
    private List<SeanceEntity> seances;
}