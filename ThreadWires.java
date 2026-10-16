import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ThreadWires {
    public static void open(Point location, JFrame parentFrame) {
        JFrame frame = new JFrame("Thread Wires");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame, parentFrame);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame, JFrame parentFrame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        String[] labels = {"Unit:", "Major Diameter:", "TPI:", "Pitch:", "Included Angle:", "Best Wire Size:", "Nominal Pitch Diameter", "Preferred Wire Diameter:"};
        JTextField[] fields = new JTextField[labels.length - 1];
        JComboBox<String> unitComboBox = new JComboBox<>(new String[]{"Imperial", "Metric"});
        JComboBox<String> angleComboBox = new JComboBox<>(new String[]{"60Degree", "55Degree", "47.5Degree", "40Degree", "29Degree"});

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            panel.add(new JLabel(labels[i]), gbc);

            if (i == 0) {
                gbc.gridx = 1;
                panel.add(unitComboBox, gbc);
            } else if (i == 4) {
                gbc.gridx = 1;
                panel.add(angleComboBox, gbc);
            } else {
                fields[i - 1] = new JTextField(10);
                gbc.gridx = 1;
                panel.add(fields[i - 1], gbc);
            }
        }

        fields[5].setEditable(false); // Best Wire Size field

        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = labels.length + 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        unitComboBox.addActionListener(e -> {
            boolean isMetric = "Metric".equals(unitComboBox.getSelectedItem());
            fields[1].setEnabled(!isMetric);
            fields[2].setEnabled(isMetric);
        });

        KeyAdapter autofillPitchAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ("Imperial".equals(unitComboBox.getSelectedItem())) {
                    try {
                        double tpi = Double.parseDouble(fields[1].getText());
                        fields[2].setText(String.format("%.5f", ThreadDataFormulas.calculatePitch(tpi)));
                    } catch (NumberFormatException ex) {
                        fields[2].setText("");
                    }
                }
            }
        };

        fields[0].addKeyListener(autofillPitchAdapter);
        fields[1].addKeyListener(autofillPitchAdapter);

        ActionListener autofillBestWireSizeListener = e -> {
            try {
                double pitch = Double.parseDouble(fields[2].getText());
                double angleInDegrees = switch ((String) angleComboBox.getSelectedItem()) {
                    case "60Degree" -> 60.0;
                    case "55Degree" -> 55.0;
                    case "47.5Degree" -> 47.5;
                    case "40Degree" -> 40.0;
                    default -> 29.0;
                };
                fields[4].setText(String.format("%.5f", ThreadDataFormulas.calculateBestWireSize(pitch, angleInDegrees)));
            } catch (NumberFormatException ex) {
                fields[4].setText("");
            }
        };

        fields[2].addActionListener(autofillBestWireSizeListener);
        angleComboBox.addActionListener(autofillBestWireSizeListener);

        KeyAdapter autofillNominalPitchDiameterAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    double majorDiameter = Double.parseDouble(fields[0].getText());
                    double pitch = Double.parseDouble(fields[2].getText());
                    double angleInDegrees = switch ((String) angleComboBox.getSelectedItem()) {
                        case "60Degree" -> 60.0;
                        case "55Degree" -> 55.0;
                        case "47.5Degree" -> 47.5;
                        case "40Degree" -> 40.0;
                        default -> 29.0;
                    };
                    fields[5].setText(String.format("%.5f", ThreadDataFormulas.NomPD(majorDiameter, pitch, angleInDegrees)));
                } catch (NumberFormatException ex) {
                    fields[5].setText("");
                }
            }
        };

        fields[0].addKeyListener(autofillNominalPitchDiameterAdapter);
        fields[2].addKeyListener(autofillNominalPitchDiameterAdapter);
        angleComboBox.addActionListener(e -> autofillNominalPitchDiameterAdapter.keyReleased(null));

        calculateButton.addActionListener(e -> {
            try {
                double majorDiameter = fields[0].getText().isEmpty() ? 0 : Double.parseDouble(fields[0].getText());
                double tpi = fields[1].isEnabled() && !fields[1].getText().isEmpty() ? Double.parseDouble(fields[1].getText()) : 0;
                double pitch = fields[2].isEnabled() && !fields[2].getText().isEmpty() ? Double.parseDouble(fields[2].getText()) : 0;
                double bestWireSize = fields[4].getText().isEmpty() ? 0 : Double.parseDouble(fields[4].getText());
                double preferredWireDiameter = fields[6].getText().isEmpty() ? 0 : Double.parseDouble(fields[6].getText());
                double pitchDiameter = fields[5].getText().isEmpty() ? 0 : Double.parseDouble(fields[5].getText());
                String angle = (String) angleComboBox.getSelectedItem();
                String selectedUnit = (String) unitComboBox.getSelectedItem();

                if ("Imperial".equals(selectedUnit) && pitch == 0 && tpi != 0) {
                    pitch = ThreadDataFormulas.calculatePitch(tpi);
                }

                double measurementOverBestWireSize = 0;
                double measurementOverPreferredWireSize = 0;

                if (pitch != 0 && bestWireSize != 0) {
                    switch (angle) {
                        case "60Degree" -> {
                            measurementOverBestWireSize = ThreadDataFormulas.calculateMOWUnifiedThread(majorDiameter, tpi, bestWireSize);
                            measurementOverPreferredWireSize = ThreadDataFormulas.calculateMOWUnifiedThread(majorDiameter, tpi, preferredWireDiameter);
                        }
                        case "55Degree" -> {
                            measurementOverBestWireSize = ThreadDataFormulas.calculateMOWWhitworthThread(majorDiameter, tpi, bestWireSize);
                            measurementOverPreferredWireSize = ThreadDataFormulas.calculateMOWWhitworthThread(majorDiameter, tpi, preferredWireDiameter);
                        }
                        case "47.5Degree" -> {
                            measurementOverBestWireSize = ThreadDataFormulas.calculateMOWThread47_5Degree(majorDiameter, pitch, bestWireSize);
                            measurementOverPreferredWireSize = ThreadDataFormulas.calculateMOWThread47_5Degree(majorDiameter, tpi, preferredWireDiameter);
                        }
                        case "40Degree" -> {
                            measurementOverBestWireSize = ThreadDataFormulas.calculateMOWThread40Degree(majorDiameter, tpi, bestWireSize);
                            measurementOverPreferredWireSize = ThreadDataFormulas.calculateMOWThread40Degree(majorDiameter, tpi, preferredWireDiameter);
                        }
                        case "29Degree" -> {
                            measurementOverBestWireSize = ThreadDataFormulas.calculateMOWThread29Degree(majorDiameter, tpi);
                            measurementOverPreferredWireSize = ThreadDataFormulas.calculateMOWThread29Degree(majorDiameter, tpi);
                        }
                        default -> throw new IllegalArgumentException("Invalid angle selected");
                    }
                }

                resultArea.setText(String.format(
                        "Thread Data:\n" +
                        "Major Diameter: %.5f inches\n" +
                        "TPI: %.5f\n" +
                        "Pitch: %.5f inches\n" +
                        "Included Angle: %s\n" +
                        "Best Wire Size: %.5f inches\n" +
                        "Nominal Pitch Diameter: %.5f inches\n" +
                        "Measurement Over Best Wire Size: %.5f inches\n" +
                        "Preferred Wire Diameter: %.5f inches\n" +
                        "Measurement Over Preferred Wire Size: %.5f inches",
                        majorDiameter, tpi, pitch, angle, bestWireSize, pitchDiameter, measurementOverBestWireSize, preferredWireDiameter, measurementOverPreferredWireSize));
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter valid numerical values.");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            parentFrame.setVisible(true);
        });
    }
}
