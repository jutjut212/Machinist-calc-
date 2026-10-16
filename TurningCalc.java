import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.*;

public class TurningCalc {
    private static boolean isMetric = false; // Default to Imperial
    private static final Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static final boolean darkMode = prefs.getBoolean("darkMode", false); // Load dark mode setting
    private static JButton iprToIpmButton;
    private static JButton ipmToIprButton;
    private static JLabel diameterLabel;
    private static JLabel feedRateLabel;
    private static JLabel resultLabel;
    private static JLabel sfmToRpmHeader;
    private static JLabel iprToIpmHeader;
    private static JLabel ipmToIprHeader;
    private static JLabel diameterConversionLabel;
    private static JLabel sfmToRpmLabel;



    public static void open(Point location) {
        JFrame frame = new JFrame("Turning Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 800); // Increased size to accommodate new section
        frame.setLocation(location);
        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);
        placeComponents(panel, frame);
        if (darkMode) {
            applyDarkMode(panel, darkMode);
        }
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Add a "Back to Main Screen" button
        JButton backButton = new JButton("Back to Main Screen");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);
        backButton.addActionListener((ActionEvent e) -> {
            frame.dispose(); // Close the current frame
            CalculatorMainScreen.open(frame.getLocation()); // Open the Main screen
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

        // Add components for turning calculations
        JLabel turningCalcHeader = new JLabel("Turning Calculations");
        turningCalcHeader.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(turningCalcHeader, gbc);

        diameterLabel = new JLabel("Diameter (in):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(diameterLabel, gbc);
        JTextField diameterField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(diameterField, gbc);

        JLabel rpmLabel = new JLabel("RPM:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(rpmLabel, gbc);
        JTextField rpmField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(rpmField, gbc);

        feedRateLabel = new JLabel("Feed Rate (in/rev):");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(feedRateLabel, gbc);
        JTextField feedRateField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(feedRateField, gbc);

        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);
        resultLabel = new JLabel("Result: ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);
        calculateButton.addActionListener((ActionEvent e) -> {
            try {
                double diameter = Double.parseDouble(diameterField.getText());
                double rpm = Double.parseDouble(rpmField.getText());
                double feedRate = Double.parseDouble(feedRateField.getText());
                double cuttingSpeed = calculateCuttingSpeed(diameter, rpm, isMetric);
                double cutTime = calculateCutTime(diameter, feedRate, rpm, isMetric);
                String format = "%.2f";
                resultLabel.setText(String.format(
                        "<html>Cutting Speed: " + format + " %s<br><br>Cut Time: " + format + " min</html>",
                        cuttingSpeed, isMetric ? "m/min" : "in/min", cutTime
                ));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Please enter valid numbers.");
            }
        });

        // Add action listeners to unit selection buttons
        imperialButton.addActionListener((ActionEvent e) -> {
            if (isMetric) {
                isMetric = false;
                updateLabelsAndButtons();
                convertInputs(diameterField, feedRateField, false);
                updateResults(diameterField, feedRateField, rpmField, resultLabel);
            }
        });
        metricButton.addActionListener((ActionEvent e) -> {
            if (!isMetric) {
                isMetric = true;
                updateLabelsAndButtons();
                convertInputs(diameterField, feedRateField, true);
                updateResults(diameterField, feedRateField, rpmField, resultLabel);
            }
        });

        // Add a larger space between "Result" and "Conversion Formulas"
        gbc.gridy = 8;
        panel.add(Box.createVerticalStrut(20), gbc);

        // Add components for conversion formulas
        JLabel conversionLabel = new JLabel("Conversion Formulas");
        conversionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(conversionLabel, gbc);

        sfmToRpmHeader = new JLabel("SFM to RPM");
        sfmToRpmHeader.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(sfmToRpmHeader, gbc);
        JLabel sfmToRpmLabel = new JLabel("SFM:");
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(sfmToRpmLabel, gbc);
        JTextField sfmField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 11;
        panel.add(sfmField, gbc);
        diameterConversionLabel = new JLabel("Diameter (in):");
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(diameterConversionLabel, gbc);
        JTextField diameterConversionField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 12;
        panel.add(diameterConversionField, gbc);
        JButton sfmToRpmButton = new JButton("Convert SFM to RPM");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        panel.add(sfmToRpmButton, gbc);
        JLabel sfmToRpmResultLabel = new JLabel("RPM: ");
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        panel.add(sfmToRpmResultLabel, gbc);
        sfmToRpmButton.addActionListener((ActionEvent e) -> {
            try {
                double sfm = Double.parseDouble(sfmField.getText());
                double diameter = Double.parseDouble(diameterConversionField.getText());
                double rpm = sfmToRpm(sfm, diameter);
                String format = "%.2f";
                sfmToRpmResultLabel.setText(String.format("RPM: " + format, rpm));
            } catch (NumberFormatException ex) {
                sfmToRpmResultLabel.setText("Error: Please enter valid numbers.");
            }
        });
        iprToIpmHeader = new JLabel("IPR To IPM");
        iprToIpmHeader.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        panel.add(iprToIpmHeader, gbc);
        JLabel iprToIpmLabel = new JLabel("IPR:");
        gbc.gridx = 0;
        gbc.gridy = 16;
        panel.add(iprToIpmLabel, gbc);
        JTextField iprField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 16;
        panel.add(iprField, gbc);
        JLabel rpmConversionLabel = new JLabel("RPM:");
        gbc.gridx = 0;
        gbc.gridy = 17;
        panel.add(rpmConversionLabel, gbc);
        JTextField rpmConversionField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 17;
        panel.add(rpmConversionField, gbc);
        iprToIpmButton = new JButton("Convert IPR to IPM");
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        panel.add(iprToIpmButton, gbc);
        JLabel iprToIpmResultLabel = new JLabel("IPM: ");
        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.gridwidth = 2;
        panel.add(iprToIpmResultLabel, gbc);
        iprToIpmButton.addActionListener((ActionEvent e) -> {
            try {
                double ipr = Double.parseDouble(iprField.getText());
                double rpm = Double.parseDouble(rpmConversionField.getText());
                double ipm = iprToIpm(ipr, rpm);
                String format = "%.2f";
                iprToIpmResultLabel.setText(String.format("IPM: " + format, ipm));
            } catch (NumberFormatException ex) {
                iprToIpmResultLabel.setText("Error: Please enter valid numbers.");
            }
        });

        ipmToIprHeader = new JLabel("IPM to IPR");
        ipmToIprHeader.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 2;
        panel.add(ipmToIprHeader, gbc);
        JLabel ipmToIprLabel = new JLabel("IPM:");
        gbc.gridx = 0;
        gbc.gridy = 21;
        panel.add(ipmToIprLabel, gbc);
        JTextField ipmField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 21;
        panel.add(ipmField, gbc);
        ipmToIprButton = new JButton("Convert IPM to IPR");
        gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.gridwidth = 2;
        panel.add(ipmToIprButton, gbc);
        JLabel ipmToIprResultLabel = new JLabel("IPR: ");
        gbc.gridx = 0;
        gbc.gridy = 23;
        gbc.gridwidth = 2;
        panel.add(ipmToIprResultLabel, gbc);
        ipmToIprButton.addActionListener((ActionEvent e) -> {
            try {
                double ipm = Double.parseDouble(ipmField.getText());
                double rpm = Double.parseDouble(rpmConversionField.getText());
                double ipr = ipmToIpr(ipm, rpm);
                String format = "%.2f";
                ipmToIprResultLabel.setText(String.format("IPR: " + format, ipr));
            } catch (NumberFormatException ex) {
                ipmToIprResultLabel.setText("Error: Please enter valid numbers.");
            }
        });
    }

    private static void updateLabelsAndButtons() {
        diameterLabel.setText(isMetric ? "Diameter (mm):" : "Diameter (in):");
        feedRateLabel.setText(isMetric ? "Feed Rate (m/rev):" : "Feed Rate (in/rev):");
        iprToIpmButton.setText(isMetric ? "Convert MPR to MPM" : "Convert IPR to IPM");
        ipmToIprButton.setText(isMetric ? "Convert MPM to MPR" : "Convert IPM to IPR");
        iprToIpmHeader.setText(isMetric ? "MPR To MPM" : "IPR To IPM");
        ipmToIprHeader.setText(isMetric ? "MPM to MPR" : "IPM to IPR");
        sfmToRpmHeader.setText(isMetric ? "SMM to RPM" : "SFM to RPM");
        diameterConversionLabel.setText(isMetric ? "Diameter (mm)" : "Diameter (in)");
        sfmToRpmLabel.setText(isMetric ? "SMM" : "SFM");
            }
        
        
            private static void convertInputs(JTextField diameterField, JTextField feedRateField, boolean toMetric) {
        try {
            double diameter = Double.parseDouble(diameterField.getText());
            double feedRate = Double.parseDouble(feedRateField.getText());
            if (toMetric) {
                diameter *= 25.4; // Convert inches to mm
                feedRate *= 25.4; // Convert inches to mm
            } else {
                diameter /= 25.4; // Convert mm to inches
                feedRate /= 25.4; // Convert mm to inches
            }
            diameterField.setText(String.valueOf(diameter));
            feedRateField.setText(String.valueOf(feedRate));
        } catch (NumberFormatException ex) {
            // Ignore conversion if input is invalid
        }
    }

    private static void updateResults(JTextField diameterField, JTextField feedRateField, JTextField rpmField, JLabel resultLabel) {
        try {
            double diameter = Double.parseDouble(diameterField.getText());
            double rpm = Double.parseDouble(rpmField.getText());
            double feedRate = Double.parseDouble(feedRateField.getText());
            double cuttingSpeed = calculateCuttingSpeed(diameter, rpm, isMetric);
            double cutTime = calculateCutTime(diameter, feedRate, rpm, isMetric);
            String format = "%.2f";
            resultLabel.setText(String.format(
                    "<html>Cutting Speed: " + format + " %s<br><br>Cut Time: " + format + " min</html>",
                    cuttingSpeed, isMetric ? "m/min" : "ft/min", cutTime
            ));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Please enter valid numbers.");
        }
    }

    public static double calculateCuttingSpeed(double diameter, double rpm, boolean isMetric) {
        double cuttingSpeed = (Math.PI * diameter * rpm) / (isMetric ? 1000 : 12);
        return cuttingSpeed;
    }

    public static double calculateCutTime(double diameter, double feedRate, double rpm, boolean isMetric) {
        double cutTime = (diameter / feedRate) / rpm;
        return isMetric ? cutTime * 25.4 : cutTime; // Convert to mm/min if metric
    }

    // Conversion Formulas
    public static double sfmToRpm(double sfm, double diameter) {
        return (sfm * 12) / (Math.PI * diameter);
    }

    public static double iprToIpm(double ipr, double rpm) {
        return ipr * rpm;
    }

    public static double ipmToIpr(double ipm, double rpm) {
        return ipm / rpm;
    }

    private static void applyDarkMode(Component component, boolean darkMode) {
        if (darkMode) {
            component.setBackground(Color.DARK_GRAY);
            component.setForeground(Color.WHITE);
        } else {
            component.setBackground(Color.LIGHT_GRAY);
            component.setForeground(Color.BLACK);
        }
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                applyDarkMode(child, darkMode);
            }
        }
    }

    public static boolean isDarkMode() {
        return darkMode;
    }
}