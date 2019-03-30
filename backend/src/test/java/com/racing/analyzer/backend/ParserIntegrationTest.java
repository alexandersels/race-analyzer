package com.racing.analyzer.backend;

import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import com.racing.analyzer.backend.logic.aggregators.RaceOverviewAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ParserIntegrationTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParserIntegrationTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:5.7")
            .withDatabaseName("racing");

    @Autowired
    private RaceRepository raceRepository;

    @Test
    @Sql({"/RaceDemoData.sql", "/LiveTimingDemoData.sql"})
    public void run() {
        Optional<Race> race = raceRepository.findById(1L);
        RaceOverviewDTO raceData = RaceOverviewAggregator.aggregate(race.get());
        assertThat(raceData.getAmountOfDrivers()).isEqualTo(32);
        assertThat(raceData.getAmountOfRounds()).isEqualTo(978);
        assertThat(raceData.getAmountOfPitStops()).isEqualTo(35);

        // Verify winner
        assertThat(raceData.getWinner().getNumber()).isEqualTo(130);
        assertThat(raceData.getWinner().getAmountOfRounds()).isEqualTo(38);
        assertThat(raceData.getWinner().getBestLap()).isEqualTo(91125000L);
        assertThat(raceData.getWinner().getName()).isEqualTo("Versluis-Buurman");

        // Verify random participant
        DriverDTO ricWood = raceData.getDrivers().stream().filter(driver -> driver.getNumber() == 123).findFirst().get();
        assertThat(ricWood.getName()).isEqualTo("RIC WOOD");
        assertThat(ricWood.getLastState()).isEqualTo(LiveTimingState.PIT);
        assertThat(ricWood.getBestLap()).isEqualTo(94688000L);
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.show-sql=true"
            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
