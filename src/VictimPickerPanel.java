import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class VictimPickerPanel extends JPanel {

    //VictimPicker needed variables
    VictimPicker victimPicker = new VictimPicker();
    ArrayList<Victim> victims;
    ArrayList<Victim> twoVictims;

    private JFrame frame;
    private JLabel timeLabel;
    private Timer timer;
    private int timeLeftInSeconds;

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
        JPanel timerPanel = new JPanel();
        // Create and configure the main panel
        this.setLayout(new BorderLayout());
//        this.add(new JLabel("Victim: ", SwingConstants.LEFT), BorderLayout.NORTH);

        // Add this panel to the JFrame
        frame.setContentPane(this);

        if (isFile)
            setUpFileGUI();
        else {
            setUpTextGUI();
            victimPicker.initializeVictim();
        }

        timer = new Timer(1000, e -> {
            if (timeLeftInSeconds > 0) {
                timeLeftInSeconds--;
                updateTimeLabel(timeLeftInSeconds);
            } else {
                timer.stop();
                timeLabel.setText("Time's Up!");
            }
        });

        timerPanel.add(timeLabel);
        JButton timeButton = new JButton("Edit Timer");
        timerPanel.add(timeButton);
        timeButton.addActionListener(e -> {
            JFrame timeFrame = new JFrame();
            JPanel timePanel = new JPanel();
            JPanel textButtonPanel = new JPanel();
            textButtonPanel.setLayout(new BoxLayout(textButtonPanel, BoxLayout.Y_AXIS));
            JButton doneButton = new JButton("Done");
            timePanel.add(new JLabel("Edit Timer in Minutes"));
            timePanel.setPreferredSize(new Dimension(200, 150));

            JTextField textField = new JTextField(20);
            textButtonPanel.add(textField);
            textButtonPanel.add(doneButton, BorderLayout.CENTER);
            timePanel.add(textButtonPanel);

            doneButton.addActionListener(e2 -> {
                if (textField.getText() == null) {
                    throw new IllegalStateException("Not enough victims to choose from.");
                }
                else {
                    updateTimeLabel(parseInt(textField.getText()) * 60);
                    timer.start();
                }
            });

            timeFrame.getContentPane().add(timePanel);
            timeFrame.pack();
            timeFrame.setVisible(true);
        });

        // Prepare and show the frame
        frame.add(timerPanel, BorderLayout.SOUTH);

        frame.pack(); // Adjusts frame size to fit its contents
        frame.setVisible(true);
        timer.start();
    }

    private void setUpFileGUI() {
        JButton button = new JButton("Pick Victim(s)");
        JPanel pickerPanel = new JPanel();

        pickerPanel.setLayout(new BoxLayout(pickerPanel, BoxLayout.Y_AXIS));
        pickerPanel.add(button);
        this.add(pickerPanel, BorderLayout.CENTER);

        button.setBounds(40, 20, 10, 10);

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel victim = new JLabel();

        pickerPanel.add(label);
        pickerPanel.add(victim);

        button.addActionListener(e -> {
            twoVictims = victimPicker.chooseTwo();
            label.setText("The victim is: ");
            victim.setText(twoVictims.getFirst().getName());
        });

    }

    private void setUpTextGUI() {
        JButton button = new JButton("Pick Victim(s)");
        JPanel pickerPanel = new JPanel();
        JTextField victimField = new JTextField(20);
        JPanel victimPanel = new JPanel();
        JButton victimDoneButton = new JButton("Submit Name");
        victimPanel.setLayout(new BoxLayout(victimPanel, BoxLayout.Y_AXIS));

        pickerPanel.setLayout(new BoxLayout(pickerPanel, BoxLayout.Y_AXIS));
        pickerPanel.add(button);
        this.add(pickerPanel, BorderLayout.CENTER);

        button.setBounds(40, 20, 10, 10);
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel victim = new JLabel();

        victimField.setMaximumSize(victimField.getPreferredSize());
        victimDoneButton.addActionListener(e -> {
            if (victimField.getText() == null) {
                throw new IllegalStateException("Not enough victims to choose from.");
            }
            else {
                victimPicker.addVictim(victimField.getText());
            }
        });

        victimPanel.add(victimField);
        victimPanel.add(victimDoneButton, BorderLayout.CENTER);

        pickerPanel.add(label);
        pickerPanel.add(victim);
        pickerPanel.add(victimPanel);

        button.addActionListener(e -> {
            twoVictims = victimPicker.chooseTwo();
            label.setText("The victim is: ");
            victim.setText(twoVictims.getFirst().getName());
        });
    }

    private void updateTimeLabel(int timeLeftInSeconds) {
        this.timeLeftInSeconds = timeLeftInSeconds;
        int minutes = timeLeftInSeconds / 60;
        int seconds = timeLeftInSeconds % 60;
        timeLabel.setText(String.format(" %02d:%02d", minutes, seconds));
    }
}
