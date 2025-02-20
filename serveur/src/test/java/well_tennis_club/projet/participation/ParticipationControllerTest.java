package well_tennis_club.projet.participation;
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
import well_tennis_club.projet.controller.ParticipationController;
import well_tennis_club.projet.dto.ParticipationDto;
import well_tennis_club.projet.mapper.ParticipationMapper;
import well_tennis_club.projet.service.ConnectionService;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.service.ParticipationService;

import java.util.List;
import java.util.UUID;

@Disabled
@WebMvcTest(ParticipationController.class)
public class ParticipationControllerTest {
    @MockBean
    private ParticipationService service;
    @MockBean
    private ConnectionService connectionService;
    @MockBean
    private JwtUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetParticipation() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        ParticipationDto participationDto = new ParticipationDto();
        participationDto.setIdPlayer(id1);
        participationDto.setIdSession(id2);

        List<ParticipationDto> list = List.of(participationDto);

        Mockito.when(service.getParticipationForPlayer(Mockito.any())).thenReturn(ParticipationMapper.INSTANCE.mapToListEntity(list));

        mockMvc.perform(get("/participations/"+id1))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateParticipation() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ParticipationDto participationDto = new ParticipationDto();
        participationDto.setIdPlayer(id1);
        participationDto.setIdSession(id2);

        mockMvc.perform(post("/participations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(participationDto)))
                .andExpect(status().isOk());

        Mockito.verify(service,Mockito.times(1)).createParticipation(Mockito.any());
    }

    @Test
    void testDeleteParticipation()throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ParticipationDto participationDto = new ParticipationDto();
        participationDto.setIdPlayer(id1);
        participationDto.setIdSession(id2);

        mockMvc.perform(delete("/participations/"+id1+"/"+id2))
                .andExpect(status().isOk());

        Mockito.verify(service,Mockito.times(1)).deleteParticipation(Mockito.any());
    }
}
