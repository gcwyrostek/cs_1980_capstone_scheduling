package capstoneSchedulingApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class Query {

    //Overlapping Courses of same course_num
    public static void queryGeneric(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;
        for (int i = 1; i <= tableLength(databaseName, "classes"); i++) {
            String sql =  "SELECT *" 
                        + " FROM classes" 
                        + " WHERE id == " + i
                        + " AND type == 'LEC'";

            Course A = new Course();
            ArrayList<Course> B = new ArrayList<Course>();

            try (Connection dbConnection = DriverManager.getConnection(url);
                var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    A = new Course(rs);
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }

            //If initial query failed skip to next loop
            if (A.clas_num == -1) {
                continue;
            }

            sql =         "SELECT *" 
                        + " FROM classes"
                        + " WHERE id != " + i
                        //Checks that both instances are Lectures of the same Course Number
                        + " AND type == 'LEC'"
                        + " AND course_num == " + A.course_num
                        //Condition of the class times overlapping at all
                        + " AND (" + A.start_int  + " <= end_int"
                        + " AND start_int <= " + A.end_int + ")"
                        //Condtion to make sure class shares at least one day of the week
                        + " AND (day_mon AND " + A.day_mon
                        + " OR day_tues AND " + A.day_tues
                        + " OR day_wed AND " + A.day_wed
                        + " OR day_thurs AND " + A.day_thurs
                        + " OR day_fri AND "+ A.day_fri + ")";

            try (Connection dbConnection = DriverManager.getConnection(url);
            var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    B.add(new Course(rs));           
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }

            //The default constructor for Course sets clas_num to -1, so this means the query for A failed
            if (B.size() > 0)  {
                System.out.println("Collisions for " + A.toString());
                for (Course check : B) {
                    System.out.println(check.toString());
                }
            }
        }
    }

    //Overlapping Recitation of same course_num and asso_num
    public static void queryGenericRec(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;
        for (int i = 1; i <= tableLength(databaseName, "classes"); i++) {
            String sql =  "SELECT *" 
                        + " FROM classes" 
                        + " WHERE id == " + i
                        + " AND (type == 'REC'"
                        + " OR type == 'LAB')";

            Course A = new Course();
            ArrayList<Course> B = new ArrayList<Course>();

            try (Connection dbConnection = DriverManager.getConnection(url);
                var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    A = new Course(rs);
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }

            //If initial query failed skip to next loop
            if (A.clas_num == -1) {
                continue;
            }

            sql =         "SELECT *" 
                        + " FROM classes"
                        + " WHERE id != " + i
                        //Checks that both instances are Lectures of the same Course Number
                        + " AND (type == 'REC' OR type == 'LAB')"
                        + " AND course_num == " + A.course_num
                        + " AND asso_num == " + A.asso_num
                        //Condition of the class times overlapping at all
                        + " AND (" + A.start_int  + " <= end_int"
                        + " AND start_int <= " + A.end_int + ")"
                        //Condtion to make sure class shares at least one day of the week
                        + " AND (day_mon AND " + A.day_mon
                        + " OR day_tues AND " + A.day_tues
                        + " OR day_wed AND " + A.day_wed
                        + " OR day_thurs AND " + A.day_thurs
                        + " OR day_fri AND "+ A.day_fri + ")";

            try (Connection dbConnection = DriverManager.getConnection(url);
            var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    B.add(new Course(rs));           
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }

            if (B.size() > 0) {
                System.out.println("Collisions for " + A.toString());
                for (Course check : B) {
                    System.out.println(check.toString());
                }
            }
        }
    }

    //Instructor conseq courses
    public static void queryGenericInst(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;

        for (int h = 1; h <= tableLength(databaseName, "instructors"); h++) {
            String sql =  "SELECT *" 
                        + " FROM instructors" 
                        + " WHERE id == " + h
                        + " AND instructor != ''";

            String inst = "";

            try (Connection dbConnection = DriverManager.getConnection(url);
                var statement = dbConnection.prepareStatement(sql)) {

                var rs = statement.executeQuery();

                while (rs.next()) {
                    inst = rs.getString("instructor");
                }

            } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
            }

            //If initial query failed skip to next loop
            if (inst == "") {
                continue;
            }

            for (int i = 1; i <= tableLength(databaseName, "classes"); i++) {
                sql     =     "SELECT *" 
                            + " FROM classes" 
                            + " WHERE id == " + i
                            + " AND instructor == '" + inst +"'";

                Course A = new Course();
                ArrayList<Course> B = new ArrayList<Course>();

                try (Connection dbConnection = DriverManager.getConnection(url);
                    var statement = dbConnection.prepareStatement(sql)) {

                    var rs = statement.executeQuery();

                    while (rs.next()) {
                        A = new Course(rs);
                    }

                } catch (Exception e) {
                        System.out.println(e.toString());
                        return;
                }

                //If initial query failed skip to next loop
                if (A.clas_num == -1) {
                    continue;
                }

                sql =         "SELECT *" 
                            + " FROM classes"
                            + " WHERE id != " + i
                            //Checks that both instances are Lectures of the same Course Number
                            + " AND instructor == '" + A.instructor + "'"
                            //Condition of the class times overlapping at all
                            + " AND (" + A.start_int + " - end_int < 30"
                            + " AND " + A.start_int + " - end_int > 0)"
                            //Condtion to make sure class shares at least one day of the week
                            + " AND (day_mon AND " + A.day_mon
                            + " OR day_tues AND " + A.day_tues
                            + " OR day_wed AND " + A.day_wed
                            + " OR day_thurs AND " + A.day_thurs
                            + " OR day_fri AND "+ A.day_fri + ")";

                try (Connection dbConnection = DriverManager.getConnection(url);
                var statement = dbConnection.prepareStatement(sql)) {

                    var rs = statement.executeQuery();

                    while (rs.next()) {
                        B.add(new Course(rs));           
                    }

                } catch (Exception e) {
                        System.out.println(e.toString());
                        return;
                }

                if (B.size() > 0)  {
                    System.out.println("Within 30 mins " + A.toString());
                    for (Course check : B) {
                        System.out.println(check.toString());
                    }
                }
            }
        }
    }

    public static int tableLength(String databaseName, String table) {
        String url = "jdbc:sqlite:" + databaseName;
        String sql = "SELECT * FROM " + table + " ORDER BY ROWID DESC LIMIT 1";
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
