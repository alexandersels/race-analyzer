package com.racing.analyzer.backend;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.ScheduledTaskAction;
import com.racing.analyzer.backend.logic.LiveTimingParser;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final boolean DOWNLOAD = true;
    private static final ScheduledTaskAction ACTION = ScheduledTaskAction.PARSE_URL;
    private static final String URL = "https://livetiming.getraceresults.com/demo/zolder#screen-results";

    private boolean enabled = false;
    private int i = 0;
    private int maxNr = 6;



    @Autowired
    private LiveTimingRepository repository;

    @Scheduled(fixedRate = 10000)
    public void scrapWebsite() throws IOException {
        if (enabled) {
            switch (ACTION) {
                case PARSE_URL:
                    LOGGER.info("Fixed Rate Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
                    parseSite(URL);
                    break;
                case PARSE_DOCUMENT:
                    parseDocument();
                    break;
            }

            if (DOWNLOAD) {
                LOGGER.info("Downloading");
                parseFile(URL);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            LiveTimingParser.clearCache();
            try {
                scrapWebsite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseSite(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7")
                    .get();
            Collection<LiveTiming> liveTimingCollection = LiveTimingParser.parse(document);
            repository.saveAll(liveTimingCollection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseFile(String url) throws IOException {
        PrintWriter file = new PrintWriter("C:\\Users\\AlexanderSE\\Desktop\\Exports\\" + i + ".txt", "UTF-8");
        file.println(Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7")
                .get()
                .getElementsByTag("script"));
        i++;
    }

    private void parseDocument() {
        if (i <= maxNr) {
            try {
                File file = new File("C:\\Users\\AlexanderSE\\Desktop\\Exports\\" + i + ".txt");
                Document document = Jsoup.parse(file, "UTF-8", "");
                Collection<LiveTiming> parsedTimings = LiveTimingParser.parse(document);
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
