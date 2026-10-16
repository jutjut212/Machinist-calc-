

import java.util.*;


    public class App {

        
        public static double feedRate(double rpm, double chipLoad, int numTeeth) {
            return rpm * chipLoad * numTeeth;
        }
    
        public static double sfm(double diameter, double rpm) {
            return (Math.PI * diameter * rpm) / 12;
        }
    
        public static double chipLoad(double feedRate, double rpm, int numTeeth) {
            return feedRate / (rpm * numTeeth);
        }
    
        public static double tapDrillSize(double threadSize, double threadPitch) {
            return threadSize - (1.0 / threadPitch);
        }
    
        public static double measurementOverWires(double threadDiameter, double pitch, double wireDiameter) {
            return threadDiameter + (3 * wireDiameter) - (0.866 * pitch);
        }
    
        public static double threadDepth(double threadDiameter, double pitch) {
            return 0.61343 * pitch;
        }
    
        public static double cuttingSpeed(double diameter, double rpm) {
            return (Math.PI * diameter * rpm) / 1000;  // in meters per minute
        }
    
        public static double machiningTime(double lengthOfCut, double feedRate) {
            return lengthOfCut / feedRate;  // in minutes
        }
    
        public static double materialRemovalRate(double widthOfCut, double depthOfCut, double feedRate) {
            return widthOfCut * depthOfCut * feedRate;  // in cubic inches per minute
        }
    
        public static double powerConsumption(double cuttingForce, double cuttingSpeed) {
            return cuttingForce * cuttingSpeed / 60;  // in kilowatts
        }
    
        public static double surfaceRoughness(double feedPerTooth, double noseRadius) {
            return (feedPerTooth * feedPerTooth) / (8 * noseRadius);  // in micrometers
        }
    
        public static double truePosition(double xDeviation, double yDeviation) {
            return Math.sqrt(xDeviation * xDeviation + yDeviation * yDeviation);  // in millimeters
        }
    
        // Thread data functions
        public static double[] uncThreadData(double diameter, double tpi) {
            double pitch = 1 / tpi;
            double majorDiameter = diameter;
            double minorDiameter = diameter - (1.08253 * pitch);
            double pitchDiameter = diameter - (0.64952 * pitch);
            return new double[]{majorDiameter, minorDiameter, pitchDiameter};
        }
    
        public static double[] metricThreadData(double diameter, double pitch) {
            double majorDiameter = diameter;
            double minorDiameter = diameter - (1.22687 * pitch);
            double pitchDiameter = diameter - (0.64952 * pitch);
            return new double[]{majorDiameter, minorDiameter, pitchDiameter};
        }
    
        public static double[] acmeThreadData(double diameter, double pitch) {
            double majorDiameter = diameter;
            double minorDiameter = diameter - (1.3 * pitch);
            double pitchDiameter = diameter - (0.5 * pitch);
            return new double[]{majorDiameter, minorDiameter, pitchDiameter};
        }
    
        public static double[] buttressThreadData(double diameter, double pitch) {
            double majorDiameter = diameter;
            double minorDiameter = diameter - (0.6 * pitch);
            double pitchDiameter = diameter - (0.3 * pitch);
            return new double[]{majorDiameter, minorDiameter, pitchDiameter};
        }
    
        // Basic math operations
        public static double add(double a, double b) {
            return a + b;
        }
    
        public static double subtract(double a, double b) {
            return a - b;
        }
    
        public static double multiply(double a, double b) {
            return a * b;
        }
    
        public static String divide(double a, double b) {
            if (b != 0) {
                return String.valueOf(a / b);
            } else {
                return "Error: Division by zero";
            }
        }
    
        // Unit conversion
        public static double convertUnits(double value, String fromUnit, String toUnit) {
            Map<String, Double> conversions = new HashMap<>();
            conversions.put("mm_in", value / 25.4);
            conversions.put("in_mm", value * 25.4);
            conversions.put("mm_cm", value / 10);
            conversions.put("cm_mm", value * 10);
            conversions.put("in_ft", value / 12);
            conversions.put("ft_in", value * 12);
            return conversions.getOrDefault(fromUnit + "_" + toUnit, value);
        }
    
        // Additional features
        public static double knurling(double diameter, double tpi) {
            return diameter / tpi;
        }
    
        public static List<Double> gaugeBlockStack(double targetHeight, List<Double> availableBlocks) {
            List<Double> stack = new ArrayList<>();
            double remainingHeight = targetHeight;
            Collections.sort(availableBlocks, Collections.reverseOrder());
            for (double block : availableBlocks) {
                if (block <= remainingHeight) {
                    stack.add(block);
                    remainingHeight -= block;
                }
            }
            return stack;
        }
    
        public static double taperLargeDiameter(double smallDiameter, double length, double taperPerInch) {
            return smallDiameter + (taperPerInch * length);
        }
    
        public static List<double[]> boltCircle(double diameter, int numHoles, double startAngle) {
            List<double[]> coordinates = new ArrayList<>();
            double angleIncrement = 360.0 / numHoles;
            for (int i = 0; i < numHoles; i++) {
                double angle = Math.toRadians(startAngle + i * angleIncrement);
                double x = (diameter / 2) * Math.cos(angle);
                double y = (diameter / 2) * Math.sin(angle);
                coordinates.add(new double[]{x, y});
            }
            return coordinates;
        }
    
        public static String threadMillGcode(double diameter, double pitch, double depth, double rpm, double feedRate) {
            StringBuilder gcode = new StringBuilder();
            gcode.append("G90 G54 G17 G21\n");
            gcode.append("S").append((int) rpm).append(" M3\n");
            gcode.append("G0 X0 Y0 Z5\n");
            gcode.append("G1 Z-").append(depth).append(" F").append((int) feedRate).append("\n");
            gcode.append("G2 I").append(diameter / 2).append(" P").append(pitch).append(" F").append((int) feedRate).append("\n");
            gcode.append("G0 Z5\n");
            gcode.append("M30\n");
            return gcode.toString();
        }
    
        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                boolean running = true;

                while (running) {

                    System.out.println("\nMachinist Calculator");
                    System.out.println("1. Feed Rate");
                    System.out.println("2. SFM");
                    System.out.println("3. Chip Load");
                    System.out.println("4. Tap Drill Size");
                    System.out.println("5. Measurement Over Wires");
                    System.out.println("6. Thread Depth");
                    System.out.println("7. Cutting Speed");
                    System.out.println("8. Machining Time");
                    System.out.println("9. Material Removal Rate");
                    System.out.println("10. Power Consumption");
                    System.out.println("11. Surface Roughness");
                    System.out.println("12. True Position");
                    System.out.println("13. UNC Thread Data");
                    System.out.println("14. Metric Thread Data");
                    System.out.println("15. Acme Thread Data");
                    System.out.println("16. Buttress Thread Data");
                    System.out.println("17. Basic Math Operations");
                    System.out.println("18. Unit Conversion");
                    System.out.println("19. Knurling");
                    System.out.println("20. Gauge Block Stack");
                    System.out.println("21. Taper Large Diameter");
                    System.out.println("22. Bolt Circle");
                    System.out.println("23. Thread Mill G-Code");
                    System.out.println("0. Exit");
                    
                    System.out.print("Select an option: ");
                    String choice = scanner.nextLine();
                    
                    switch (choice) {
                        case "0" -> {
                            return;
                        }
                        case "1" -> {
                            System.out.print("Enter RPM: ");
                            double rpm = scanner.nextDouble();
                            System.out.print("Enter Chip Load (in/tooth): ");
                            double chipLoad = scanner.nextDouble();
                            System.out.print("Enter Number of Teeth: ");
                            int numTeeth = scanner.nextInt();
                            double resultFeedRate = feedRate(rpm, chipLoad, numTeeth);
                            System.out.println("Feed Rate: " + resultFeedRate + " in/min");
                        }
                        case "2" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter RPM: ");
                            double rpm = scanner.nextDouble();
                            double resultSfm = sfm(diameter, rpm);
                            System.out.println("SFM: " + resultSfm + " ft/min");
                        }
                            case "3" -> {
                                System.out.print("Enter Feed Rate (in/min): ");
                                double feedRateVal = scanner.nextDouble();
                                System.out.print("Enter RPM: ");
                            double rpm = scanner.nextDouble();
                            System.out.print("Enter Number of Teeth: ");
                            int numTeeth = scanner.nextInt();
                            double resultChipLoad = chipLoad(feedRateVal, rpm, numTeeth);
                            System.out.println("Chip Load: " + resultChipLoad + " in/tooth");
                        }
                        case "4" -> {
                            System.out.print("Enter Thread Size (in): ");
                            double threadSize = scanner.nextDouble();
                            System.out.print("Enter Thread Pitch (threads/in): ");
                            double threadPitch = scanner.nextDouble();
                            double resultTapDrillSize = tapDrillSize(threadSize, threadPitch);
                            System.out.println("Tap Drill Size: " + resultTapDrillSize + " in");
                        }
                        case "5" -> {
                            System.out.print("Enter Thread Diameter (in): ");
                            double threadDiameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (threads/in): ");
                            double pitch = scanner.nextDouble();
                            System.out.print("Enter Wire Diameter (in): ");
                            double wireDiameter = scanner.nextDouble();
                            double resultMeasurementOverWires = measurementOverWires(threadDiameter, pitch, wireDiameter);
                            System.out.println("Measurement Over Wires: " + resultMeasurementOverWires + " in");
                        }
                        case "6" -> {
                            System.out.print("Enter Thread Diameter (in): ");
                            double threadDiameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (threads/in): ");
                            double pitch = scanner.nextDouble();
                            double resultThreadDepth = threadDepth(threadDiameter, pitch);
                            System.out.println("Thread Depth: " + resultThreadDepth + " in");
                        }
                        case "7" -> {
                            System.out.print("Enter Diameter (mm): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter RPM: ");
                            double rpm = scanner.nextDouble();
                            double resultCuttingSpeed = cuttingSpeed(diameter, rpm);
                            System.out.println("Cutting Speed: " + resultCuttingSpeed + " m/min");
                        }
                        case "8" -> {
                            System.out.print("Enter Length of Cut (mm): ");
                            double lengthOfCut = scanner.nextDouble();
                            System.out.print("Enter Feed Rate (mm/min): ");
                            double feedRateVal = scanner.nextDouble();
                            double resultMachiningTime = machiningTime(lengthOfCut, feedRateVal);
                            System.out.println("Machining Time: " + resultMachiningTime + " min");
                        }
                        case "9" -> {
                            System.out.print("Enter Width of Cut (mm): ");
                            double widthOfCut = scanner.nextDouble();
                            System.out.print("Enter Depth of Cut (mm): ");
                            double depthOfCut = scanner.nextDouble();
                            System.out.print("Enter Feed Rate (mm/min): ");
                            double feedRateVal = scanner.nextDouble();
                            double resultMaterialRemovalRate = materialRemovalRate(widthOfCut, depthOfCut, feedRateVal);
                            System.out.println("Material Removal Rate: " + resultMaterialRemovalRate + " in^3/min");
                        }
                        case "10" -> {
                            System.out.print("Enter Cutting Force (N): ");
                            double cuttingForce = scanner.nextDouble();
                            System.out.print("Enter Cutting Speed (m/min): ");
                            double cuttingSpeedVal = scanner.nextDouble();
                            double resultPowerConsumption = powerConsumption(cuttingForce, cuttingSpeedVal);
                            System.out.println("Power Consumption: " + resultPowerConsumption + " kW");
                        }
                        case "11" -> {
                            System.out.print("Enter Feed Per Tooth (mm): ");
                            double feedPerTooth = scanner.nextDouble();
                            System.out.print("Enter Nose Radius (mm): ");
                            double noseRadius = scanner.nextDouble();
                            double resultSurfaceRoughness = surfaceRoughness(feedPerTooth, noseRadius);
                            System.out.println("Surface Roughness: " + resultSurfaceRoughness + " µm");
                        }
                        case "12" -> {
                            System.out.print("Enter X Deviation (mm): ");
                            double xDeviation = scanner.nextDouble();
                            System.out.print("Enter Y Deviation (mm): ");
                            double yDeviation = scanner.nextDouble();
                            double resultTruePosition = truePosition(xDeviation, yDeviation);
                            System.out.println("True Position: " + resultTruePosition + " mm");
                        }
                        case "13" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Threads Per Inch (TPI): ");
                            double tpi = scanner.nextDouble();
                            double[] uncData = uncThreadData(diameter, tpi);
                            System.out.println("UNC Thread Data:");
                            System.out.println("Major Diameter: " + uncData[0] + " in");
                            System.out.println("Minor Diameter: " + uncData[1] + " in");
                            System.out.println("Pitch Diameter: " + uncData[2] + " in");
                        }
                        case "14" -> {
                            System.out.print("Enter Diameter (mm): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (mm): ");
                            double pitch = scanner.nextDouble();
                            double[] metricData = metricThreadData(diameter, pitch);
                            System.out.println("Metric Thread Data:");
                            System.out.println("Major Diameter: " + metricData[0] + " mm");
                            System.out.println("Minor Diameter: " + metricData[1] + " mm");
                            System.out.println("Pitch Diameter: " + metricData[2] + " mm");
                        }
                        case "15" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (in): ");
                            double pitch = scanner.nextDouble();
                            double[] acmeData = acmeThreadData(diameter, pitch);
                            System.out.println("Acme Thread Data:");
                            System.out.println("Major Diameter: " + acmeData[0] + " in");
                            System.out.println("Minor Diameter: " + acmeData[1] + " in");
                            System.out.println("Pitch Diameter: " + acmeData[2] + " in");
                        }
                        case "16" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (in): ");
                            double pitch = scanner.nextDouble();
                            double[] buttressData = buttressThreadData(diameter, pitch);
                            System.out.println("Buttress Thread Data:");
                            System.out.println("Major Diameter: " + buttressData[0] + " in");
                            System.out.println("Minor Diameter: " + buttressData[1] + " in");
                            System.out.println("Pitch Diameter: " + buttressData[2] + " in");
                        }
                        case "17" -> {
                            System.out.println("Basic Math Operations");
                            System.out.println("a. Addition");
                            System.out.println("b. Subtraction");
                            System.out.println("c. Multiplication");
                            System.out.println("d. Division");
                            System.out.print("Select an operation: ");
                            String mathChoice = scanner.next();
                            System.out.print("Enter first number: ");
                            double a = scanner.nextDouble();
                            System.out.print("Enter second number: ");
                            double b = scanner.nextDouble();
                            switch (mathChoice) {
                                case "a" -> System.out.println("Result: " + add(a, b));
                                case "b" -> System.out.println("Result: " + subtract(a, b));
                                case "c" -> System.out.println("Result: " + multiply(a, b));
                                case "d" -> System.out.println("Result: " + divide(a, b));
                                default -> System.out.println("Invalid choice");
                            }
                        }
                        case "18" -> {
                            System.out.print("Enter value: ");
                            double value = scanner.nextDouble();
                            System.out.print("Enter from unit (mm, in, cm, ft): ");
                            String fromUnit = scanner.next();
                            System.out.print("Enter to unit (mm, in, cm, ft): ");
                            String toUnit = scanner.next();
                            double resultConvertedValue = convertUnits(value, fromUnit, toUnit);
                            System.out.println("Converted Value: " + resultConvertedValue);
                        }
                        case "19" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Threads Per Inch (TPI): ");
                            double tpi = scanner.nextDouble();
                            double resultKnurling = knurling(diameter, tpi);
                            System.out.println("Knurling: " + resultKnurling + " in");
                        }
                        case "20" -> {
                            System.out.print("Enter Target Height (mm): ");
                            double targetHeight = scanner.nextDouble();
                            System.out.print("Enter Available Blocks (comma separated, mm): ");
                            scanner.nextLine(); // consume newline
                            List<Double> availableBlocks = new ArrayList<>();
                            for (String block : scanner.nextLine().split(",")) {
                                availableBlocks.add(Double.valueOf(block.trim()));
                            }
                            List<Double> resultGaugeBlockStack = gaugeBlockStack(targetHeight, availableBlocks);
                            System.out.println("Gauge Block Stack: " + resultGaugeBlockStack);
                        }
                            case "21" -> {
                                System.out.print("Enter Small Diameter (in): ");
                                double smallDiameter = scanner.nextDouble();
                                System.out.print("Enter Length (in): ");
                                double length = scanner.nextDouble();
                                System.out.print("Enter Taper Per Inch: ");
                                double taperPerInch = scanner.nextDouble();
                                double resultTaperLargeDiameter = taperLargeDiameter(smallDiameter, length, taperPerInch);
                                System.out.println("Large Diameter: " + resultTaperLargeDiameter + " in");
                        }
                        case "22" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Number of Holes: ");
                            int numHoles = scanner.nextInt();
                            System.out.print("Enter Start Angle (degrees): ");
                            double startAngle = scanner.nextDouble();
                            List<double[]> resultBoltCircle = boltCircle(diameter, numHoles, startAngle);
                            System.out.println("Bolt Circle Coordinates: ");
                            for (double[] coordinate : resultBoltCircle) {
                                System.out.println("X: " + coordinate[0] + ", Y: " + coordinate[1]);
                            }
                        }
                        case "23" -> {
                            System.out.print("Enter Diameter (in): ");
                            double diameter = scanner.nextDouble();
                            System.out.print("Enter Pitch (in): ");
                            double pitch = scanner.nextDouble();
                            System.out.print("Enter Depth (in): ");
                            double depth = scanner.nextDouble();
                            System.out.print("Enter RPM: ");
                            double rpm = scanner.nextDouble();
                            System.out.print("Enter Feed Rate (in/min): ");
                            double feedRate = scanner.nextDouble();
                            String resultThreadMillGcode = threadMillGcode(diameter, pitch, depth, rpm, feedRate);
                            System.out.println("Thread Mill G-Code:\n" + resultThreadMillGcode);
                        }
                        default -> System.out.println("Invalid choice");
                        }
                    }
                    scanner.nextLine(); // consume newline
            } catch (NumberFormatException e) {
            }
            }
        
       
    }

    