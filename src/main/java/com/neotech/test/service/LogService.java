package com.neotech.test.service;

import com.neotech.test.db.LogModel;
import com.neotech.test.db.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LogService {
    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final ConcurrentLinkedQueue<Date> queue = new ConcurrentLinkedQueue<>();

    private final LogRepository logRepository;
    private final TaskScheduler taskScheduler;

    @Autowired
    public LogService(LogRepository logRepository,
                      TaskScheduler taskScheduler) {
        this.logRepository = logRepository;
        this.taskScheduler = taskScheduler;
    }

    public void run() {
        logRepository.deleteAll();

        taskScheduler.scheduleAtFixedRate(this::addDate, 1000L);
        taskScheduler.scheduleWithFixedDelay(this::logDate, 3000L);
    }

    private void addDate() {
        Date currentDate = new Date();
        log.info("current time is {}", dateFormat.format(currentDate));
        queue.add(currentDate);
    }

    private void logDate() {
        if (queue.size() <= 0) {
            return;
        }

        try {
            while (queue.size() > 0) {
                Date date = queue.peek();
                if (date != null) {
                    logRepository.save(new LogModel(date));
                    log.info("save date {}", date);
                }
                queue.poll();
            }

        } catch (Exception e) {
            log.error("error saving {}", e.getMessage());
        }
    }
}
