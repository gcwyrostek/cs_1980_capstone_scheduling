package capstoneSchedulingApp;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Schedule sched = Parser.parseFile("capstone-scheduling\\src\\main\\java\\capstoneSchedulingApp\\Mock_Schedule_Correct.csv", ",");
        System.out.print(sched.toString());
    }
}
