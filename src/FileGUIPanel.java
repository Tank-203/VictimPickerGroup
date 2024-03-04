import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FileGUIPanel extends JPanel {

    VictimPicker victimPicker = new VictimPicker();
    ArrayList<Victim> victims;
    ArrayList<Victim> twoVictims;

    public FileGUIPanel() {
        victimPicker.loadList();
        JButton button = new JButton("Pick Victim(s)");
        JPanel pickerPanel = new JPanel();
        JTextField victimField = new JTextField(20);
        JPanel victimPanel = new JPanel();
        victimField.setMaximumSize(victimField.getPreferredSize());

        pickerPanel.setLayout(new BoxLayout(pickerPanel, BoxLayout.Y_AXIS));
        pickerPanel.add(button);

        button.setBounds(40, 20, 10, 10);

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel victim = new JLabel();

        victimPanel.add(victimField);

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
        volunteerButton.addActionListener(e -> {
            System.out.println(victimField.getText());

            if (victimPicker.inList(victimField.getText())) {
                victimPicker.score(victimField.getText(), 5);
            }
        });

        this.add(pickerPanel, BorderLayout.WEST);
    }

}
