package well_tennis.java.terrain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("terrain")
public class TerrainController {

    public final TerrainService terrainService;
    @Autowired
    public TerrainController(TerrainService terrainService) {
        this.terrainService = terrainService;
    }

    @CrossOrigin
    @Operation(summary = "Avoir tous les terrains", description = "Retourne tous les terrains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupéré avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur - Aucun terrain n'a pas été trouvé")
    })
    @GetMapping("/all")
    public List<TerrainDto> getAllTerrains() {
        List<TerrainDto> liste = TerrainMapper.INSTANCE.mapToListDTO(terrainService.getAllTerrains());
        if (liste == null) {
            throw new RuntimeException("Aucun terrain n'a pas été trouvé");
        }
        return liste;
    }

    @CrossOrigin
    @Operation(summary = "Tester la connexion", description = "Retourne un message de test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupéré avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur - Aucun terrain n'a pas été trouvé")
    })
    @GetMapping("/test")
    public String test() {
        return "Connexion réussie";
    }
}
