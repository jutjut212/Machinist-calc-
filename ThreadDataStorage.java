import java.util.HashMap;
import java.util.Map;

public class ThreadDataStorage {
    private static final Map<String, Double> majorDiameterMap = new HashMap<>();
    private static final Map<String, Double> tpiMap = new HashMap<>();
    private static final Map<String, Double> pitchMap = new HashMap<>();

    static {
        // Populate the maps with UNF data
        majorDiameterMap.put("#0-80 UNF", 0.060);
        majorDiameterMap.put("#1-72 UNF", 0.073);
        majorDiameterMap.put("#2-64 UNF", 0.086);
        majorDiameterMap.put("#3-56 UNF", 0.099);
        majorDiameterMap.put("#4-48 UNF", 0.112);
        majorDiameterMap.put("#5-44 UNF", 0.125);
        majorDiameterMap.put("#6-40 UNF", 0.138);
        majorDiameterMap.put("#8-36 UNF", 0.164);
        majorDiameterMap.put("#10-32 UNF", 0.190);
        majorDiameterMap.put("#12-28 UNF", 0.216);
        majorDiameterMap.put("1/4-28 UNF", 0.250);
        majorDiameterMap.put("5/16-24 UNF", 0.313);
        majorDiameterMap.put("3/8-24 UNF", 0.375);
        majorDiameterMap.put("7/16-20 UNF", 0.438);
        majorDiameterMap.put("1/2-20 UNF", 0.500);
        majorDiameterMap.put("9/16-18 UNF", 0.563);
        majorDiameterMap.put("5/8-18 UNF", 0.625);
        majorDiameterMap.put("3/4-16 UNF", 0.750);
        majorDiameterMap.put("7/8-14 UNF", 0.875);
        majorDiameterMap.put("1-12 UNF", 1.000);
        majorDiameterMap.put("1 1/8-12 UNF", 1.125);
        majorDiameterMap.put("1 1/4-12 UNF", 1.250);
        majorDiameterMap.put("1 3/8-12 UNF", 1.375);
        majorDiameterMap.put("1 1/2-12 UNF", 1.500);

        tpiMap.put("#0-80 UNF", 80.0);
        tpiMap.put("#1-72 UNF", 72.0);
        tpiMap.put("#2-64 UNF", 64.0);
        tpiMap.put("#3-56 UNF", 56.0);
        tpiMap.put("#4-48 UNF", 48.0);
        tpiMap.put("#5-44 UNF", 44.0);
        tpiMap.put("#6-40 UNF", 40.0);
        tpiMap.put("#8-36 UNF", 36.0);
        tpiMap.put("#10-32 UNF", 32.0);
        tpiMap.put("#12-28 UNF", 28.0);
        tpiMap.put("1/4-28 UNF", 28.0);
        tpiMap.put("5/16-24 UNF", 24.0);
        tpiMap.put("3/8-24 UNF", 24.0);
        tpiMap.put("7/16-20 UNF", 20.0);
        tpiMap.put("1/2-20 UNF", 20.0);
        tpiMap.put("9/16-18 UNF", 18.0);
        tpiMap.put("5/8-18 UNF", 18.0);
        tpiMap.put("3/4-16 UNF", 16.0);
        tpiMap.put("7/8-14 UNF", 14.0);
        tpiMap.put("1-12 UNF", 12.0);
        tpiMap.put("1 1/8-12 UNF", 12.0);
        tpiMap.put("1 1/4-12 UNF", 12.0);
        tpiMap.put("1 3/8-12 UNF", 12.0);
        tpiMap.put("1 1/2-12 UNF", 12.0);

        // Populate the maps with UNC data
        majorDiameterMap.put("#0-80 UNC", 0.060);
        majorDiameterMap.put("#1-64 UNC", 0.073);
        majorDiameterMap.put("#2-56 UNC", 0.086);
        majorDiameterMap.put("#3-48 UNC", 0.099);
        majorDiameterMap.put("#4-40 UNC", 0.112);
        majorDiameterMap.put("#5-40 UNC", 0.125);
        majorDiameterMap.put("#6-32 UNC", 0.138);
        majorDiameterMap.put("#8-32 UNC", 0.164);
        majorDiameterMap.put("#10-24 UNC", 0.190);
        majorDiameterMap.put("#12-24 UNC", 0.216);
        majorDiameterMap.put("1/4-20 UNC", 0.250);
        majorDiameterMap.put("5/16-18 UNC", 0.313);
        majorDiameterMap.put("3/8-16 UNC", 0.375);
        majorDiameterMap.put("7/16-14 UNC", 0.438);
        majorDiameterMap.put("1/2-13 UNC", 0.500);
        majorDiameterMap.put("9/16-12 UNC", 0.563);
        majorDiameterMap.put("5/8-11 UNC", 0.625);
        majorDiameterMap.put("3/4-10 UNC", 0.750);
        majorDiameterMap.put("7/8-9 UNC", 0.875);
        majorDiameterMap.put("1-8 UNC", 1.000);
        majorDiameterMap.put("1 1/8-7 UNC", 1.125);
        majorDiameterMap.put("1 1/4-7 UNC", 1.250);
        majorDiameterMap.put("1 3/8-6 UNC", 1.375);
        majorDiameterMap.put("1 1/2-6 UNC", 1.500);
        majorDiameterMap.put("1 3/4-5 UNC", 1.750);
        majorDiameterMap.put("2-4.5 UNC", 2.000);
        majorDiameterMap.put("2 1/4-4.5 UNC", 2.250);
        majorDiameterMap.put("2 1/2-4 UNC", 2.500);
        majorDiameterMap.put("2 3/4-4 UNC", 2.750);
        majorDiameterMap.put("3-4 UNC", 3.000);
        majorDiameterMap.put("3 1/4-4 UNC", 3.250);
        majorDiameterMap.put("3 1/2-4 UNC", 3.500);
        majorDiameterMap.put("3 3/4-4 UNC", 3.750);
        majorDiameterMap.put("4-4 UNC", 4.000);

        tpiMap.put("#0-80 UNC", 80.0);
        tpiMap.put("#1-64 UNC", 64.0);
        tpiMap.put("#2-56 UNC", 56.0);
        tpiMap.put("#3-48 UNC", 48.0);
        tpiMap.put("#4-40 UNC", 40.0);
        tpiMap.put("#5-40 UNC", 40.0);
        tpiMap.put("#6-32 UNC", 32.0);
        tpiMap.put("#8-32 UNC", 32.0);
        tpiMap.put("#10-24 UNC", 24.0);
        tpiMap.put("#12-24 UNC", 24.0);
        tpiMap.put("1/4-20 UNC", 20.0);
        tpiMap.put("5/16-18 UNC", 18.0);
        tpiMap.put("3/8-16 UNC", 16.0);
        tpiMap.put("7/16-14 UNC", 14.0);
        tpiMap.put("1/2-13 UNC", 13.0);
        tpiMap.put("9/16-12 UNC", 12.0);
        tpiMap.put("5/8-11 UNC", 11.0);
        tpiMap.put("3/4-10 UNC", 10.0);
        tpiMap.put("7/8-9 UNC", 9.0);
        tpiMap.put("1-8 UNC", 8.0);
        tpiMap.put("1 1/8-7 UNC", 7.0);
        tpiMap.put("1 1/4-7 UNC", 7.0);
        tpiMap.put("1 3/8-6 UNC", 6.0);
        tpiMap.put("1 1/2-6 UNC", 6.0);
        tpiMap.put("1 3/4-5 UNC", 5.0);
        tpiMap.put("2-4.5 UNC", 4.5);
        tpiMap.put("2 1/4-4.5 UNC", 4.5);
        tpiMap.put("2 1/2-4 UNC", 4.0);
        tpiMap.put("2 3/4-4 UNC", 4.0);
        tpiMap.put("3-4 UNC", 4.0);
        tpiMap.put("3 1/4-4 UNC", 4.0);
        tpiMap.put("3 1/2-4 UNC", 4.0);
        tpiMap.put("3 3/4-4 UNC", 4.0);
        tpiMap.put("4-4 UNC", 4.0);

        // Populate the maps with Metric data
        String[] metricThreadSizes = {
            "M1 x 0.2", "M1 x 0.25", "M1.2 x 0.2", "M1.2 x 0.25", "M1.4 x 0.2", "M1.4 x 0.3", "M1.6 x 0.2", "M1.6 x 0.35", "M1.8 x 0.2", "M1.8 x 0.35",
            "M2 x 0.25", "M2 x 0.4", "M2.2 x 0.25", "M2.2 x 0.45", "M2.5 x 0.35", "M2.5 x 0.45", "M3 x 0.35", "M3 x 0.5", "M3.5 x 0.35", "M3.5 x 0.6",
            "M4 x 0.5", "M4 x 0.7", "M5 x 0.5", "M5 x 0.8", "M6 x 0.75", "M6 x 1.0", "M7 x 0.75", "M7 x 1.0", "M8 x 1.0", "M8 x 1.25", "M10 x 1.25", "M10 x 1.5",
            "M12 x 1.5", "M12 x 1.75", "M14 x 1.5", "M14 x 2.0", "M16 x 1.5", "M16 x 2.0", "M18 x 1.5", "M18 x 2.5", "M20 x 1.5", "M20 x 2.5", "M22 x 1.5", "M22 x 2.5",
            "M24 x 2.0", "M24 x 3.0", "M27 x 2.0", "M27 x 3.0", "M30 x 2.0", "M30 x 3.5", "M33 x 2.0", "M33 x 3.5", "M36 x 2.0", "M36 x 4.0", "M39 x 2.0", "M39 x 4.0",
            "M42 x 2.0", "M42 x 4.5", "M45 x 2.0", "M45 x 4.5", "M48 x 2.0", "M48 x 5.0", "M52 x 2.0", "M52 x 5.0", "M56 x 2.0", "M56 x 5.5", "M60 x 2.0", "M60 x 5.5",
            "M64 x 2.0", "M64 x 6.0", "M68 x 2.0", "M68 x 6.0", "M72 x 2.0", "M72 x 6.0", "M76 x 2.0", "M76 x 6.0", "M80 x 2.0", "M80 x 6.0", "M85 x 2.0", "M85 x 6.0",
            "M90 x 2.0", "M90 x 6.0", "M95 x 2.0", "M95 x 6.0", "M100 x 2.0", "M100 x 6.0"
        };

        double[] metricMajorDiameters = {
            1.0, 1.0, 1.2, 1.2, 1.4, 1.4, 1.6, 1.6, 1.8, 1.8, 2.0, 2.0, 2.2, 2.2, 2.5, 2.5, 3.0, 3.0, 3.5, 3.5, 4.0, 4.0, 5.0, 5.0, 6.0, 6.0, 7.0, 7.0, 8.0, 8.0, 10.0, 10.0,
            12.0, 12.0, 14.0, 14.0, 16.0, 16.0, 18.0, 18.0, 20.0, 20.0, 22.0, 22.0, 24.0, 24.0, 27.0, 27.0, 30.0, 30.0, 33.0, 33.0, 36.0, 36.0, 39.0, 39.0, 42.0, 42.0, 45.0, 45.0,
            48.0, 48.0, 52.0, 52.0, 56.0, 56.0, 60.0, 60.0, 64.0, 64.0, 68.0, 68.0, 72.0, 72.0, 76.0, 76.0, 80.0, 80.0, 85.0, 85.0, 90.0, 90.0, 95.0, 95.0, 100.0, 100.0
        };

        double[] metricPitches = {
            0.2, 0.25, 0.2, 0.25, 0.2, 0.3, 0.2, 0.35, 0.2, 0.35, 0.25, 0.4, 0.25, 0.45, 0.35, 0.45, 0.35, 0.5, 0.35, 0.6, 0.5, 0.7, 0.5, 0.8, 0.75, 1.0, 0.75, 1.0, 1.0, 1.25, 1.25, 1.5,
            1.5, 1.75, 1.5, 2.0, 1.5, 2.0, 1.5, 2.5, 1.5, 2.5, 1.5, 2.5, 2.0, 3.0, 2.0, 3.0, 2.0, 3.5, 2.0, 3.5, 2.0, 4.0, 2.0, 4.0, 2.0, 4.5, 2.0, 4.5, 2.0, 4.5, 2.0, 5.0, 2.0, 5.0, 2.0, 5.5,
            2.0, 5.5, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0, 2.0, 6.0
        };

        for (int i = 0; i < metricThreadSizes.length; i++) {
            majorDiameterMap.put(metricThreadSizes[i], metricMajorDiameters[i]);
            pitchMap.put(metricThreadSizes[i], metricPitches[i]);
        }
    }

    public static Double getMajorDiameter(String threadSize) {
        return majorDiameterMap.get(threadSize);
    }

    public static Double getTPI(String threadSize) {
        return tpiMap.get(threadSize);
    }

    public static Double getPitch(String threadSize) {
        return pitchMap.get(threadSize);
    }
}
