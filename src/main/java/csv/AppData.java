package csv;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AppData {
    private String[] header;
    private int[][] data;

    public AppData(String[] header, int[][] data) {
        this.header = header;
        this.data = data;
    }

    public String toCsvString() {

        if (header == null) return "";

        String headerString = String.join(",", header);

        if (data == null) return headerString;

        String dataString = Arrays.stream(data)
                .map(line -> Arrays.stream(line)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));

        return headerString + "\n" + dataString;
    }
}
