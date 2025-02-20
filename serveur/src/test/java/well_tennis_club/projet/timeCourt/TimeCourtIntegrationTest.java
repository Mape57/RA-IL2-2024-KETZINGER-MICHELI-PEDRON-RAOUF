package well_tennis_club.projet.timeCourt;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.entity.TimeCourtEntity;
import well_tennis_club.projet.key.TimeCourtKey;
import well_tennis_club.projet.service.TimeCourtService;

import java.util.List;
import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class TimeCourtIntegrationTest {
    @Autowired
    private TimeCourtService service;

    @Test
    @Transactional
    void testTimeCourtService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetTimeCourt(){
        List<TimeCourtEntity> timeCourt = service.getTimeCourtForCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(timeCourt).isNotNull();
        Assertions.assertThat(timeCourt.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testCreateTimeCourt(){
        TimeCourtEntity newTimeCourt = new TimeCourtEntity();
        TimeCourtKey key = new TimeCourtKey();
        key.setIdCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        key.setIdTime(UUID.fromString("1c728dab-df0f-4630-a0b1-9da99f4c4619"));
        newTimeCourt.setTimeCourtKey(key);
        TimeCourtEntity timeCourtEntity = service.createTimeCourt(newTimeCourt);

        List<TimeCourtEntity> timeCourt = service.getTimeCourtForCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(timeCourt).isNotNull();
        Assertions.assertThat(timeCourt.size()).isEqualTo(2);

        service.deleteTimeCourt(timeCourtEntity);
    }

    @Test
    @Transactional
    void testDeleteTimeCourt(){
        TimeCourtEntity newTimeCourt = new TimeCourtEntity();
        TimeCourtKey key = new TimeCourtKey();
        key.setIdCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        key.setIdTime(UUID.fromString("1c728dab-df0f-4630-a0b1-9da99f4c4619"));
        newTimeCourt.setTimeCourtKey(key);
        TimeCourtEntity timeCourtEntity = service.createTimeCourt(newTimeCourt);

        List<TimeCourtEntity> timeCourt = service.getTimeCourtForCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(timeCourt).isNotNull();
        Assertions.assertThat(timeCourt.size()).isEqualTo(2);

        service.deleteTimeCourt(timeCourtEntity);

        timeCourt = service.getTimeCourtForCourt(UUID.fromString("4f8535fd-9b6c-4bb7-b0c7-e988cf039d83"));
        Assertions.assertThat(timeCourt).isNotNull();
        Assertions.assertThat(timeCourt.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void testGetAllTimeCourt(){
        List<TimeCourtEntity> timeCourt = service.getAllTimeCourt();
        Assertions.assertThat(timeCourt).isNotNull();
        Assertions.assertThat(timeCourt.size()).isEqualTo(1);
    }
}
