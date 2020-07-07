package generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListMethodsTest {

    @Test
    public void swapWithIntegersList() {

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(1, 5, 3, 4, 2));

        ListMethods.swap(list, 1, 4);

        assertArrayEquals(expectedList.toArray(), list.toArray());
    }

    @Test
    public void swapWithStringsList() {

        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        List<String> expectedList = new ArrayList<>(Arrays.asList("2", "1", "3", "4", "5"));

        ListMethods.swap(list, 0, 1);

        assertArrayEquals(expectedList.toArray(), list.toArray());
    }

    @Test
    public void swapNullListException() {
        assertThrows(IllegalArgumentException.class, () -> ListMethods.swap(null, 0, 1), "List is null");
    }

    @Test
    public void swapEmptyListException() {
        List<String> list = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> ListMethods.swap(list, 0, 1), "List is empty");
    }

    @Test
    public void swapBoundsException() {

        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

        //check i bounds
        assertThrows(IndexOutOfBoundsException.class, () -> ListMethods.swap(list, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> ListMethods.swap(list, 5, 1));

        //check j bounds
        assertThrows(IndexOutOfBoundsException.class, () -> ListMethods.swap(list, 0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> ListMethods.swap(list, 0, 5));
    }

    @Test
    public void arrayToListWithIntegers() {
        Integer[] array = {1, 2, 3};

        ArrayList<Integer> list = ListMethods.arrayToList(array);

        assertArrayEquals(array, list.toArray());
    }

    @Test
    public void arrayToListWithStrings() {
        String[] array = {"1", "2", "3"};

        ArrayList<String> list = ListMethods.arrayToList(array);

        assertArrayEquals(array, list.toArray());
    }

    @Test
    public void arrayToListWithNullArray() {
        ArrayList<String> list = ListMethods.arrayToList(null);
        assertNull(list);
    }

    @Test
    public void checkEmptyArrayToList() {
        ArrayList<String> list = ListMethods.arrayToList(new String[0]);
        assertTrue(list.isEmpty());
    }


}
