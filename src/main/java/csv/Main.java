package csv;

import static csv.CsvMethods.*;

public class Main {
    public static void main(String[] args) {

        AppData appData = readFromCsvFile("files/file1.csv");
        System.out.println(appData.toCsvString());

        saveToCsvFile("files/file2.csv", appData);
    }
}
