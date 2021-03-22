package com.testTask.testTask;

import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private CompanyService service;

    @Override
    public void run(String...args) throws Exception {
        if(!args[0].split("=")[1].isEmpty()) {
            Utils.setCompanyName(args[0].split("=")[1]);
        }

    }
}
