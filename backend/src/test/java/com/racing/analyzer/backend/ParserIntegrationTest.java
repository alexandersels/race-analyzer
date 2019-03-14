package com.racing.analyzer.backend;

import com.racing.analyzer.backend.dto.DriverDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.logic.DriverParser;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
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

import java.util.Collection;
import java.util.List;

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

    @Autowired
    private LiveTimingRepository liveTimingRepository;

    @Test
    @Sql({"/RaceDemoData.sql","/LiveTimingDemoData.sql"})
    public void run() {
        List<LiveTiming> allTimings = liveTimingRepository.findAll();
        final Collection<DriverDTO> driverData = DriverParser.createDriverData(allTimings);
        assertThat(driverData.size()).isEqualTo(32);
        assertThat(driverData.stream().mapToInt(d -> d.rounds.size()).sum()).isEqualTo(978);
        assertThat(driverData.stream().mapToInt(d -> d.pitStops).sum()).isEqualTo(35);
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
