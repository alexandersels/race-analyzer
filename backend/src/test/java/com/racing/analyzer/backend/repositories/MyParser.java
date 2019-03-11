package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.Readers.LiveTimingCSVReader;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.logic.RoundParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MyParser {

    private Collection<LiveTiming> inputData = new ArrayList<>();

    @Before
    public void init() {
        inputData = LiveTimingCSVReader.parseTimings("LiveTrackingDemoData.csv");
    }

    @Test
    public void test() {

        Map<Integer, List<LiveTiming>> mappingOne = inputData.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        int totalOne = 0;
        for(int key: mappingOne.keySet()) {
            int x = RoundParser.createRoundDataForSpecificDriver(mappingOne.get(key)).size();
            totalOne += x;
        }

        assertThat(totalOne).isEqualTo(978);

    }


}
