package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.ParseType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveTimingParser {

    private static final Pattern PATTERN = Pattern.compile("raceResultsViewModel\\.r_c\\(.*\\);");

    public static Collection<LiveTiming> parseSite(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7")
                    .get();
            String data = getDataAsString(document);
            return parse(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private static String getDataAsString(Document document) {
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

    private static Collection<LiveTiming> parse(String data) {
        if (data != null) {
            data = data.replace("raceResultsViewModel.r_c", "")
                    .replace("([", "")
                    .replace("], true", "")
                    .replace("\"", "");

            String[] splits = data.split("],\\[");
            List<LiveTiming> liveTimings = new ArrayList<>();
            for (int i = 0; i < splits.length; i += 17) {

                LiveTiming liveTiming = LiveTiming.getBuilder()
                        .withPosition(parseIntegerValue(splits[i + ParseType.POSITION.getIndex()]))
                        .withNumber(parseIntegerValue(splits[i + ParseType.NUMBER.getIndex()]))
                        .withName(parseStringValue(splits[i + ParseType.NAME.getIndex()]))
                        .withCls(parseStringValue(splits[i + ParseType.CLS.getIndex()]))
                        .withLastLapTime(parseLastLapTime(splits[i + ParseType.LAST.getIndex()]))
                        .withBestLapTime(parseBestLapTime(splits[i + ParseType.BEST.getIndex()]))
                        .withNationality(parseStringValue(splits[i + ParseType.NAT.getIndex()]))
                        .withCar(parseStringValue(splits[i + ParseType.CAR.getIndex()]))
                        .build();

                liveTimings.add(liveTiming);
            }

            liveTimings.stream().forEach(x -> System.out.println(x));
            return liveTimings;
        } else {
            return Collections.emptyList();
        }
    }

    private static int parseIntegerValue(String data) {
        try {
            String[] split = data.split(",");
            if (split.length != 3) {
                return -1;
            }
            return Integer.parseInt(split[2]);
        } catch (Exception e) {
            return -1;
        }
    }

    private static String parseStringValue(String data) {
        String[] split = data.split(",");
        if (split.length != 3) {
            return "";
        }
        return split[2];
    }

    private static int parseLastLapTime(String data) {
        return parseLapTime(4, data);
    }

    private static int parseBestLapTime(String data) {
        return parseLapTime(3, data);
    }

    private static int parseLapTime(int expectedSize, String data) {
        try {
            String[] split = data.split(",");
            if (split.length != expectedSize) {
                return -1;
            }
            return Integer.parseInt(split[2]);
        } catch (Exception e) {
            return -1;
        }

    }
}
