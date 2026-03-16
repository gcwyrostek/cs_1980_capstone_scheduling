package capstoneSchedulingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class scheduleApp {
    public static void main(String [] args){
        SpringApplication.run(scheduleApp.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(){
        return args -> {
            String dbPath = System.getenv().getOrDefault("SQLITE_DB_PATH", "/app/data/schedule.db");
            String starterCsv = System.getenv().getOrDefault("STARTER_CSV_PATH", "/app/data/Mock_Schedule_Correct_Classrooms.csv");
            java.io.File csvFile = new java.io.File(starterCsv);
            if(csvFile.exists()){
                Parser.parseFile(dbPath, starterCsv, ",");
                System.out.println("Starter DB initialized from: " + starterCsv);
            }
            else
                System.out.println("No starter CSV found at: " + starterCsv);
        };
    }


}
