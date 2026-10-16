import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class Feedrate {
    private static boolean isMetric = false; // Default to Imperial
    private static Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static boolean darkMode = prefs.getBoolean("darkMode", false); // Load dark mode setting

    public static void open(Point location) {
        JFrame frame = new JFrame("Feed Rate Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame);

        if (darkMode) {
            applyDarkMode(panel, darkMode);
        

        frame.setVisible(true);}
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Add a "Back to Mill Calc Screen" button
        JButton backButton = new JButton("Back to Mill Calc Screen");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                MillCalc.open(frame.getLocation()); // Open the Mill Calc screen
            }
        });

        // Add unit selection radio buttons
        JLabel unitLabel = new JLabel("Select Unit:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(unitLabel, gbc);

        JRadioButton imperialButton = new JRadioButton("Imperial");
        JRadioButton metricButton = new JRadioButton("Metric");
        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(imperialButton);
        unitGroup.add(metricButton);
        imperialButton.setSelected(true);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(imperialButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(metricButton, gbc);

        // Add components for feed rate calculation
        JLabel rpmLabel = new JLabel("RPM:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(rpmLabel, gbc);

        JTextField rpmField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(rpmField, gbc);

        JLabel chipLoadLabel = new JLabel("Chip Load (in):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(chipLoadLabel, gbc);

        JTextField chipLoadField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(chipLoadField, gbc);

        JLabel numTeethLabel = new JLabel("Number of Teeth:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(numTeethLabel, gbc);

        JTextField numTeethField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(numTeethField, gbc);

        JButton calculateButton = new JButton("Calculate Feed Rate");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        JLabel resultLabel = new JLabel("Feed Rate: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double rpm = Double.parseDouble(rpmField.getText());
                    double chipLoad = Double.parseDouble(chipLoadField.getText());
                    int numTeeth = Integer.parseInt(numTeethField.getText());
                    double feedRate = feedRate(rpm, chipLoad, numTeeth, isMetric);
                    resultLabel.setText("Feed Rate: " + feedRate + (isMetric ? " mm/min" : " in/min"));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Please enter valid numbers.");
                }
            }
        });

        // Add action listeners to unit selection buttons
        imperialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMetric = false;
                chipLoadLabel.setText("Chip Load (in):");
                convertInputs(chipLoadField, false);
            }
        });

        metricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMetric = true;
                chipLoadLabel.setText("Chip Load (mm):");
                convertInputs(chipLoadField, true);
            }
        });
    }

    private static void convertInputs(JTextField chipLoadField, boolean toMetric) {
        try {
            double chipLoad = Double.parseDouble(chipLoadField.getText());
            if (toMetric) {
                chipLoad *= 25.4; // Convert inches to mm
            } else {
                chipLoad /= 25.4; // Convert mm to inches
            }
            chipLoadField.setText(String.valueOf(chipLoad));
        } catch (NumberFormatException ex) {
            // Ignore conversion if input is invalid
        }
    }

    public static double feedRate(double rpm, double chipLoad, int numTeeth, boolean isMetric) {
        double feedRate = rpm * chipLoad * numTeeth;
        return isMetric ? feedRate * 25.4 : feedRate; // Convert to mm/min if metric
    }
    private static void applyDarkMode(Component component, boolean darkMode) {
        if (darkMode) {
            component.setBackground(Color.DARK_GRAY);
            component.setForeground(Color.WHITE);
        } else {
            component.setBackground(Color.LIGHT_GRAY);
            component.setForeground(Color.BLACK);
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyDarkMode(child, darkMode);
            }
        }
    }
}