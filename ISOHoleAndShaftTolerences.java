import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ISOHoleAndShaftTolerences extends JFrame {
    private JComboBox<String> fitDropdown;
    private JComboBox<String> sizeDropdown;
    private JLabel toleranceLabel;
    private Map<String, Map<String, String[]>> toleranceMap;

    public ISOHoleAndShaftTolerances(Point location) {
        setTitle("ISO Hole and Shaft Tolerances");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setLocation(location);

        JButton openButton = new JButton("Open CSV");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCSVFile();
            }
        });

        fitDropdown = new JComboBox<>();
        sizeDropdown = new JComboBox<>();
        toleranceLabel = new JLabel("Tolerance: ");

        fitDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSizeDropdown();
            }
        });

        sizeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTolerance();
            }
        });

        add(openButton);
        add(fitDropdown);
        add(sizeDropdown);
        add(toleranceLabel);
    }

    public static void open(Point location) {
        SwingUtilities.invokeLater(() -> {
            ISOHoleAndShaftTolerances frame = new ISOHoleAndShaftTolerances(location);
            frame.setVisible(true);
        });
    }

    private void openCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            toleranceMap = readCSV(selectedFile.getAbsolutePath());
            updateFitDropdown();
        }
    }

    private void updateFitDropdown() {
        fitDropdown.removeAllItems();
        if (toleranceMap != null) {
            for (String fit : toleranceMap.keySet()) {
                fitDropdown.addItem(fit);
            }
        }
        updateSizeDropdown();
    }

    private void updateSizeDropdown() {
        sizeDropdown.removeAllItems();
        String selectedFit = (String) fitDropdown.getSelectedItem();
        if (selectedFit != null && toleranceMap != null) {
            for (String size : toleranceMap.get(selectedFit).keySet()) {
                sizeDropdown.addItem(size);
            }
        }
        updateTolerance();
    }

    private void updateTolerance() {
        String selectedFit = (String) fitDropdown.getSelectedItem();
        String selectedSize = (String) sizeDropdown.getSelectedItem();
        if (selectedFit != null && selectedSize != null && toleranceMap != null) {
            String[] tolerances = toleranceMap.get(selectedFit).get(selectedSize);
            toleranceLabel.setText("Tolerance: " + tolerances[0] + " / " + tolerances[1]);
        } else {
            toleranceLabel.setText("Tolerance: N/A");
        }
    }

    private Map<String, Map<String, String[]>> readCSV(String filePath) {
        Map<String, Map<String, String[]>> toleranceMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(","); // Read the header line
            for (int i = 1; i < headers.length; i++) {
                toleranceMap.put(headers[i], new HashMap<>());
            }
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String size = data[0];
                for (int i = 1; i < data.length; i++) {
                    String[] tolerances = data[i].split("/");
                    toleranceMap.get(headers[i]).put(size, tolerances);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toleranceMap;
    }
}