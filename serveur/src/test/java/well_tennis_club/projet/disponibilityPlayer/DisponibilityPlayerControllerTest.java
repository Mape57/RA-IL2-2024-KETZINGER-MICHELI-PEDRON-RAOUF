package well_tennis_club.projet.disponibilityPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import well_tennis_club.projet.controller.DisponibilityPlayerController;
import well_tennis_club.projet.dto.DisponibilityPlayerDto;
import well_tennis_club.projet.mapper.DisponibilityPlayerMapper;
import well_tennis_club.projet.service.ConnectionService;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.service.DisponibilityPlayerService;

import java.util.List;
import java.util.UUID;

@Disabled
@WebMvcTest(DisponibilityPlayerController.class)
public class DisponibilityPlayerControllerTest {
    @MockBean
    private DisponibilityPlayerService service;

    @MockBean
    private ConnectionService connectionService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDisponibilityPlayer() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        DisponibilityPlayerDto disponibilityPlayerDto = new DisponibilityPlayerDto();
        disponibilityPlayerDto.setIdDisponibility(id1);
        disponibilityPlayerDto.setIdPlayer(id2);

        List<DisponibilityPlayerDto> list = List.of(disponibilityPlayerDto);

        Mockito.when(service.getDisponibilitiesForPlayer(Mockito.any())).thenReturn(DisponibilityPlayerMapper.INSTANCE.mapToListEntity(list));

        mockMvc.perform(get("/disponibilityPlayer/"+id2))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDisponibilityPlayer() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        DisponibilityPlayerDto disponibilityPlayerDto = new DisponibilityPlayerDto();
        disponibilityPlayerDto.setIdDisponibility(id1);
        disponibilityPlayerDto.setIdPlayer(id2);

        mockMvc.perform(post("/disponibilityPlayer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(disponibilityPlayerDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).createDisponibilityPlayer(Mockito.any());
    }

    @Test
    void testDeleteDisponibilityPlayer() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        DisponibilityPlayerDto disponibilityPlayerDto = new DisponibilityPlayerDto();
        disponibilityPlayerDto.setIdDisponibility(id1);
        disponibilityPlayerDto.setIdPlayer(id2);

        mockMvc.perform(delete("/disponibilityPlayer/"+id2+"/"+id1))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).deleteDisponibilityPlayer(Mockito.any());
    }
}
