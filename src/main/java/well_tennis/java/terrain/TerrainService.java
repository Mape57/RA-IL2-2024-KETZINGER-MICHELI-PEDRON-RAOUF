package well_tennis.java.terrain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerrainService {

    public final TerrainRepository terrainRepository;
    @Autowired
    public TerrainService(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    public List<TerrainEntity> getAllTerrains() {
        return terrainRepository.findAll();
    }
}
