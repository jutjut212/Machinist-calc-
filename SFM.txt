import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SFM {
    private static boolean isMetric = false; // Default to Imperial

    public static void open(Point location) {
        JFrame frame = new JFrame("SFM Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
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

        // Add components for SFM calculation
        JLabel diameterLabel = new JLabel("Diameter (in):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(diameterLabel, gbc);

        JTextField diameterField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(diameterField, gbc);

        JLabel rpmLabel = new JLabel("RPM:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(rpmLabel, gbc);

        JTextField rpmField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(rpmField, gbc);

        JButton calculateButton = new JButton("Calculate SFM");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        JLabel resultLabel = new JLabel("SFM: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double diameter = Double.parseDouble(diameterField.getText());
                    double rpm = Double.parseDouble(rpmField.getText());
                    double sfm = sfm(diameter, rpm, isMetric);
                    resultLabel.setText("SFM: " + sfm + (isMetric ? " m/min" : " ft/min"));
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
                diameterLabel.setText("Diameter (in):");
                convertInputs(diameterField, false);
            }
        });

        metricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMetric = true;
                diameterLabel.setText("Diameter (mm):");
                convertInputs(diameterField, true);
            }
        });
    }

    private static void convertInputs(JTextField diameterField, boolean toMetric) {
        try {
            double diameter = Double.parseDouble(diameterField.getText());
            if (toMetric) {
                diameter *= 25.4; // Convert inches to mm
            } else {
                diameter /= 25.4; // Convert mm to inches
            }
            diameterField.setText(String.valueOf(diameter));
        } catch (NumberFormatException ex) {
            // Ignore conversion if input is invalid
        }
    }

    public static double sfm(double diameter, double rpm, boolean isMetric) {
        double sfm = (Math.PI * diameter * rpm) / 12;
        return isMetric ? sfm * 0.3048 : sfm; // Convert to m/min if metric
    }
}