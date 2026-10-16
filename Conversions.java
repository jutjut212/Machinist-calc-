import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.prefs.Preferences;

public class Conversions {
    private static Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static boolean darkMode = prefs.getBoolean("darkMode", false); // Load dark mode setting
    

    public static void open(Point location) {
        JFrame frame = new JFrame("Conversions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 700); // Increased size to accommodate new section
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame);

        if (darkMode) {
            applyDarkMode(panel, darkMode);
        }

        frame.setVisible(true); // Move this line outside the if block
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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                CalculatorMainScreen.open(frame.getLocation()); // Open the main screen
            }
        });

        // Add unit selection combo boxes
        JLabel fromUnitLabel = new JLabel("From Unit:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(fromUnitLabel, gbc);

        String[] units = {"inches", "feet", "miles", "mm", "cm", "m", "km"};
        JComboBox<String> fromUnitComboBox = new JComboBox<>(units);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(fromUnitComboBox, gbc);

        JLabel toUnitLabel = new JLabel("To Unit:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(toUnitLabel, gbc);

        JComboBox<String> toUnitComboBox = new JComboBox<>(units);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(toUnitComboBox, gbc);

        // Add components for conversions
        JLabel inputLabel = new JLabel("Input Value:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(inputLabel, gbc);

        JTextField inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER); // Center-align text
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(inputField, gbc);

        JButton convertButton = new JButton("Convert");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        JLabel resultLabel = new JLabel("Result: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        JLabel fractionLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(fractionLabel, gbc);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double inputValue = Double.parseDouble(inputField.getText());
                    String fromUnit = (String) fromUnitComboBox.getSelectedItem();
                    String toUnit = (String) toUnitComboBox.getSelectedItem();
                    double result = convertUnits(inputValue, fromUnit, toUnit);
                    DecimalFormat df = new DecimalFormat("#.#####");
                    resultLabel.setText("Result: " + df.format(result) + " " + toUnit);
                    double resultInInches = convertUnits(result, toUnit, "inches");
                    fractionLabel.setText("Fractional (inches): " + formatAsFraction(resultInInches) + " inches");
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Please enter a valid number.");
                    fractionLabel.setText("");
                }
            }
        });

        // Add section for converting fractions to decimals
        JLabel fractionToDecimalLabel = new JLabel("Convert Fraction to Decimal:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(fractionToDecimalLabel, gbc);

        JTextField fractionField = new JTextField();
        fractionField.setHorizontalAlignment(JTextField.CENTER); // Center-align text
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(fractionField, gbc);

        JButton fractionConvertButton = new JButton("Convert Fraction");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(fractionConvertButton, gbc);

        JLabel decimalResultLabel = new JLabel("Decimal Result: ");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(decimalResultLabel, gbc);

        fractionConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String fraction = fractionField.getText();
                    double decimalResult = convertFractionToDecimal(fraction);
                    DecimalFormat df = new DecimalFormat("#.#####");
                    decimalResultLabel.setText("Decimal Result: " + df.format(decimalResult));
                } catch (NumberFormatException ex) {
                    decimalResultLabel.setText("Error: Please enter a valid fraction.");
                }
            }
        });

        // Add section for converting fractions to metric units
        JLabel fractionToMetricLabel = new JLabel("Convert Fraction to Metric:");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(fractionToMetricLabel, gbc);

        JTextField fractionMetricField = new JTextField();
        fractionMetricField.setHorizontalAlignment(JTextField.CENTER); // Center-align text
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        panel.add(fractionMetricField, gbc);

        JComboBox<String> metricUnitComboBox = new JComboBox<>(new String[]{"mm", "cm", "m", "km"});
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        panel.add(metricUnitComboBox, gbc);

        JButton fractionToMetricButton = new JButton("Convert to Metric");
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        panel.add(fractionToMetricButton, gbc);

        JLabel metricResultLabel = new JLabel("Metric Result: ");
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        panel.add(metricResultLabel, gbc);

        fractionToMetricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String fraction = fractionMetricField.getText();
                    double decimalValue = convertFractionToDecimal(fraction);
                    String metricUnit = (String) metricUnitComboBox.getSelectedItem();
                    double metricResult = convertUnits(decimalValue, "inches", metricUnit);
                    DecimalFormat df = new DecimalFormat("#.#####");
                    metricResultLabel.setText("Metric Result: " + df.format(metricResult) + " " + metricUnit);
                } catch (NumberFormatException ex) {
                    metricResultLabel.setText("Error: Please enter a valid fraction.");
                }
            }
        });
    }

    private static double convertUnits(double value, String fromUnit, String toUnit) {
        double result = value;
    
        // Conversion factors
        final double INCH_TO_MM = 25.4;
        final double INCH_TO_CM = 2.54;
        final double INCH_TO_M = 0.0254;
        final double INCH_TO_KM = 0.0000254;
        final double INCH_TO_FEET = 1.0 / 12.0;
        final double INCH_TO_MILES = 1.0 / 63360.0;
        final double MM_TO_INCH = 1 / INCH_TO_MM;
        final double CM_TO_INCH = 1 / INCH_TO_CM;
        final double M_TO_INCH = 1 / INCH_TO_M;
        final double KM_TO_INCH = 1 / INCH_TO_KM;
        final double FEET_TO_INCH = 12.0;
        final double MILES_TO_INCH = 63360.0;
    
        // Convert from inches to the desired unit
        if (fromUnit.equals("inches")) {
            switch (toUnit) {
                case "mm":
                    result = value * INCH_TO_MM;
                    break;
                case "cm":
                    result = value * INCH_TO_CM;
                    break;
                case "m":
                    result = value * INCH_TO_M;
                    break;
                case "km":
                    result = value * INCH_TO_KM;
                    break;
                case "feet":
                    result = value * INCH_TO_FEET;
                    break;
                case "miles":
                    result = value * INCH_TO_MILES;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported unit: " + toUnit);
            }
        } else {
            // Convert from other units to inches first
            switch (fromUnit) {
                case "mm":
                    result = value * MM_TO_INCH;
                    break;
                case "cm":
                    result = value * CM_TO_INCH;
                    break;
                case "m":
                    result = value * M_TO_INCH;
                    break;
                case "km":
                    result = value * KM_TO_INCH;
                    break;
                case "feet":
                    result = value * FEET_TO_INCH;
                    break;
                case "miles":
                    result = value * MILES_TO_INCH;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported unit: " + fromUnit);
            }
            // Convert from inches to the desired unit
            result = convertUnits(result, "inches", toUnit);
        }
    
        return result;
    }
    

    private static double convertFractionToDecimal(String fraction) throws NumberFormatException {
        if (fraction.contains("/")) {
            String[] parts = fraction.split("/");
            if (parts.length == 2) {
                try {
                    double numerator = Double.parseDouble(parts[0].trim());
                    double denominator = Double.parseDouble(parts[1].trim());
                    return numerator / denominator;
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid fraction format");
                }
            } else {
                throw new NumberFormatException("Invalid fraction format");
            }
        } else {
            throw new NumberFormatException("Invalid fraction format");
        }
    }
    

    private static String formatAsFraction(double decimal) {
        final double TOLERANCE = 1.0E-6; // Tolerance for approximation
        int numerator = 1;
        int denominator = 1;
        double fraction = (double) numerator / denominator;
    
        while (Math.abs(fraction - decimal) > TOLERANCE) {
            if (fraction < decimal) {
                numerator++;
            } else {
                denominator++;
                numerator = (int) Math.round(decimal * denominator);
            }
            fraction = (double) numerator / denominator;
        }
    
        return numerator + "/" + denominator;
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