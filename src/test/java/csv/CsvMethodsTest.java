package csv;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CsvMethodsTest {

    @Test
    void TryParseLinesNullInput() {
        AppData data = CsvMethods.TryParseLines(null);
        assertNull(data);
    }

    @Test
    void TryParseLinesEmptyInput() {
        List<String> lines = new ArrayList<>();
        AppData data = CsvMethods.TryParseLines(lines);
        assertNull(data);
    }

    @Test
    void TryParseLinesOneLine() {
        List<String> lines = new ArrayList<>();
        lines.add("q,w,e");
        AppData data = CsvMethods.TryParseLines(lines);
        assertNull(data);
    }

    @Test
    void TryParseLinesEmptyFirstLine() {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("1,2,3");
        AppData data = CsvMethods.TryParseLines(lines);
        assertNull(data);
    }

    @Test
    void TryParseLinesBadNumberOfColumns() {
        List<String> lines = new ArrayList<>();
        lines.add("q,w,e");
        lines.add("1,2,3");
        lines.add("1,2");
        AppData data = CsvMethods.TryParseLines(lines);
        assertNull(data);
    }

    @Test
    void TryParseLinesBadNumberFormat() {
        List<String> lines = new ArrayList<>();
        lines.add("q,w,e");
        lines.add("a,2,3");
        AppData data = CsvMethods.TryParseLines(lines);
        assertNull(data);
    }

    @Test
    void TryParseLinesCorrectLines() {
        List<String> lines = new ArrayList<>();
        lines.add("q,w,e");
        lines.add("1,2,3");
        lines.add("4,5,6");

        AppData data = CsvMethods.TryParseLines(lines);
        String csv = data.toCsvString();

        String expectedCsv = "q,w,e\n1,2,3\n4,5,6";
        assertEquals(expectedCsv, csv);
    }
}