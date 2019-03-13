package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.entities.Race;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = RaceServiceTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RaceServiceTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.5")
            .withDatabaseName("racing");

    @Autowired
    private RaceRepository repository;

    @Test
    public void test() {
        System.out.println("Hallo");
        final List<Race> all = repository.findAll();
        assert (all.size() == 0);

        repository.save(new Race());
        final List<Race> allTwo = repository.findAll();
        assert (allTwo.size() == 0);
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
