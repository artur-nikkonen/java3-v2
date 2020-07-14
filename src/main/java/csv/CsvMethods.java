package csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class CsvMethods {

    public static AppData readCsvFile(String fileName) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            return TryParseLines(lines);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveToCsvFile(String fileName, AppData appData) {
        try {
            Files.write(Paths.get(fileName), Collections.singleton(appData.toCsvString()), StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppData TryParseLines(List<String> lines) {

        if (lines == null || lines.size() < 2) return null;

        String[] header = lines.get(0).trim().split("\\s*,\\s*");
        if (header.length == 0) return null; //bad header

        int[][] data = new int[lines.size() - 1][header.length];

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i).trim().split("\\s*,\\s*");
            if (line.length != header.length) return null; //bad data line

            for (int j = 0; j < line.length; j++) {
                try {
                    data[i - 1][j] = Integer.parseInt(line[j]);
                } catch (NumberFormatException e) { //bad numbers in data
                    return null;
                }
            }
        }

        return new AppData(header, data);
    }
}
