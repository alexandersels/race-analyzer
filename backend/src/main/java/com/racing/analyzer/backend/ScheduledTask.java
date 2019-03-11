package com.racing.analyzer.backend;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
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
import java.util.Collection;

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    private static final boolean DOWNLOAD = true;
    private static final ScheduledTaskAction ACTION = ScheduledTaskAction.PARSE_URL;
    private static final String URL = "https://livetiming.getraceresults.com/demo/zolder#screen-results";

    private boolean enabled = false;
    private int i = 0;
    private int maxNr = 50;
    private LiveTimingParser parser;

    @Autowired
    private LiveTimingRepository repository;

    @Scheduled(fixedRate = 5000)
    public void scrapWebsite() throws IOException {
        if (enabled) {
            switch (ACTION) {
                case PARSE_URL:
                    LOGGER.info("Parsing site: {}", URL);
                    parseSite(URL);
                    break;
                case PARSE_DOCUMENT:
                    LOGGER.info("Reading document");
                    parseDocument();
                    break;
            }

            if (DOWNLOAD) {
                LOGGER.info("Downloading");
                downloadDocument(URL);
            }
        }
    }

    public void recordingFor(boolean enabled, Race race) {
        this.enabled = enabled;
        if (enabled) {
            this.parser = LiveTimingParser.forRace(race);
            try {
                scrapWebsite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            LOGGER.info("Stopped recording !!");
        }
    }

    private void parseSite(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7")
                    .get();
            Collection<LiveTiming> liveTimingCollection = parser.parse(document);
            //repository.saveAll(liveTimingCollection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadDocument(String url) throws IOException {
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
                Collection<LiveTiming> parsedTimings = parser.parse(document);
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
