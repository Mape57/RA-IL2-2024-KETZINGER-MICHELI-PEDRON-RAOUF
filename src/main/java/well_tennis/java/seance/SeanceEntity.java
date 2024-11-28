package well_tennis.java.seance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SeanceEntity {
    private String jour;
    private String debut;
    private String fin;
    private String entraineur;
    private List<String> joueurs;
}
