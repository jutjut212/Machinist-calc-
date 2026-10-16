public class ThreadDataFormulas {

    public static double calculatePitch(double tpi) {
        return 1.0 / tpi;
    }

    public static double calculateThreadDepth(double pitch) {
        return 0.866 * pitch;
    }

    public static double calculateMinorDiameterExternal(double majorDiameter, double pitch) {
        return majorDiameter - (2 * (0.625 * pitch));
    }

    public static double calculateMinorDiameterInternal(double majorDiameter, double threadDepth) {
        return majorDiameter - (2 * threadDepth);
    }

    public static double calculateLead(double pitch) {
        return pitch; // For single-start threads, lead is equal to pitch
    }

    public static double calculateTapDrillSize(double majorDiameter, double pitch) {
        return majorDiameter - pitch;
    }

    public static double calculateExternalMajorDiameterTolerance(double pitch) {
        return 0.060 * Math.pow(pitch, 2.0 / 3.0); // For Class 2A and 3A
    }

    public static double calculateExternalPitchDiameterTolerance(double majorDiameter, double pitch, double LE) {
        return 0.0015 * Math.pow(majorDiameter, 1.0 / 3.0) + 0.0015 * Math.sqrt(LE) + 0.015 * Math.pow(pitch, 2.0 / 3.0); // For Class 2A
    }

    public static double calculateExternalPitchDiameterTolerance3A(double majorDiameter, double pitch, double LE) {
        return 0.001 * Math.pow(majorDiameter, 1.0 / 3.0) + 0.001 * Math.sqrt(LE) + 0.01 * Math.pow(pitch, 2.0 / 3.0); // For Class 3A
    }

    public static double calculateInternalMinorDiameterTolerance(double pitch) {
        return 0.120 * Math.pow(pitch, 2.0 / 3.0); // For Class 2B and 3B
    }

    public static double calculateInternalPitchDiameterTolerance(double majorDiameter, double pitch, double LE) {
        return 0.0015 * Math.pow(majorDiameter, 1.0 / 3.0) + 0.0015 * Math.sqrt(LE) + 0.015 * Math.pow(pitch, 2.0 / 3.0); // For Class 2B
    }

    public static double calculateInternalPitchDiameterTolerance3B(double majorDiameter, double pitch, double LE) {
        return 0.001 * Math.pow(majorDiameter, 1.0 / 3.0) + 0.001 * Math.sqrt(LE) + 0.01 * Math.pow(pitch, 2.0 / 3.0); // For Class 3B
    }

    public static double calculateBestWireSize(double pitch, double angleInDegrees) {
        double angleInRadians = Math.toRadians(angleInDegrees / 2);
        return (pitch / 2) / Math.cos(angleInRadians);
    }

    public static double calculatePDUnifiedThread(double M, double P, double W) {
        // Unified (60°) Threads
        return M - 0.86603 * P + 3 * W;
    }

    public static double calculateMOWUnifiedThread(double D, double n, double W) {
        // Unified (60°) Threads
        return D - 1.51555/n + 3 * W;
    }  

    public static double calculatePDWhitworthThread(double M, double P, double W) {
        // Whitworth (55°) Threads
        return M - 0.9605 * P + 3.1657 * W;
    }

    public static double calculateMOWWhitworthThread(double D, double n, double W) {
        // Whitworth (55°) Threads
        return D - 1.60082/n + 3.16568 * W;
    }

    public static double calculatePDMetricThread(double M, double P, double W) {
        // Metric (60°) Threads
        return M - 0.86603 * P + 3 * W;
    }

    public static double calculateMOWMetricThread(double D, double P, double W) {
        // Metric (60°) Threads
        return D - 1.51555*P - 3 * W;
    }

    public static double calculatePDThread47_5Degree(double M, double P, double W) {
        // 47.5° Threads
        return M - 0.92388 * P + 3.111 * W;
    }

    public static double calculateMOWThread47_5Degree(double D, double P, double W) {
        // 47.5° Threads
        return D - 1.73634*P + 3.4829 * W;
    }

    public static double calculatePDThread45Degree(double M, double P, double W) {
        // 45° Threads
        return M - 0.90032 * P + 3.061 * W;
    }

    public static double calculateMOWThread45Degree(double E, double P, double W) {
        // 45° Threads
        return E + 0.90032 * P - 3.061 * W;
    }

    public static double calculatePDThread40Degree(double M, double P, double W) {
        // 40° Threads
        return M - 0.81915 * P + 2.828 * W;
    }

    public static double calculateMOWThread40Degree(double D, double n, double W) {
        // 40° Threads
        return D - 2.01034/n + 3.9238 * W;
    }

    public static double calculatePDThread29Degree(double M, double P, double W) {
        // 29° Threads
        return M - 0.63439 * P + 2.165 * W;
    }

    public static double calculateMOWThread29Degree(double D, double n) {
        // 29° Threads
        return D * (.5149/n);
    }

    public static double NomPD(double majorDiameter, double pitch, double threadAngle) {
        double angleFactor = Math.sqrt(3 * Math.tan(Math.toRadians(threadAngle / 2)));
        return majorDiameter - (angleFactor * pitch / 8);
    }
}