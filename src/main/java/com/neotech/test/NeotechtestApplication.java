package com.neotech.test;

import com.neotech.test.service.LogService;
import com.neotech.test.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class NeotechtestApplication implements CommandLineRunner {

    private static final String PRINT_PARAM = "-p";

    private final PrintService printService;
    private final LogService logService;

    @Autowired
    public NeotechtestApplication(PrintService printService, LogService logService) {
        this.printService = printService;
        this.logService = logService;
    }

    public static void main(String[] args) {
        SpringApplication.run(NeotechtestApplication.class, args);
    }

    private static boolean checkPrint(String[] args) {
        return args.length > 0 && args[0].equals(PRINT_PARAM);
    }

    @Override
    public void run(String... args) throws Exception {
        if (checkPrint(args)) {
            printService.run();

        } else {
            logService.run();
        }
    }
}