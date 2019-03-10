package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.LiveTimingState;
import com.racing.analyzer.backend.enums.ParseType;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveTimingParser {

    private static final Pattern PATTERN = Pattern.compile("raceResultsViewModel\\.r_c\\(.*\\);");
    private static final long MAX_LONG = 9223372036854775807L;
    private static LiveTimingCache dataCache = new LiveTimingCache();

    public static void clearCache() {
        dataCache.clear();
    }

    public static Collection<LiveTiming> parse(Document document) {
        String text = getRelevantData(document);
        if (text != null) {
            return extractLiveTimings(text);
        } else {
            return Collections.emptyList();
        }
    }

    private static String getRelevantData(Document document) {
        Elements scriptElements = document.getElementsByTag("script");
        for (Element element : scriptElements) {
            for (DataNode node : element.dataNodes()) {
                Matcher matcher = PATTERN.matcher(node.getWholeData());
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return null;
    }

    private static Collection<LiveTiming> extractLiveTimings(String data) {
        data = data.replace("raceResultsViewModel.r_c", "")
                .replace("([", "")
                .replace("], true", "")
                .replace("\"", "");

        String[] splits = data.split("],\\[");
        List<LiveTiming> liveTimings = new ArrayList<>();
        for (int i = 0; i < splits.length; i += 17) {
            LiveTiming liveTiming = buildLiveTiming(splits, i);
            if (liveTiming.getNumber() == 127) {
                dataCache.isNewEntry(liveTiming);
                liveTimings.add(liveTiming);
                //}
            }
        }
        return liveTimings;
    }

    private static LiveTiming buildLiveTiming(String[] splits, int i) {
        return LiveTiming.getBuilder()
                .withPosition(parseIntegerValue(splits[i + ParseType.POSITION.getIndex()]))
                .withNumber(parseIntegerValue(splits[i + ParseType.NUMBER.getIndex()]))
                .withName(parseStringValue(splits[i + ParseType.NAME.getIndex()]))
                .withCls(parseStringValue(splits[i + ParseType.CLS.getIndex()]))
                .withLastLapTime(parseLongValue(splits[i + ParseType.LAST.getIndex()]))
                .withBestLapTime(parseLongValue(splits[i + ParseType.BEST.getIndex()]))
                .withNationality(parseStringValue(splits[i + ParseType.NAT.getIndex()]))
                .withCar(parseStringValue(splits[i + ParseType.CAR.getIndex()]))
                .withState(parseState(splits[i + ParseType.STATE.getIndex()]))
                .inPit(parseInPitTiming(splits[i + ParseType.LAST.getIndex()]))
                .withSectorOne(parseLongValue(splits[i + ParseType.S1.getIndex()]))
                .withSectorTwo(parseLongValue(splits[i + ParseType.S2.getIndex()]))
                .withSectorThree(parseLongValue(splits[i + ParseType.S3.getIndex()]))
                .build();
    }

    private static int parseIntegerValue(String data) {
        data = stripInvalidCharacter(data);
        String[] split = data.split(",");
        return Integer.parseInt(split[2]);
    }

    private static String parseStringValue(String data) {
        data = stripInvalidCharacter(data);
        String[] split = data.split(",");
        return split[2];
    }

    private static long parseLongValue(String data) {
        data = stripInvalidCharacter(data);
        String[] split = data.split(",");
        long parsedLong = Long.parseLong(split[2]);
        if (parsedLong == MAX_LONG) {
            return -1;
        } else {
            return parsedLong;
        }
    }

    private static boolean parseInPitTiming(String data) {
        data = stripInvalidCharacter(data);
        String[] split = data.split(",");
        if (split.length != 4) {
            return false;
        }

        if (split[3].equals("-2")) {
            return true;
        } else {
            return false;
        }
    }

    private static LiveTimingState parseState(String data) {
        data = data.replace(")", "").replace("]", "").replace(";", "");
        String[] split = data.split(",");
        switch (split[2]) {
            case "SIn Pit":
                return LiveTimingState.PIT;
            case "SOutLap":
                return LiveTimingState.OUTLAP;
            case "S?":
                return LiveTimingState.UNKNOWN;
            case "SFinshd":
                return LiveTimingState.FINISHED;
            default:
                return LiveTimingState.RACING;
        }
    }

    private static String stripInvalidCharacter(String data) {
        return data.replace(")", "")
                .replace("]", "")
                .replace(";", "");
    }
}
