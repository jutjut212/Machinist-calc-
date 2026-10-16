import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UNCThreadData {
    public static void open(Point location) {
        JFrame frame = new JFrame("UNC Thread Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocation(location);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = .25;
        gbc.weighty = 0;

        JButton backButton = new JButton("Back to Thread Data Screen");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                ThreadData.open(frame.getLocation()); // Open the Thread Data screen
            }
        });

        JLabel threadSizeLabel = new JLabel("Select Thread Size:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(threadSizeLabel, gbc);

        String[] threadSizes = {
            "#0-80 UNC", "#1-64 UNC", "#2-56 UNC", "#3-48 UNC", "#4-40 UNC", "#5-40 UNC", "#6-32 UNC", "#8-32 UNC", "#10-24 UNC", "#12-24 UNC",
            "1/4-20 UNC", "5/16-18 UNC", "3/8-16 UNC", "7/16-14 UNC", "1/2-13 UNC", "9/16-12 UNC", "5/8-11 UNC", "3/4-10 UNC", "7/8-9 UNC", "1-8 UNC",
            "1 1/8-7 UNC", "1 1/4-7 UNC", "1 3/8-6 UNC", "1 1/2-6 UNC", "1 3/4-5 UNC", "2-4.5 UNC", "2 1/4-4.5 UNC", "2 1/2-4 UNC", "2 3/4-4 UNC", "3-4 UNC",
            "3 1/4-4 UNC", "3 1/2-4 UNC", "3 3/4-4 UNC", "4-4 UNC"
        };
        JComboBox<String> threadSizeComboBox = new JComboBox<>(threadSizes);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(threadSizeComboBox, gbc);

        JLabel decimalPlacesLabel = new JLabel("Decimal Places:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(decimalPlacesLabel, gbc);

        JCheckBox decimalPlaces3 = new JCheckBox("3");
        JCheckBox decimalPlaces4 = new JCheckBox("4");
        JCheckBox decimalPlaces5 = new JCheckBox("5");

        ButtonGroup decimalPlacesGroup = new ButtonGroup();
        decimalPlacesGroup.add(decimalPlaces3);
        decimalPlacesGroup.add(decimalPlaces4);
        decimalPlacesGroup.add(decimalPlaces5);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(decimalPlaces3);
        checkBoxPanel.add(decimalPlaces4);
        checkBoxPanel.add(decimalPlaces5);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(checkBoxPanel, gbc);

        JButton showDataButton = new JButton("Show Data");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(showDataButton, gbc);

        JEditorPane dataArea = new JEditorPane();
        dataArea.setContentType("text/html");
        dataArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dataArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);


        showDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String threadSize = (String) threadSizeComboBox.getSelectedItem();
                int decimalPlaces = -1;

                if (decimalPlaces3.isSelected()) {
                    decimalPlaces = 3;
                } else if (decimalPlaces4.isSelected()) {
                    decimalPlaces = 4;
                } else if (decimalPlaces5.isSelected()) {
                    decimalPlaces = 5;
                }

                if (threadSize != null && decimalPlaces != -1) {
                    Double majorDiameter = ThreadDataStorage.getMajorDiameter(threadSize);
                    Double tpi = ThreadDataStorage.getTPI(threadSize);
                    if (majorDiameter != null && tpi != null) {
                        String externalData = calculateExternalThreadData(majorDiameter, tpi, decimalPlaces);
                        String internalData = calculateInternalThreadData(majorDiameter, tpi, decimalPlaces);
                        String leadAndTapDrillData = calculateLeadAndTapDrillSize(majorDiameter, tpi, decimalPlaces);
                        String toleranceData = calculateToleranceData(majorDiameter, tpi, decimalPlaces);
                        dataArea.setText("<html>" + externalData + "<br><br>" + internalData + "<br><br>" + leadAndTapDrillData + "<br><br>" + toleranceData + "</html>");
                    } else {
                        dataArea.setText("Data not available for the selected thread size.");
                    }
                } else {
                    dataArea.setText("Please select a valid thread size and decimal places.");
                }
            }
        });
    }

    private static String calculateExternalThreadData(double majorDiameter, double tpi, int decimalPlaces) {
        double pitch = ThreadDataFormulas.calculatePitch(tpi);
        double threadDepth = ThreadDataFormulas.calculateThreadDepth(pitch);
        double minorDiameterExternal = ThreadDataFormulas.calculateMinorDiameterExternal(majorDiameter, pitch);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "<b>External Thread Data:</b><br>" +
            "Major Diameter: <b>" + format + "</b> inches<br>" +
            "TPI: <b>%.0f</b><br>" +
            "Pitch (P): <b>" + format + "</b> inches<br>" +
            "Thread Depth (H): <b>" + format + "</b> inches<br>" +
            "Minor Diameter (d1): <b>" + format + "</b> inches",
            majorDiameter, tpi, pitch, threadDepth, minorDiameterExternal
        );
    }

    private static String calculateInternalThreadData(double majorDiameter, double tpi, int decimalPlaces) {
        double pitch = ThreadDataFormulas.calculatePitch(tpi);
        double threadDepth = ThreadDataFormulas.calculateThreadDepth(pitch);
        double minorDiameterInternal = ThreadDataFormulas.calculateMinorDiameterInternal(majorDiameter, threadDepth);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "<b>Internal Thread Data:</b><br>" +
            "Major Diameter: <b>" + format + "</b> inches<br>" +
            "TPI: <b>%.0f</b><br>" +
            "Pitch (P): <b>" + format + "</b> inches<br>" +
            "Thread Depth (H): <b>" + format + "</b> inches<br>" +
            "Minor Diameter (D1): <b>" + format + "</b> inches",
            majorDiameter, tpi, pitch, threadDepth, minorDiameterInternal
        );
    }

    private static String calculateLeadAndTapDrillSize(double majorDiameter, double tpi, int decimalPlaces) {
        double pitch = ThreadDataFormulas.calculatePitch(tpi);
        double lead = ThreadDataFormulas.calculateLead(pitch);
        double tapDrillSize = ThreadDataFormulas.calculateTapDrillSize(majorDiameter, pitch);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "<b>Lead and Tap Drill Size:</b><br>" +
            "Lead: <b>" + format + "</b> inches<br>" +
            "Tap Drill Size: <b>" + format + "</b> inches",
            lead, tapDrillSize
        );
    }

    private static String calculateToleranceData(double majorDiameter, double tpi, int decimalPlaces) {
        double pitch = ThreadDataFormulas.calculatePitch(tpi);
        double LE = 5 * pitch; // Default length of engagement

        // External Thread Tolerances
        double Td = ThreadDataFormulas.calculateExternalMajorDiameterTolerance(pitch);
        double Td2 = ThreadDataFormulas.calculateExternalPitchDiameterTolerance(majorDiameter, pitch, LE);
        double Td2_3A = ThreadDataFormulas.calculateExternalPitchDiameterTolerance3A(majorDiameter, pitch, LE);

        // Internal Thread Tolerances
        double TD1 = ThreadDataFormulas.calculateInternalMinorDiameterTolerance(pitch);
        double TD2 = ThreadDataFormulas.calculateInternalPitchDiameterTolerance(majorDiameter, pitch, LE);
        double TD2_3B = ThreadDataFormulas.calculateInternalPitchDiameterTolerance3B(majorDiameter, pitch, LE);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "<b>Tolerance Data:</b><br>" +
            "<b>External Thread Tolerances:</b><br>" +
            "Major Diameter Tolerance (Td): <b>" + format + "</b> inches<br>" +
            "Pitch Diameter Tolerance (Td2 - Class 2A): <b>" + format + "</b> inches<br>" +
            "Pitch Diameter Tolerance (Td2 - Class 3A): <b>" + format + "</b> inches<br><br>" +
            "<b>Internal Thread Tolerances:</b><br>" +
            "Minor Diameter Tolerance (TD1): <b>" + format + "</b> inches<br>" +
            "Pitch Diameter Tolerance (TD2 - Class 2B): <b>" + format + "</b> inches<br>" +
            "Pitch Diameter Tolerance (TD2 - Class 3B): <b>" + format + "</b> inches",
            Td, Td2, Td2_3A, TD1, TD2, TD2_3B
        );
    }
}
