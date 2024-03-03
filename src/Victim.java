import java.util.ArrayList;
import java.util.Date;

public class Victim {
    private String name;
    private final int section;
    private int score;
    private Date lastPicked;
    private ArrayList<Date> absences;
    private int numberOfPicks;

    public Victim(String name, int section) {
        this.name = name;
        this.section = section;
        score = 0;
        lastPicked = null;
        absences = new ArrayList<Date>();
        numberOfPicks = 0;
    }

    public void addNumberOfPicks(int num) {
        numberOfPicks += num;
    }

    //SETTERS
    public void setScore(int score) {
        this.score = score;
    }

    //Not really needed
    public void setName(String name) {
        this.name = name;
    }

    public void setlastPicked(Date date) {
        lastPicked = date;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getSection() {
        return section;
    }

    public int getScore() {
        return score;
    }

    public Date getLastPicked() {
        return lastPicked;
    }

    public ArrayList<Date> getAbsences() {
        return absences;
    }

    public int getNumberOfPicks() {
        return numberOfPicks;
    }
}