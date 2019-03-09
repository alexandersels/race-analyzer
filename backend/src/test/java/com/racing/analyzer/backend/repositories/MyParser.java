package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MyParser {

    private List<LiveTiming> inputData = new ArrayList<>();
    private List<LiveTiming> inputDataWithoutCache = new ArrayList<>();

    @Before
    public void init() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("TestData.csv").getFile());
        File fileTwo = new File(classLoader.getResource("DemoExportWithoutCaching.csv").getFile());

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                inputData.add(parse(line));
            }
        }

        try (Scanner scanner = new Scanner(fileTwo)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                inputDataWithoutCache.add(parse(line));
            }
        }
    }

    @Test
    public void test() {
        Map<Integer, List<LiveTiming>> mapping = inputData.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        Map<Integer, List<LiveTiming>> mappingNoCache = inputDataWithoutCache.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        Collection<Round> rounds = mapping.entrySet()
                .stream()
                .flatMap(x -> lapsDone(x.getValue()))
                .collect(Collectors.toList());

        Collection<Round> roundsNoCache = mappingNoCache.entrySet()
                .stream()
                .flatMap(x -> lapsDone(x.getValue()))
                .collect(Collectors.toList());

        int lapsDone = rounds.size();
        int pitStops = rounds.stream().mapToInt(x -> x.inPit ? 1 : 0).sum();

        int lapsNoCache = roundsNoCache.size();
        int pitStopsNoCache = roundsNoCache.stream().mapToInt(x -> x.inPit ? 1 : 0).sum();

        assertThat(inputData.size()).isEqualTo(1183); // # input sets
        assertThat(mapping.keySet().size()).isEqualTo(32); // # divers
        assertThat(lapsDone).isEqualTo(978); // # rounds
        assertThat(pitStops).isEqualTo(35); // # pit stops

        assertThat(mappingNoCache.keySet().size()).isEqualTo(32);
        assertThat(lapsNoCache).isEqualTo(978); // # rounds
        assertThat(pitStopsNoCache).isEqualTo(35); // # pit stops



    }

    private Stream<Round> lapsDone(Collection<LiveTiming> liveTimings) {
        List<Round> rounds = new ArrayList<>();

        LiveTiming previousTiming = null;
        for (LiveTiming liveTiming : liveTimings) {

            if (previousTiming == null) {
                previousTiming = liveTiming;
            }

            if (previousTiming.getLastTime() != liveTiming.getLastTime()) {
                previousTiming = liveTiming;
                rounds.add(new Round(liveTiming.getLastTime(), liveTiming.isInPit()));
            }
        }

        return rounds.stream();
    }

    private LiveTiming parse(String data) {

        String[] split = data.split(";");

        return LiveTiming.getBuilder()
                .withPosition(Integer.parseInt(split[9]))
                .withNumber(Integer.parseInt(split[8]))
                .withName(split[6])
                .withCls(split[3])
                .withLastLapTime(Integer.parseInt(split[5]))
                .withBestLapTime(Integer.parseInt(split[2]))
                .withNationality(split[7])
                .withCar(split[10])
                .withState(getStateFrom(split[11]))
                .inPit(Integer.parseInt(split[4].replace("\"", "")) == 1)
                .withSectorOne(Integer.parseInt(split[12]))
                .withSectorTwo(Integer.parseInt(split[13]))
                .withSectorThree(Integer.parseInt(split[14]))
                .build();
    }

    private LiveTimingState getStateFrom(String value) {
        switch (value) {
            case "1":
                return LiveTimingState.RACING;
            case "2":
                return LiveTimingState.PIT;
            case "3":
                return LiveTimingState.OUTLAP;
            case "4":
                return LiveTimingState.FINISHED;
            default:
                return LiveTimingState.UNKNOWN;
        }
    }

    private class Round {

        public long laptTime;
        public boolean inPit;

        public Round(long lapTime, boolean inPit) {
            this.laptTime = lapTime;
            this.inPit = inPit;

        }
    }
}
