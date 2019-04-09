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
import java.util.List;

@Service
public class PrintService {
    private static final Logger log = LoggerFactory.getLogger(PrintService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final LogRepository logRepository;
    private final TaskScheduler taskScheduler;

    @Autowired
    public PrintService(LogRepository logRepository, TaskScheduler taskScheduler) {
        this.logRepository = logRepository;
        this.taskScheduler = taskScheduler;
    }

    public void run() {
        taskScheduler.schedule(this::printLogs, new Date(1000L));
    }

    private void printLogs() {
        List<LogModel> logModels = logRepository.findAll();
        for (LogModel logModel : logModels) {
            log.info("logged time is {}", dateFormat.format(logModel.getDate()));
        }
        System.exit(0);
    }
}
