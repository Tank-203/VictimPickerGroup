import javax.swing.*;

public class Main {
    public static VictimPicker victimPicker = new VictimPicker();
    public static void main(String[] args) {
        //VictimPicker GUI
        SwingUtilities.invokeLater(() -> new VictimPickerPanel(victimPicker).setVisible(true));
    }
}