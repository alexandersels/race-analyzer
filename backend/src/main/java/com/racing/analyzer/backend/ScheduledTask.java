package com.racing.analyzer.backend;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.logic.LiveTimingParser;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private boolean enabled = false;

    @Autowired
    private LiveTimingRepository repository;

    @Scheduled(fixedRate = 5000)
    public void scrapWebsite() {
        if (enabled) {
            LOGGER.info("Fixed Rate Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
            Collection<LiveTiming> liveTimingCollection = LiveTimingParser.parseSite(
                    "https://livetiming.getraceresults.com/demo/zolder#screen-results");
            repository.saveAll(liveTimingCollection);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            LiveTimingParser.clearCache();
            scrapWebsite();
        }
    }
}
