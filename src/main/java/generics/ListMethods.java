package generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListMethods {

    public static <T> void swap(List<T> list, int i, int j) {

        if (list == null) {
            throw new IllegalArgumentException("List is null");
        }

        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }

        if (i < 0 || i >= list.size()) {
            String message = "Index i = " + i + " is out of list bounds [0;" + (list.size() - 1) + "]";
            throw new IndexOutOfBoundsException(message);
        }

        if (j < 0 || j >= list.size()) {
            String message = "Index j = " + j + " is out of list bounds [0;" + (list.size() - 1) + "]";
            throw new IndexOutOfBoundsException(message);
        }

        T element = list.get(i);
        list.set(i, list.get(j));
        list.set(j, element);
    }
    public static <T> ArrayList<T> arrayToList(T[] array){

        if(array == null) return null;

        ArrayList<T> list = new ArrayList<>();

        Collections.addAll(list, array);

        return list;
    }
}
