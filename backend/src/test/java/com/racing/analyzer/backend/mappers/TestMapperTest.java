package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


public class TestMapperTest {

    @Test
    public void test() {
        TestMapper mapper = Mappers.getMapper(TestMapper.class);

        LiveTiming timing = LiveTiming.builder()
                .name("Name")
                .build();
        assertThat(mapper.toDto(timing).getDriverName()).isEqualTo("Name");
    }
}