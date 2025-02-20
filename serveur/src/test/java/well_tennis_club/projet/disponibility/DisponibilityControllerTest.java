package well_tennis_club.projet.disponibility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import well_tennis_club.projet.controller.DisponibilityController;
import well_tennis_club.projet.dto.DisponibilityDto;
import well_tennis_club.projet.entity.DisponibilityEntity;
import well_tennis_club.projet.mapper.DisponibilityMapper;
import well_tennis_club.projet.service.ConnectionService;
import well_tennis_club.projet.config.JwtUtils;
import well_tennis_club.projet.service.DisponibilityService;

import java.util.UUID;

@Disabled
@WebMvcTest(DisponibilityController.class)
public class DisponibilityControllerTest {
    @MockBean
    private DisponibilityService service;

    @MockBean
    private ConnectionService connectionService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDisponibility() throws Exception {
        UUID id = UUID.randomUUID();
        DisponibilityDto disponibilityDto = new DisponibilityDto();
        disponibilityDto.setId(id);
        disponibilityDto.setDayWeek(1);
        disponibilityDto.setOpen("08:00");
        disponibilityDto.setClose("20:00");

        Mockito.when(service.getDisponibilityById(Mockito.any())).thenReturn(DisponibilityMapper.INSTANCE.mapToEntity(disponibilityDto));

        mockMvc.perform(get("/disponibility/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDisponibility() throws Exception{
        DisponibilityDto disponibilityDto = new DisponibilityDto();
        disponibilityDto.setDayWeek(1);
        disponibilityDto.setOpen("08:00");
        disponibilityDto.setClose("20:00");

        mockMvc.perform(post("/disponibility")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(disponibilityDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).createDisponibility(Mockito.any());
    }

    @Test
    void testUpdateDisponiblity()throws Exception{
        UUID id = UUID.randomUUID();
        DisponibilityDto disponibilityDto = new DisponibilityDto();
        disponibilityDto.setId(id);
        disponibilityDto.setDayWeek(1);
        disponibilityDto.setOpen("08:00");
        disponibilityDto.setClose("20:00");

        DisponibilityEntity disponibilityEntity = new DisponibilityEntity();
        disponibilityEntity.setId(id);
        disponibilityEntity.setDayWeek(2);
        disponibilityEntity.setOpen("08:00");
        disponibilityEntity.setClose("20:00");

        Mockito.when(service.getDisponibilityById(id)).thenReturn(disponibilityEntity);

        Mockito.doAnswer(invocation->{
            disponibilityDto.setDayWeek(2);
            return null;
        }).when(service).updateDisponibility(Mockito.any());

        mockMvc.perform(patch("/disponibility/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(disponibilityDto)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).updateDisponibility(Mockito.any());
        Assertions.assertEquals("Tuesday", disponibilityDto.getDayWeek());
    }

    @Test
    void testDeleteDisponibility() throws Exception {
        UUID id = UUID.randomUUID();

        DisponibilityDto disponibilityDto = new DisponibilityDto();
        disponibilityDto.setId(id);

        mockMvc.perform(delete("/disponibility/" + id))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).deleteDisponibility(Mockito.any());
    }
}
