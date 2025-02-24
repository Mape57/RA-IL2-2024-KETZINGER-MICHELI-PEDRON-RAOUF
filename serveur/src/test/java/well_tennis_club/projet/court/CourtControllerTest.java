package well_tennis_club.projet.court;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import well_tennis_club.projet.core.court.CourtController;
import well_tennis_club.projet.core.court.CourtDto;
import well_tennis_club.projet.core.court.CourtEntity;
import well_tennis_club.projet.core.court.CourtMapper;
import well_tennis_club.projet.core.trainer.service.ConnectionService;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.core.court.CourtService;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@WebMvcTest(CourtController.class)
public class CourtControllerTest {
    @MockBean
    private CourtService service;

    @MockBean
    private ConnectionService connectionService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCourt() throws Exception {
        UUID id = UUID.randomUUID();
        CourtDto courtDto = new CourtDto();
        courtDto.setId(id);
        courtDto.setName("Terrain 1");

        Mockito.when(service.getCourtById(Mockito.any())).thenReturn(CourtMapper.INSTANCE.mapToEntity(courtDto));

        mockMvc.perform(get("/courts/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateCourt() throws Exception{
        CourtDto courtDto = new CourtDto();
        courtDto.setName("New Court");

        mockMvc.perform(post("/courts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courtDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).createCourt(Mockito.any());
    }

    @Test
    void testUpdateCourt() throws Exception {
        UUID id = UUID.randomUUID();
        CourtDto courtDto = new CourtDto();
        courtDto.setId(id);
        courtDto.setName("Terrain 1");
        courtDto.setTimes(new ArrayList<>());

        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setId(id);
        courtEntity.setName("Terrain 1");
        courtEntity.setTimes(new ArrayList<>());

        Mockito.when(service.getCourtById(id)).thenReturn(courtEntity);

        Mockito.doAnswer(invocation->{
            courtDto.setName("Updated Terrain 1");
            return null;
        }).when(service).updateCourt(Mockito.any());

        mockMvc.perform(patch("/courts/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courtDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).updateCourt(Mockito.any());
        Assertions.assertEquals("Updated Terrain 1", courtDto.getName());
    }

    @Test
    void testDeleteCourts() throws Exception {
        UUID id = UUID.randomUUID();

        CourtDto courtDto = new CourtDto();
        courtDto.setId(id);

        mockMvc.perform(delete("/courts/" + id))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).deleteCourt(Mockito.any());
    }
}
