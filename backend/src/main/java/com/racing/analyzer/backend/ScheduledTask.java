package com.racing.analyzer.backend;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.logic.LiveTimingParser;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    private Race race;
    private LiveTimingParser liveTimingParser;

    @Autowired
    private LiveTimingRepository repository;

    @Scheduled(fixedRate = 1000)
    public void scrapWebsite() {
        if (race != null && race.isRecording()) {
            LOGGER.info("Parsing site: {}", race.getUrl());
            parseSite(race.getUrl());
        }
    }

    public void record(Race race) {
        this.race = race;
        if (race.isRecording()) {
            LOGGER.info("Started recording !!");
            this.liveTimingParser = LiveTimingParser.forRace(race);
            scrapWebsite();
        } else {
            LOGGER.info("Stopped recording !!");
        }
    }

    private void parseSite(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7")
                    .get();
            Collection<LiveTiming> liveTimingCollection = liveTimingParser.parse(document);
            repository.saveAll(liveTimingCollection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
