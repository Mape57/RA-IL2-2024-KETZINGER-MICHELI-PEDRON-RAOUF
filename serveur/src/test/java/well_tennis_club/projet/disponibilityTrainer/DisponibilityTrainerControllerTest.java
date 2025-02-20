package well_tennis_club.projet.disponibilityTrainer;

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
import well_tennis_club.projet.controller.DisponibilityTrainerController;
import well_tennis_club.projet.dto.DisponibilityTrainerDto;
import well_tennis_club.projet.mapper.DisponibilityTrainerMapper;
import well_tennis_club.projet.service.ConnectionService;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.service.DisponibilityTrainerService;

import java.util.List;
import java.util.UUID;

@Disabled
@WebMvcTest(DisponibilityTrainerController.class)
public class DisponibilityTrainerControllerTest {
    @MockBean
    private DisponibilityTrainerService service;
    @MockBean
    private ConnectionService connectionService;
    @MockBean
    private JwtUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDisponibilityTrainer() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        DisponibilityTrainerDto disponibilityTrainerDto = new DisponibilityTrainerDto();
        disponibilityTrainerDto.setIdDisponibility(id1);
        disponibilityTrainerDto.setIdTrainer(id2);

        List<DisponibilityTrainerDto> list = List.of(disponibilityTrainerDto);

        Mockito.when((service.getDisponibilityForTrainer(Mockito.any()))).thenReturn(DisponibilityTrainerMapper.INSTANCE.mapToListEntity(list));

        mockMvc.perform(get("/disponibilityTrainer/"+id2))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDisponibilityTrainer() throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        DisponibilityTrainerDto disponibilityTrainerDto = new DisponibilityTrainerDto();
        disponibilityTrainerDto.setIdDisponibility(id1);
        disponibilityTrainerDto.setIdTrainer(id2);

        mockMvc.perform(post("/disponibilityTrainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(disponibilityTrainerDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).createDisponibilityTrainer(Mockito.any());
    }

    @Test
    void testDeleteDisponibilityTrainer()throws Exception{
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        DisponibilityTrainerDto disponibilityTrainerDto = new DisponibilityTrainerDto();
        disponibilityTrainerDto.setIdTrainer(id1);
        disponibilityTrainerDto.setIdDisponibility(id2);

        mockMvc.perform(delete("/disponibilityTrainer/"+id1+"/"+id2))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).deleteDisponibilityTrainer(Mockito.any());
    }
}
