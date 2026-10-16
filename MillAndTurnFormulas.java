public class MillAndTurnFormulas {

    // Milling Formulas
    public static double calculateCuttingSpeed(double diameter, double rpm, boolean isMetric) {
        return (Math.PI * diameter * rpm) / (isMetric ? 1000 : 12);
    }

    public static double calculateFeedRate(double rpm, double chipLoad, int numTeeth, boolean isMetric) {
        double feedRate = rpm * chipLoad * numTeeth;
        return isMetric ? feedRate * 25.4 : feedRate; // Convert to mm/min if metric
    }

    public static double calculateMaterialRemovalRate(double cuttingSpeed, double feedRate, double depthOfCut) {
        return cuttingSpeed * feedRate * depthOfCut;
    }

    public static double calculatePowerConsumption(double mrr, double specificCuttingForce) {
        return (mrr * specificCuttingForce) / (60 * 1000); // Convert to kW
    }

    public static double calculateSurfaceRoughness(double feedRate, double noseRadius) {
        return (feedRate * feedRate) / (8 * noseRadius);
    }

    // Turning Formulas
    public static double calculateTurningCuttingSpeed(double diameter, double rpm, boolean isMetric) {
        return (Math.PI * diameter * rpm) / (isMetric ? 1000 : 12);
    }

    public static double calculateTurningFeedRate(double rpm, double feedPerRev) {
        return rpm * feedPerRev;
    }

    public static double calculateTurningDepthOfCut(double initialDiameter, double finalDiameter) {
        return (initialDiameter - finalDiameter) / 2;
    }

    public static double calculateTurningMachiningTime(double lengthOfCut, double feedRate, double rpm) {
        return lengthOfCut / (feedRate * rpm);
    }

    public static double calculateTurningMaterialRemovalRate(double cuttingSpeed, double feedRate, double depthOfCut) {
        return cuttingSpeed * feedRate * depthOfCut;
    }

    public static double calculateTurningPowerConsumption(double mrr, double specificCuttingForce) {
        return (mrr * specificCuttingForce) / (60 * 1000); // Convert to kW
    }

    public static double calculateTurningSurfaceRoughness(double feedRate, double noseRadius) {
        return (feedRate * feedRate) / (8 * noseRadius);
    }

    // Conversion Formulas
    public static double sfmToRpm(double sfm, double diameter) {
        return (sfm * 12) / (Math.PI * diameter);
    }

    public static double iprToIpm(double ipr, double rpm) {
        return ipr * rpm;
    }

    public static double ipmToIpr(double ipm, double rpm) {
        return ipm / rpm;
    }
}