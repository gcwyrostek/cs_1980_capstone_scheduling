package capstoneSchedulingApp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.nio.file.*;
import java.io.File;
import org.apache.commons.io.FileUtils;

@SpringBootApplication
public class scheduleApp extends Application{

    private ConfigurableApplicationContext context;
    private String dbPath = System.getenv().getOrDefault("SQLITE_DB_PATH", "tmpData/");

    public static void main(String [] args){
        launch(args);
    }

    @Override
    public void init(){
        context = new SpringApplicationBuilder(scheduleApp.class).run();
        try {
            Files.createDirectory(Paths.get("tmpData/"));
        } catch (Exception e) {
            System.out.println("Data Directory Already Exists");
        }
    }

    @Override
    public void start(Stage stage){
        WebView webView = new WebView();
        webView.getEngine().load("http://127.0.0.1:8080/");
        Scene scene = new Scene(webView, 1200, 800);
        stage.setTitle("Schedule Validation Tool");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        if(context!=null)
            context.close();

        try {
            FileUtils.deleteDirectory(new File("tmpData/"));
        } catch (Exception e) {
            System.out.println("Error cleaning data directory");
        }

        
    }

    


}
