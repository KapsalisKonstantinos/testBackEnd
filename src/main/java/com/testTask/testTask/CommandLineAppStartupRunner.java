package com.testTask.testTask;

import com.testTask.testTask.services.CompanyService;
import com.testTask.testTask.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Override
    public void run(String...args) throws Exception {
        if(args.length > 0){
            String[] parameter = args[0].split("=");
            if(!parameter[1].isEmpty() && parameter[0].equalsIgnoreCase("--company.name")) {
                Utils.setCompanyName(args[0].split("=")[1]);
            }
        }

    }
}
