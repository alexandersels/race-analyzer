package com.racing.analyzer.backend.Readers;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.LiveTimingState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class LiveTimingCSVReader {

    public static Collection<LiveTiming> parseTimings(String fileName) {

        List<LiveTiming> data = new ArrayList<>();
        ClassLoader classLoader = LiveTimingCSVReader.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                data.add(parse(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static LiveTiming parse(String data) {

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

    private static LiveTimingState getStateFrom(String value) {
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
}
