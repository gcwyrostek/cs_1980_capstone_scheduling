package capstoneSchedulingApp;


public class App {
    public static void main(String[] args) {
        Parser.parseFile("schedule.db","capstone-scheduling/src/main/java/capstoneSchedulingApp/Courses_with_Overlapping_Times.csv", ",");
        //System.out.print(sched.toString());
        Query.queryLectureOfSameCourseNumberOverlap("schedule.db");
    }
}
