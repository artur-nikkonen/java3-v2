import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * https://www.pdfdrive.com/java-puzzlers-traps-pitfalls-and-corner-cases-e34353605.html
 * Java Puzzlers: Traps, Pitfalls, and Corner Cases
 * страница 153
 * Вопрос: как будет отсортирован массив?
 */
public class SortPuzzle {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] arr = new Integer[100];

        //Заполняем массив случайными значения
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt();
        }

        //Создаем компаратор
        Comparator<Integer> comparator = (i1, i2) -> i2 - i1;

        //Сортируем массив
        Arrays.sort(arr, comparator);

        //Проверяем порядок сортировки
        boolean asc = true;
        boolean desc = true;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] < arr[i]) desc = false;
            if (arr[i - 1] > arr[i]) asc = false;
        }

        System.out.println("Ascending: " + asc);
        System.out.println("Descending: " + desc);
    }

    /**
     * Проблема в том, что разница между элементами может превышать Integer.MAX_VALUE
     * Поэтому в компараторе надо возрващать не разницу между элементами, а -1, 0 или +1
     */


}
