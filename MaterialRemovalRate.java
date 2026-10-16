import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaterialRemovalRate {
    private static boolean isMetric = false; // Default to Imperial

    public static void open(Point location) {
        JFrame frame = new JFrame("Material Removal Rate Calculator");
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

        // Add components for material removal rate calculation
        JLabel widthOfCutLabel = new JLabel("Width of Cut (in):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(widthOfCutLabel, gbc);

        JTextField widthOfCutField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(widthOfCutField, gbc);

        JLabel depthOfCutLabel = new JLabel("Depth of Cut (in):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(depthOfCutLabel, gbc);

        JTextField depthOfCutField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(depthOfCutField, gbc);

        JLabel feedRateLabel = new JLabel("Feed Rate (in/min):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(feedRateLabel, gbc);

        JTextField feedRateField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(feedRateField, gbc);

        JButton calculateButton = new JButton("Calculate Material Removal Rate");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        JLabel resultLabel = new JLabel("Material Removal Rate: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double widthOfCut = Double.parseDouble(widthOfCutField.getText());
                    double depthOfCut = Double.parseDouble(depthOfCutField.getText());
                    double feedRate = Double.parseDouble(feedRateField.getText());
                    double materialRemovalRate = materialRemovalRate(widthOfCut, depthOfCut, feedRate, isMetric);
                    resultLabel.setText("Material Removal Rate: " + materialRemovalRate + (isMetric ? " cm³/min" : " in³/min"));
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
                widthOfCutLabel.setText("Width of Cut (in):");
                depthOfCutLabel.setText("Depth of Cut (in):");
                feedRateLabel.setText("Feed Rate (in/min):");
                convertInputs(widthOfCutField, depthOfCutField, feedRateField, false);
            }
        });

        metricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMetric = true;
                widthOfCutLabel.setText("Width of Cut (mm):");
                depthOfCutLabel.setText("Depth of Cut (mm):");
                feedRateLabel.setText("Feed Rate (mm/min):");
                convertInputs(widthOfCutField, depthOfCutField, feedRateField, true);
            }
        });
    }

    private static void convertInputs(JTextField widthOfCutField, JTextField depthOfCutField, JTextField feedRateField, boolean toMetric) {
        try {
            double widthOfCut = Double.parseDouble(widthOfCutField.getText());
            double depthOfCut = Double.parseDouble(depthOfCutField.getText());
            double feedRate = Double.parseDouble(feedRateField.getText());

            if (toMetric) {
                widthOfCut *= 25.4; // Convert inches to mm
                depthOfCut *= 25.4; // Convert inches to mm
                feedRate *= 25.4; // Convert inches/min to mm/min
            } else {
                widthOfCut /= 25.4; // Convert mm to inches
                depthOfCut /= 25.4; // Convert mm to inches
                feedRate /= 25.4; // Convert mm/min to inches/min
            }

            widthOfCutField.setText(String.valueOf(widthOfCut));
            depthOfCutField.setText(String.valueOf(depthOfCut));
            feedRateField.setText(String.valueOf(feedRate));
        } catch (NumberFormatException ex) {
            // Ignore conversion if input is invalid
        }
    }

    public static double materialRemovalRate(double widthOfCut, double depthOfCut, double feedRate, boolean isMetric) {
        double mrr = widthOfCut * depthOfCut * feedRate; // in cubic inches per minute
        return isMetric ? mrr * 16.3871 : mrr; // Convert to cubic centimeters per minute if metric
    }
}