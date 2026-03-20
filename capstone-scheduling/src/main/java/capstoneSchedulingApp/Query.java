package capstoneSchedulingApp;

import java.sql.Connection;
import java.sql.DriverManager;

public class Query {

    public static void queryLectureOfSameCourseNumberOverlap(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;
        String current = "";
        for (int i = 1; i < tableLength(databaseName); i++) {
            String sql =  "SELECT *" 
                        + " FROM classes t1" 
                        + " WHERE id == " + i
                        + " AND type == 'LEC'";

            int clas_num = 0;
            int course_num = 0;
            String instructor = "";
            String days = "";
            String startTime = "";
            String endTime = "";
            int start = 0;
            int end = 0;
            boolean mon = false;
            boolean tues = false;
            boolean wed = false;
            boolean thurs = false;
            boolean fri = false;

            try (Connection dbConnection = DriverManager.getConnection(url);
                var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    clas_num = rs.getInt("clas_num");
                    course_num = rs.getInt("course_num");
                    instructor = rs.getString("instructor");
                    days = rs.getString("days");
                    startTime = rs.getString("start");
                    endTime = rs.getString("end");
                    start = rs.getInt("start_int");
                    end = rs.getInt("end_int");
                    mon = rs.getBoolean("day_mon");                    
                    tues = rs.getBoolean("day_tues");                    
                    wed = rs.getBoolean("day_wed");                    
                    thurs = rs.getBoolean("day_thurs");
                    fri = rs.getBoolean("day_fri");

                    current = clas_num + " - " + course_num + " with " + instructor + " on " + days + " from: " + startTime + "-" + endTime;
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }
            sql =         "SELECT *" 
                        + " FROM classes"
                        + " WHERE id != " + i
                        //Checks that both instances are Lectures of the same Course Number
                        + " AND type == 'LEC'"
                        + " AND course_num == " + course_num
                        //Condition of the class times overlapping at all
                        + " AND (" + start  + " <= end_int"
                        + " AND start_int <= " + end + ")"
                        //Condtion to make sure class shares at least one day of the week
                        + " AND (day_mon AND " + mon
                        + " OR day_tues AND " + tues
                        + " OR day_wed AND " + wed
                        + " OR day_thurs AND " + thurs
                        + " OR day_fri AND "+ fri + ")";
            try (Connection dbConnection = DriverManager.getConnection(url);
            var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    //System.out.println("ID: " + i + " collides with " + rs.getString("id"));
                    int clas_num2 = rs.getInt("clas_num");
                    int course_num2 = rs.getInt("course_num");
                    String instructor2 = rs.getString("instructor");
                    String days2 = rs.getString("days");
                    String startTime2 = rs.getString("start");
                    String endTime2 = rs.getString("end");
                    System.out.println(current);
                    System.out.println(clas_num2 + " - " + course_num2 + " with " + instructor2 + " on " + days2 + " from: " + startTime2 + "-" + endTime2);
                
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }
        }
    }

    public static int tableLength(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;
        String sql = "SELECT * FROM classes ORDER BY ROWID DESC LIMIT 1";
        try (Connection dbConnection = DriverManager.getConnection(url);
            var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();
                while (rs.next()) {
                    return rs.getInt("id");
                }
                return 0;
                

        } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
        }
    }
}
