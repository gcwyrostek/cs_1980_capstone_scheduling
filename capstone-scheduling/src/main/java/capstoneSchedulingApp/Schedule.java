package capstoneSchedulingApp;

import java.util.ArrayList;

public class Schedule {

    private ArrayList<Course>[][] sched;
    private boolean[][] instFlag;
    private int startTime;
    private int endTime;
    private int sectionSize;
    private int days;
    private int arraySize;

    public Schedule(int days, int startTime, int endTime, int sectionSize) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.sectionSize = sectionSize;
        this.days = days;
        this.arraySize = (endTime - startTime) / sectionSize;
        sched = new ArrayList[days][arraySize];
        instFlag = new boolean[days][arraySize];
    }

    public void add(Course toAdd){
        int timeSlotStart = (toAdd.getStartToInt() - startTime) / sectionSize;
        ArrayList<Integer> eachDay = new ArrayList<Integer>();
        switch(toAdd.getDays()) {
            case MTuWThF:
                eachDay.add(0);
                eachDay.add(1);
                eachDay.add(2);
                eachDay.add(3);
                eachDay.add(4);
                break;
            case MWF:
                eachDay.add(0);
                eachDay.add(2);
                eachDay.add(4);
                break;
            case MW:
                eachDay.add(0);
                eachDay.add(2);
                break;
            case TuTh:
                eachDay.add(1);
                eachDay.add(3);                
                break;
            case MF:
                eachDay.add(0);
                eachDay.add(4);
                break;
            case WF:
                eachDay.add(2);
                eachDay.add(4);
                break;
            case M:
                eachDay.add(0);
                break;
            case Tu:
                eachDay.add(1);
                break;
            case W:
                eachDay.add(2);
                break;
            case Th:
                eachDay.add(3);
                break;
            case F:
                eachDay.add(4);
                break;
        }

        for (int day : eachDay) {
            if (!instFlag[day][timeSlotStart]) {
                instFlag[day][timeSlotStart] = true;
                sched[day][timeSlotStart] = new ArrayList<Course>();
            }
            sched[day][timeSlotStart].add(toAdd);
        }
    }

    public String toString() {
        String out = "";
        for (int d = 0; d < days; d++) {
            for (int slots = 0; slots < arraySize; slots++){
                if (sched[d][slots] != null)
                {
                    out += "Slot [" + d + "][" + slots + "]" + System.lineSeparator();
                    for (Course c : sched[d][slots]){
                        out += c.toString() + System.lineSeparator();
                    }
                }
            }
        }
        return out;
    }
    
}
