package well_tennis_club.projet.sessionConstraint;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import well_tennis_club.projet.core.session_constraint.SessionConstraintEntity;
import well_tennis_club.projet.core.player.service.PlayerService;
import well_tennis_club.projet.core.session_constraint.SessionConstraintService;

import java.util.UUID;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class SessionConstraintIntegrationTest {
    @Autowired
    private SessionConstraintService service;
    @Autowired
    private PlayerService playerService;

    @Test
    @Transactional
    void testConstraintService(){
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @Transactional
    void testGetConstraint(){
        SessionConstraintEntity constraint = service.getConstraintById(UUID.fromString("92a6132e-50a7-4657-9a58-325a0cca8544"));
        Assertions.assertThat(constraint).isNotNull();
        Assertions.assertThat(constraint.getInfAge()).isEqualTo(18);
        Assertions.assertThat(constraint.getSupAge()).isEqualTo(99);
    }

    @Test
    @Transactional
    void testCreateConstraint(){
        UUID id = UUID.randomUUID();

        SessionConstraintEntity newConstraint = new SessionConstraintEntity();
        newConstraint.setInfAge(18);
        newConstraint.setSupAge(99);
        newConstraint.setInfLevel(15);
        newConstraint.setSupLevel(20);
        newConstraint.setAgeDiff(10);
        newConstraint.setLevelDiff(2);
        newConstraint.setDuration(2);
        newConstraint.setId(id);
        SessionConstraintEntity constraintEntity = service.createConstraint(newConstraint);

        SessionConstraintEntity constraint = service.getConstraintById(id);
        Assertions.assertThat(constraint).isNotNull();
        Assertions.assertThat(constraint.getInfLevel()).isEqualTo(15);
        Assertions.assertThat(constraint.getSupLevel()).isEqualTo(20);

        service.deleteById(constraintEntity.getId());
    }

    @Test
    @Transactional
    void testUpdateConstraint(){
        SessionConstraintEntity updatedConstraint = new SessionConstraintEntity();
        SessionConstraintEntity constraint = service.getConstraintById(UUID.fromString("92a6132e-50a7-4657-9a58-325a0cca8544"));
        Assertions.assertThat(constraint).isNotNull();
        Assertions.assertThat(constraint.getInfLevel()).isEqualTo(1);
        Assertions.assertThat(constraint.getSupLevel()).isEqualTo(10);

        updatedConstraint.setId(UUID.fromString("92a6132e-50a7-4657-9a58-325a0cca8544"));
        updatedConstraint.setInfLevel(15);
        updatedConstraint.setSupLevel(20);
        updatedConstraint.setAgeDiff(constraint.getAgeDiff());
        updatedConstraint.setLevelDiff(constraint.getLevelDiff());
        updatedConstraint.setDuration(constraint.getDuration());
        updatedConstraint.setInfAge(constraint.getInfAge());
        updatedConstraint.setSupAge(constraint.getSupAge());
        service.updateConstraint(updatedConstraint);

        constraint = service.getConstraintById(UUID.fromString("92a6132e-50a7-4657-9a58-325a0cca8544"));
        Assertions.assertThat(constraint).isNotNull();
        Assertions.assertThat(constraint.getInfLevel()).isEqualTo(15);
        Assertions.assertThat(constraint.getSupLevel()).isEqualTo(20);
    }

    @Test
    @Transactional
    void testDeleteConstraint(){
        UUID id = UUID.randomUUID();

        SessionConstraintEntity newConstraint = new SessionConstraintEntity();
        newConstraint.setInfAge(18);
        newConstraint.setSupAge(99);
        newConstraint.setInfLevel(15);
        newConstraint.setSupLevel(20);
        newConstraint.setAgeDiff(10);
        newConstraint.setLevelDiff(2);
        newConstraint.setDuration(2);
        newConstraint.setId(id);
        SessionConstraintEntity constraintEntity = service.createConstraint(newConstraint);

        SessionConstraintEntity constraint = service.getConstraintById(id);
        Assertions.assertThat(constraint).isNotNull();
        Assertions.assertThat(constraint.getInfLevel()).isEqualTo(15);
        Assertions.assertThat(constraint.getSupLevel()).isEqualTo(20);

        service.deleteById(constraintEntity.getId());
        constraint = service.getConstraintById(id);
        Assertions.assertThat(constraint).isNull();
    }

    @Test
    @Transactional
    void testGetAllConstraints(){
        Assertions.assertThat(service.getAllConstraints()).isNotNull();
        Assertions.assertThat(service.getAllConstraints().size()).isEqualTo(2);
    }
}
