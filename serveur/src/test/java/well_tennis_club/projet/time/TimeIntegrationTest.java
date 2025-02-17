package well_tennis_club.projet.time;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class TimeIntegrationTest {
    @Autowired
    private TimeService service;

    @Test
    @Transactional
    void testTimeService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetTime(){
        TimeEntity time = service.getTimeById(UUID.fromString("b8b5a1c5-0020-4cd1-bed6-bd07a7623cdd"));
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getDayWeek()).isEqualTo(1);
        Assertions.assertThat(time.getStart()).isEqualTo("08:00");
        Assertions.assertThat(time.getStop()).isEqualTo("12:00");
    }

    @Test
    @Transactional
    void testCreateTime(){
        UUID id = UUID.randomUUID();

        TimeEntity newTime = new TimeEntity();
        newTime.setDayWeek(5);
        newTime.setStart("08:00");
        newTime.setStop("18:00");
        newTime.setId(id);
        TimeEntity timeEntity = service.createTime(newTime);

        TimeEntity time = service.getTimeById(id);
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getDayWeek()).isEqualTo(5);
        Assertions.assertThat(time.getStart()).isEqualTo("08:00");
        Assertions.assertThat(time.getStop()).isEqualTo("18:00");

        service.deleteTime(timeEntity);
    }

    @Test
    @Transactional
    void testUpdateTime(){
        TimeEntity updatedTime = new TimeEntity();
        TimeEntity time = service.getTimeById(UUID.fromString("b8b5a1c5-0020-4cd1-bed6-bd07a7623cdd"));
        Assertions.assertThat(time.getDayWeek()).isEqualTo(1);

        updatedTime.setId(time.getId());
        updatedTime.setDayWeek(2);
        updatedTime.setStart("09:00");
        updatedTime.setStop("13:00");
        TimeEntity timeEntity = service.updateTime(updatedTime);

        TimeEntity timeUpdated = service.getTimeById(time.getId());
        Assertions.assertThat(timeUpdated).isNotNull();
        Assertions.assertThat(timeUpdated.getDayWeek()).isEqualTo(2);
        Assertions.assertThat(timeUpdated.getStart()).isEqualTo("09:00");
        Assertions.assertThat(timeUpdated.getStop()).isEqualTo("13:00");
    }

    @Test
    @Transactional
    void testDeleteTime(){
        UUID id = UUID.randomUUID();

        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setDayWeek(5);
        timeEntity.setStart("08:00");
        timeEntity.setStop("18:00");
        timeEntity.setId(id);
        service.createTime(timeEntity);

        TimeEntity time = service.getTimeById(id);
        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getDayWeek()).isEqualTo(5);
        Assertions.assertThat(time.getStart()).isEqualTo("08:00");
        Assertions.assertThat(time.getStop()).isEqualTo("18:00");

        service.deleteTime(timeEntity);
        time = service.getTimeById(id);
        Assertions.assertThat(time).isNull();
    }

    @Test
    @Transactional
    void testGetAllTimes(){
        Assertions.assertThat(service.getAllTimes()).isNotNull();
        Assertions.assertThat(service.getAllTimes().size()).isEqualTo(2);
    }
}
