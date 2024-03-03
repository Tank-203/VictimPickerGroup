import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class VictimPickerPanel extends JPanel {

    //VictimPicker needed variables
    VictimPicker victimPicker = new VictimPicker();
    ArrayList<Victim> victims;
    ArrayList<Victim> twoVictims;

    private JFrame frame;
    private JLabel timeLabel;
    private Timer timer;
    private int timeLeftInSeconds = 1 * 60;

    private boolean isFile = false;

    public VictimPickerPanel(VictimPicker victimPicker) {
        frame = new JFrame("Victim Picker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300)); // Set the preferred size of the frame
        frame.setLocationRelativeTo(null); // Center the window

        // Show the initial choice dialog
        showChoiceDialog();
    }

    private void showChoiceDialog() {
        // Custom panel for the dialog
        JPanel dialogPanel = new JPanel();
        dialogPanel.add(new JLabel("Choose how to input names:"));

        // Options for the user
        String[] options = {"From File", "From Text Field"};

        // It's better to use frame here or null if the frame isn't available yet
        int choice = JOptionPane.showOptionDialog(frame, dialogPanel, "Input Method",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        // Process the user's choice
        if (choice == JOptionPane.YES_OPTION) {
            isFile = true;
            victimPicker.loadList();
        } else if (choice == JOptionPane.NO_OPTION) {
            isFile = false;
        }

        // Display the main JPanel after configuring based on user choice
        displayMainPanel();
    }

    private void displayMainPanel() {
        timeLabel = new JLabel();
        updateTimeLabel();
        // Create and configure the main panel
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Victim: ", SwingConstants.LEFT), BorderLayout.NORTH);

        // Add this panel to the JFrame
        frame.setContentPane(this);

        if (isFile)
            setUpFileGUI();
        else
            setUpTextGUI();

        timer = new Timer(1000, e -> {
            if (timeLeftInSeconds > 0) {
                timeLeftInSeconds--;
                updateTimeLabel();
            } else {
                timer.stop();
                timeLabel.setText("Time's Up!");
            }
        });

        this.add(timeLabel, BorderLayout.SOUTH);

        // Prepare and show the frame
        frame.pack(); // Adjusts frame size to fit its contents
        frame.setVisible(true);
        timer.start();
    }

    private void setUpFileGUI() {
        JButton button = new JButton("Pick Victim(s)");
        button.setBounds(5, 5, 15, 10);
        this.add(button, BorderLayout.LINE_START);
        button.addActionListener(e -> {
            twoVictims = victimPicker.chooseTwo();
            System.out.println(twoVictims.getFirst().getName() + " " + twoVictims.getLast().getName());
        });

    }

    private void setUpTextGUI() {
        JButton button = new JButton("Text Button was here");
        this.add(button, BorderLayout.NORTH);
    }

    public void loadListFromFile() {
        victims = new ArrayList<>();
        File inFile = new File("newstudentslist" + ".txt");
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

    private void updateTimeLabel() {
        int minutes = timeLeftInSeconds / 60;
        int seconds = timeLeftInSeconds % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
