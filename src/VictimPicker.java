import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class VictimPicker {
    private ArrayList<Victim> victims;
    private ArrayList<Victim> pickedToday;
    private ArrayList<Victim> absentToday;

    public ArrayList<Victim> chooseTwo() {
        ArrayList<Victim> chosenTwo = new ArrayList<>();
        Random rand = new Random();
        if (victims.size() < 2) {
            throw new IllegalStateException("Not enough victims to choose from.");
        }
        int randomIndex = rand.nextInt(victims.size());
        int firstRand = 0;
        int secondRand = 0;
        do {
            chosenTwo.clear();
            firstRand = randomIndex;
            chosenTwo.add(victims.get(randomIndex));

            randomIndex = rand.nextInt(victims.size());
            secondRand = randomIndex;
            chosenTwo.add(victims.get(randomIndex));

        } while (chosenTwo.getFirst() == chosenTwo.getLast());

        pickedToday.add(victims.get(firstRand));
        pickedToday.add(victims.get(secondRand));

        return chosenTwo;
    }

    public void score(int points) {
        Date todaysDate = new Date();
        //First Student
        int firstScore = pickedToday.getFirst().getScore() + points;
        pickedToday.getFirst().setlastPicked(todaysDate);
        pickedToday.getFirst().setScore(firstScore);
        pickedToday.getFirst().addNumberOfPicks(1);

        //Second Student
        int secondScore = pickedToday.getLast().getScore() + points;
        pickedToday.getLast().setlastPicked(todaysDate);
        pickedToday.getLast().setScore(secondScore);
        pickedToday.getLast().addNumberOfPicks(1);
    }

    public void markAbsent(Victim absentVictim) {
        Date todaysDate = new Date();
        absentToday.add(absentVictim);
        absentVictim.getAbsences().add(todaysDate);
    }

    public void loadList() {
        absentToday = new ArrayList<>();
        victims = new ArrayList<>();
        pickedToday = new ArrayList<>();
        File inFile = new File("studentslist" + ".txt");
        try {
            Scanner file = new Scanner(inFile);
            while (file.hasNextLine()) {
                String line = file.nextLine();
                Victim currentVictim = new Victim(line, 22199);
                victims.add(currentVictim);
            }
        } catch (Exception e) {
            throw new IllegalAccessError("File not found: " + e);
        }
    }

}
