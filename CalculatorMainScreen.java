
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import javax.swing.*;

public class CalculatorMainScreen {

    private static final Preferences prefs = Preferences.userRoot().node(Settings.class.getName());
    private static String currentUnit = prefs.get("defaultUnit", "Inches"); // Load default unit
    private static boolean darkMode = prefs.getBoolean("darkMode", false); // Load dark mode setting

    public static void open(Point location) {
        JFrame mainFrame = new JFrame("Machinist Calculator Main Screen");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 700); // Increase the size of the window
        mainFrame.setLocation(location);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainFrame.add(mainPanel);
        placeMainComponents(mainPanel, mainFrame);

        if (darkMode) {
            applyDarkMode(mainPanel, darkMode);
        }

        mainFrame.setVisible(true);
    }

    private static void placeMainComponents(JPanel panel, JFrame mainFrame) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        Dimension buttonSize = new Dimension(80, 80); // Standard button size

        JTextField display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        panel.add(display, gbc);

        // Add key binding for backspace key
        display.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "BACKSPACE");
        display.getActionMap().put("BACKSPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentText = display.getText();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        JButton unitButton = new JButton(currentUnit = prefs.get("defaultUnit", "mm"));
        unitButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(unitButton, gbc);

        unitButton.addActionListener((ActionEvent e) -> {
            try {
                double value = Double.parseDouble(display.getText());
                if (currentUnit.equals("mm")) {
                    value /= 25.4; // Convert mm to inches
                    currentUnit = "inches";
                    unitButton.setText("Unit: inches");
                } else {
                    value *= 25.4; // Convert inches to mm
                    currentUnit = "mm";
                    unitButton.setText("Unit: mm");
                }
                display.setText(String.valueOf(value));
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        });

        // Conversions button
        JButton conversionsButton = new JButton("Conversions");
        conversionsButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(conversionsButton, gbc);

        conversionsButton.addActionListener((ActionEvent e) -> {
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            Conversions.open(location);
        });

        // Mill Calc button
        JButton millCalcButton = new JButton("Mill Calc");
        millCalcButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(millCalcButton, gbc);

        millCalcButton.addActionListener((ActionEvent e) -> {
            // Open the Mill Calc screen in the same location and close the current screen
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            MillCalc.open(location);
        });

        // Thread Wires button
        JButton threadWiresButton = new JButton("Thread Wires");
        threadWiresButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(threadWiresButton, gbc);

        threadWiresButton.addActionListener((ActionEvent e) -> {
            // Open the Thread Wires screen in the same location and close the current screen
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            ThreadWires.open(location, mainFrame);
        });

        // Thread Data button
        JButton threadDataButton = new JButton("Thread Data");
        threadDataButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(threadDataButton, gbc);

        threadDataButton.addActionListener((var e) -> {
            // Open the Thread Data screen in the same location and close the current screen
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            ThreadData.open(location);
        });

        // Turning Calc button
        JButton turningCalcButton = new JButton("Turning Calc");
        turningCalcButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(turningCalcButton, gbc);

        turningCalcButton.addActionListener((ActionEvent e) -> {
            // Open the Turning Calc screen in the same location and close the current screen
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            TurningCalc.open(location);
        });

        // Add the Circle Calculator button
        JButton circleCalculatorButton = new JButton("Circle Calculator");
        circleCalculatorButton.setPreferredSize(new Dimension(125, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(circleCalculatorButton, gbc);

        circleCalculatorButton.addActionListener((ActionEvent e) -> {
            // Open the Circle Calculator screen in the same location and close the current screen
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            CircleCalculatorGUI.open(location);
        });

        // Trigonometric buttons
        JButton sinButton = new JButton("sin");
        sinButton.setPreferredSize(new Dimension(20, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(sinButton, gbc);
        sinButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + " sin ");
        });

        JButton cosButton = new JButton("cos");
        cosButton.setPreferredSize(new Dimension(20, 40));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(cosButton, gbc);
        cosButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + " cos ");
        });

        JButton tanButton = new JButton("tan");
        tanButton.setPreferredSize(new Dimension(20, 40));
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(tanButton, gbc);
        tanButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + " tan ");
        });

        JButton logButton = new JButton("log");
        logButton.setPreferredSize(new Dimension(20, 40));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(logButton, gbc);
        logButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + " log ");
        });

        JButton lnButton = new JButton("ln");
        lnButton.setPreferredSize(new Dimension(20, 40));
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(lnButton, gbc);
        lnButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + " ln ");
        });

        // Create buttons for digits 0-9
        int x = 0, y = 6;
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setPreferredSize(buttonSize);
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.gridwidth = 1;
            panel.add(button, gbc);
            button.addActionListener((ActionEvent e) -> {
                display.setText(display.getText() + button.getText());
            });
            x++;
            if (i % 3 == 0) {
                x = 0;
                y++;
            }
            String key = "NUMPAD" + i;
            panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD" + i), key);
            panel.getActionMap().put(key, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    display.setText(display.getText() + button.getText());
                }
            });
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setPreferredSize(buttonSize);
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(zeroButton, gbc);
        zeroButton.addActionListener((ActionEvent e) -> {
            display.setText(display.getText() + "0");
        });

        JButton decimalButton = new JButton(".");
        decimalButton.setPreferredSize(buttonSize);
        gbc.gridx = 2;
        gbc.gridy = 9;
        panel.add(decimalButton, gbc);
        decimalButton.addActionListener((ActionEvent e) -> {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        });

        // Create buttons for operations
        String[] operations = {"/", "*", "-", "+"};
        x = 3;
        y = 5;
        for (String op : operations) {
            JButton button = new JButton(op);
            button.setPreferredSize(buttonSize);
            gbc.gridx = x;
            gbc.gridy = y;
            panel.add(button, gbc);
            button.addActionListener((ActionEvent e) -> {
                display.setText(display.getText() + " " + button.getText() + " ");
            });
            y++;
            String key = "NUMPAD" + op;
            KeyStroke keyStroke = null;
            switch (op) {
                case "+" ->
                    keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0);
                case "-" ->
                    keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0);
                case "*" ->
                    keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0);
                case "/" ->
                    keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0);
            }
            if (keyStroke != null) {
                panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
                panel.getActionMap().put(key, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        display.setText(display.getText() + " " + button.getText() + " ");
                    }
                });
            }
        }

        JButton equalsButton = new JButton("=");
        equalsButton.setPreferredSize(buttonSize);
        gbc.gridx = 3;
        gbc.gridy = 9;
        panel.add(equalsButton, gbc);
        equalsButton.addActionListener((ActionEvent e) -> {
            try {
                String infix = display.getText();
                String postfix = CalculatorUtils.infixToPostfix(infix);
                double result = CalculatorUtils.evaluatePostfix(postfix);
                display.setText(String.valueOf(result));
            } catch (Exception ex) {
                display.setText("Error");
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "ENTER");
        panel.getActionMap().put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equalsButton.doClick();
            }
        });

        JButton clearButton = new JButton("C");
        clearButton.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(clearButton, gbc);
        clearButton.addActionListener((ActionEvent e) -> {
            display.setText("");
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLEAR");
        panel.getActionMap().put("CLEAR", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButton.doClick();
            }
        });

        JButton deleteButton = new JButton("←");
        deleteButton.setPreferredSize(buttonSize);
        gbc.gridx = 3;
        gbc.gridy = 4;
        panel.add(deleteButton, gbc);
        deleteButton.addActionListener((ActionEvent e) -> {
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        });

        // Handle backspace key press
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String currentText = display.getText();
                    if (!currentText.isEmpty()) {
                        display.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
            }
        });

        // Add key binding for number pad delete
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "DELETE");
        panel.getActionMap().put("DELETE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming deleteButton is defined elsewhere in your code
                // deleteButton.doClick();
            }
        });

        // Settings button
        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(62, 25)); // Half the size of the original button
        gbc.gridx = 3;
        gbc.gridy = 1; 
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Align to bottom right corner
        panel.add(settingsButton, gbc);
        settingsButton.addActionListener((ActionEvent e) -> {
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            Settings.open(location);
        });

        // Settings button
        JButton ISOHoleAndShaftTolerencesButton = new JButton("ISO FITS");
        ISOHoleAndShaftTolerencesButton.setPreferredSize(new Dimension(62, 25)); // Half the size of the original button
        gbc.gridx = 2;
        gbc.gridy = 1; 
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Align to bottom right corner
        panel.add(ISOHoleAndShaftTolerencesButton, gbc);
        ISOHoleAndShaftTolerencesButton.addActionListener((ActionEvent e) -> {
            Point location = mainFrame.getLocation();
            mainFrame.dispose();
            ISOHoleAndShaftTolerences.open(location);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> open(new Point(100, 100)));
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

    public static String getCurrentUnit() {
        return currentUnit;
    }

    public static void setCurrentUnit(String currentUnit) {
        CalculatorMainScreen.currentUnit = currentUnit;
    }

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void setDarkMode(boolean darkMode) {
        CalculatorMainScreen.darkMode = darkMode;
    }
}
