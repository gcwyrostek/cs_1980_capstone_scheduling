package capstoneSchedulingApp;


public class App {
    public static void main(String[] args) {
        Parser.parseFile("schedule.db","src/main/java/capstoneSchedulingApp/Instructor_Conseq_Classes.csv", ",");
        //Query.queryGenericInst("schedule.db");
        Query.queryTeacherProximity("schedule.db", 30);
    }
}
