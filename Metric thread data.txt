import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetricThreadData {
    public static void open(Point location) {
        JFrame frame = new JFrame("Metric Thread Data");
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
            "M1 x 0.2", "M1 x 0.25", "M1.2 x 0.2", "M1.2 x 0.25", "M1.4 x 0.2", "M1.4 x 0.3", "M1.6 x 0.2", "M1.6 x 0.35", "M1.8 x 0.2", "M1.8 x 0.35",
            "M2 x 0.25", "M2 x 0.4", "M2.2 x 0.25", "M2.2 x 0.45", "M2.5 x 0.35", "M2.5 x 0.45", "M3 x 0.35", "M3 x 0.5", "M3.5 x 0.35", "M3.5 x 0.6",
            "M4 x 0.5", "M4 x 0.7", "M5 x 0.5", "M5 x 0.8", "M6 x 0.75", "M6 x 1.0", "M7 x 0.75", "M7 x 1.0", "M8 x 1.0", "M8 x 1.25", "M10 x 1.25", "M10 x 1.5",
            "M12 x 1.5", "M12 x 1.75", "M14 x 1.5", "M14 x 2.0", "M16 x 1.5", "M16 x 2.0", "M18 x 1.5", "M18 x 2.5", "M20 x 1.5", "M20 x 2.5", "M22 x 1.5", "M22 x 2.5",
            "M24 x 2.0", "M24 x 3.0", "M27 x 2.0", "M27 x 3.0", "M30 x 2.0", "M30 x 3.5", "M33 x 2.0", "M33 x 3.5", "M36 x 2.0", "M36 x 4.0", "M39 x 2.0", "M39 x 4.0",
            "M42 x 2.0", "M42 x 4.5", "M45 x 2.0", "M45 x 4.5", "M48 x 2.0", "M48 x 5.0", "M52 x 2.0", "M52 x 5.0", "M56 x 2.0", "M56 x 5.5", "M60 x 2.0", "M60 x 5.5",
            "M64 x 2.0", "M64 x 6.0", "M68 x 2.0", "M68 x 6.0", "M72 x 2.0", "M72 x 6.0", "M76 x 2.0", "M76 x 6.0", "M80 x 2.0", "M80 x 6.0", "M85 x 2.0", "M85 x 6.0",
            "M90 x 2.0", "M90 x 6.0", "M95 x 2.0", "M95 x 6.0", "M100 x 2.0", "M100 x 6.0"
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

        JTextArea dataArea = new JTextArea(10, 30);
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
                    Double pitch = ThreadDataStorage.getPitch(threadSize);
                    if (majorDiameter != null && pitch != null) {
                        String externalData = calculateExternalThreadData(majorDiameter, pitch, decimalPlaces);
                        String internalData = calculateInternalThreadData(majorDiameter, pitch, decimalPlaces);
                        String leadAndTapDrillData = calculateLeadAndTapDrillSize(majorDiameter, pitch, decimalPlaces);
                        dataArea.setText(externalData + "\n\n" + internalData + "\n\n" + leadAndTapDrillData);
                    } else {
                        dataArea.setText("Data not available for the selected thread size.");
                    }
                } else {
                    dataArea.setText("Please select a valid thread size and decimal places.");
                }
            }
        });
    }

    private static String calculateExternalThreadData(double majorDiameter, double pitch, int decimalPlaces) {
        double threadDepth = 0.613 * pitch;
        double minorDiameterExternal = majorDiameter - (2 * threadDepth);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "External Thread Data:\nMajor Diameter: " + format + " mm\nPitch: " + format + " mm\nThread Depth: " + format + " mm\nMinor Diameter (d1): " + format + " mm",
            majorDiameter, pitch, threadDepth, minorDiameterExternal
        );
    }

    private static String calculateInternalThreadData(double majorDiameter, double pitch, int decimalPlaces) {
        double threadDepth = 0.613 * pitch;
        double minorDiameterInternal = majorDiameter - (2 * threadDepth);

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "Internal Thread Data:\nMajor Diameter: " + format + " mm\nPitch: " + format + " mm\nThread Depth: " + format + " mm\nMinor Diameter (D1): " + format + " mm",
            majorDiameter, pitch, threadDepth, minorDiameterInternal
        );
    }

    private static String calculateLeadAndTapDrillSize(double majorDiameter, double pitch, int decimalPlaces) {
        double lead = pitch; // For single-start threads, lead is equal to pitch
        double tapDrillSize = majorDiameter - pitch;

        String format = "%." + decimalPlaces + "f";

        return String.format(
            "Lead and Tap Drill Size:\nLead: " + format + " mm\nTap Drill Size: " + format + " mm",
            lead, tapDrillSize
        );
    }
}
