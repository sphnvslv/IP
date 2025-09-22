package Lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class StringProcessorTest {

    @Test
    public void testFindShortestSubstring() {
        String result1 = StringProcessor.findShortestSubstring("hello", "eo");
        assertEquals("e", result1);

        String result2 = StringProcessor.findShortestSubstring("world", "od");
        assertEquals("o", result2);

        String result3 = StringProcessor.findShortestSubstring("test", "ygv");
        assertNull(result3);

        String result4 = StringProcessor.findShortestSubstring("", "a");
        assertNull(result4);
    }

    @Test
    public void testFormatResults() {
        List<String> tokens = Arrays.asList("12.5", "abc", "23-45");
        List<Double> numbers = Arrays.asList(12.5, 23.0);
        List<String> timeTokens = Arrays.asList("23-45", "23-65");
        String finalString = "результат";
        String searchChars = "ea";
        String originalString = "исходная";

        String result = StringProcessor.formatResults(
                tokens, numbers, timeTokens, finalString, searchChars, originalString
        );

        assertTrue(result.contains("исходная"));
        assertTrue(result.contains("ea"));
        assertTrue(result.contains("12.5"));
        assertTrue(result.contains("23-45"));
        assertTrue(result.contains("результат"));
    }

    @Test
    public void testFormatResultsWithNull() {
        String result = StringProcessor.formatResults(
                null, null, null, null, null, null
        );

        assertNotNull(result);
        assertTrue(result.contains("null"));
    }
}