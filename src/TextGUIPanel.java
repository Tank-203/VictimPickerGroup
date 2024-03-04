import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TextGUIPanel extends JPanel {

    VictimPicker victimPicker = new VictimPicker();
    ArrayList<Victim> victims;
    ArrayList<Victim> twoVictims;

    public TextGUIPanel() {
        victimPicker.initializeVictim();
        JButton button = new JButton("Pick Victim(s)");
        JPanel pickerPanel = new JPanel();
        JTextField victimField = new JTextField(20);
        JPanel victimPanel = new JPanel();
        JButton victimDoneButton = new JButton("Submit Name");
        victimPanel.setLayout(new BoxLayout(victimPanel, BoxLayout.Y_AXIS));

        pickerPanel.setLayout(new BoxLayout(pickerPanel, BoxLayout.Y_AXIS));
        pickerPanel.add(button);

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
        JButton absentButton = new JButton("Mark Absent");
        pickerPanel.add(absentButton);
        this.add(pickerPanel,BorderLayout.CENTER);
        absentButton.addActionListener(e->{
            victimPicker.markAbsent(twoVictims.getFirst());
        });

        JButton addpointButton = new JButton("Add Point");
        pickerPanel.add(addpointButton);
        addpointButton.addActionListener(e->{
            victimPicker.score(twoVictims.getFirst().getName(), 1);
        });

        JButton subpointButton = new JButton("Subtract Point");
        pickerPanel.add(subpointButton);
        subpointButton.addActionListener(e->{
            victimPicker.score(twoVictims.getFirst().getName(), -1);
        });

        JButton volunteerButton = new JButton("Volunteered");
        pickerPanel.add(volunteerButton);
        this.add(pickerPanel, BorderLayout.CENTER);
        volunteerButton.addActionListener(e -> {
            if (victimPicker.inList(victimField.getText())) {
                victimPicker.score(victimField.getText(), 5);
            }
        });
    }
}
