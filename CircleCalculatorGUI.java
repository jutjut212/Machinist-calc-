import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class CircleCalculatorGUI {
    private static Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static boolean darkMode = prefs.getBoolean("darkMode", false); // Load dark mode setting

    public static void open(Point location) {
        JFrame frame = new JFrame("Circle Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame);

        if (darkMode) {
            applyDarkMode(panel, darkMode);
        }

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 22);

        // Add a "Back to Main Screen" button
        JButton backButton = new JButton("Back to Main Screen");
        backButton.setFont(labelFont);
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

        // Add input fields and labels
        JLabel radiusLabel = new JLabel("Radius:");
        radiusLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(radiusLabel, gbc);

        JTextField radiusField = new JTextField(15);
        radiusField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(radiusField, gbc);

        JLabel arcLengthLabel = new JLabel("Arc Length:");
        arcLengthLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(arcLengthLabel, gbc);

        JTextField arcLengthField = new JTextField(15);
        arcLengthField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(arcLengthField, gbc);

        JLabel angleLabel = new JLabel("Included Angle (degrees):");
        angleLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(angleLabel, gbc);

        JTextField angleField = new JTextField(15);
        angleField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(angleField, gbc);

        JLabel chordLengthLabel = new JLabel("Chord Length:");
        chordLengthLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(chordLengthLabel, gbc);

        JTextField chordLengthField = new JTextField(15);
        chordLengthField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(chordLengthField, gbc);

        JLabel segmentHeightLabel = new JLabel("Segment Height:");
        segmentHeightLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(segmentHeightLabel, gbc);

        JTextField segmentHeightField = new JTextField(15);
        segmentHeightField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(segmentHeightField, gbc);

        JLabel segmentAreaLabel = new JLabel("Segment Area:");
        segmentAreaLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(segmentAreaLabel, gbc);

        JTextField segmentAreaField = new JTextField(15);
        segmentAreaField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(segmentAreaField, gbc);

        JLabel sectorAreaLabel = new JLabel("Sector Area:");
        sectorAreaLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(sectorAreaLabel, gbc);

        JTextField sectorAreaField = new JTextField(15);
        sectorAreaField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(sectorAreaField, gbc);

        JLabel tangentLengthLabel = new JLabel("Tangent Length:");
        tangentLengthLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(tangentLengthLabel, gbc);

        JTextField tangentLengthField = new JTextField(15);
        tangentLengthField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(tangentLengthField, gbc);

        // Add calculate and clear buttons
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);
        gbc.gridy = 10;
        panel.add(clearButton, gbc);

        // Add action listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double radius = Double.parseDouble(radiusField.getText());
                    double includedAngle = Double.parseDouble(angleField.getText());

                    double angleRadians = Math.toRadians(includedAngle);

                    if (radius > 0 && angleRadians > 0) {
                        double arcLength = radius * angleRadians;
                        double chordLength = 2 * radius * Math.sin(angleRadians / 2);
                        double segmentHeight = radius * (1 - Math.cos(angleRadians / 2));
                        double segmentArea = radius * radius * Math.acos((radius - segmentHeight) / radius) - (radius - segmentHeight) * Math.sqrt(2 * radius * segmentHeight - segmentHeight * segmentHeight);
                        double sectorArea = 0.5 * radius * radius * angleRadians;
                        double tangentLength = radius * Math.tan(angleRadians / 2);

                        arcLengthField.setText(String.valueOf(arcLength));
                        chordLengthField.setText(String.valueOf(chordLength));
                        segmentHeightField.setText(String.valueOf(segmentHeight));
                        segmentAreaField.setText(String.valueOf(segmentArea));
                        sectorAreaField.setText(String.valueOf(sectorArea));
                        tangentLengthField.setText(String.valueOf(tangentLength));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid numbers.");
                }
            }
        });

        // Add action listener to the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radiusField.setText("");
                arcLengthField.setText("");
                angleField.setText("");
                chordLengthField.setText("");
                segmentHeightField.setText("");
                segmentAreaField.setText("");
                sectorAreaField.setText("");
                tangentLengthField.setText("");
            }
        });

        // Add focus listeners to highlight the selected text field
        radiusField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                radiusField.selectAll();
            }
        });
        arcLengthField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                arcLengthField.selectAll();
            }
        });
        angleField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                angleField.selectAll();
            }
        });
        chordLengthField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                chordLengthField.selectAll();
            }
        });
        segmentHeightField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                segmentHeightField.selectAll();
            }
        });
        segmentAreaField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                segmentAreaField.selectAll();
            }
        });
        sectorAreaField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sectorAreaField.selectAll();
            }
        });
        tangentLengthField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tangentLengthField.selectAll();
            }
        });
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