package com.racing.analyzer.backend.scheduled;

import com.racing.analyzer.backend.configuration.ParsingConfig;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.logic.LiveTimingParser;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

@Component
public class ScheduledTask {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) " +
            "Version/9.1.2 Safari/601.7.7";

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final HashMap<Long, LiveTimingParser> raceCache = new HashMap<>();
    private boolean isStartingUp = true;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private LiveTimingRepository liveTimingRepository;

    @Autowired
    private ParsingConfig parsingConfig;

    @Scheduled(fixedRate = 5000)
    public void scrapWebsite() {
        if (isStartingUp) {
            initialise();
            isStartingUp = false;
        }
        raceCache.values().forEach(parser -> {
            final Race race = parser.getRace();

            try {
                final Document document = Jsoup.connect(race.getUrl())
                                               .userAgent(USER_AGENT)
                                               .get();
                final Collection<LiveTiming> liveTimings = parser.parse(document);

                // Check if we should log the result.
                if (parsingConfig.isLoggingEnabled()) {
                    if (!liveTimings.isEmpty()) {
                        LOGGER.info("Updating Records for Race " + race.getId() + ": " + liveTimings.size() + " are added.");
                    }
                }

                // Check if snapshots of the site should be saved.
                if (parsingConfig.isCreateSnapshots()) {
                    final LocalTime time = LocalTime.now();
                    File temp = File.createTempFile("race_" + race.getId() + "_" + DATE_FORMAT.format(time),".txt");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                    bw.write(document.toString());
                    bw.close();
                }

                if (!liveTimings.isEmpty()) {
                    liveTimingRepository.saveAll(liveTimings);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void startRecording(Race race) {
        LOGGER.info("Added race: " + race.getId() + " to the recording cache.");
        if (!raceCache.containsKey(race.getId())) {
            raceCache.put(race.getId(), LiveTimingParser.of(race));
        }
    }

    public void stopRecording(Race race) {
        LOGGER.info("Removed race: " + race.getId() + " from the cache.");
        raceCache.remove(race.getId());
    }

    private void initialise() {
        LOGGER.error("Initialised the Scheduled Task Cache.");
        raceRepository.findAllRecordingRaces().forEach(race -> raceCache.put(race.getId(), LiveTimingParser.of(race)));
    }

}
