import java.util.HashSet;
import java.util.Set;

/**
 * https://www.pdfdrive.com/java-puzzlers-traps-pitfalls-and-corner-cases-e34353605.html
 * Java Puzzlers: Traps, Pitfalls, and Corner Cases
 * страница 139
 * Вопрос: Сколько элементов будет в сете?
 */
public class DifferencesPuzzle {
    public static void main(String[] args) {
        //разница между всеми элементами = 111
        int[] vals = {789, 678, 567, 456, 345, 234, 123, 012};
        Set<Integer> diffs = new HashSet<Integer>();

        for (int i = 0; i < vals.length; i++) {
            for (int j = i; j < vals.length; j++) {
                diffs.add(vals[i] - vals[j]);
            }
        }

        System.out.println(diffs.size());

        //Выводим элементы сета
        //System.out.println(diffs.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    /**
     * В Идее не очень интересно, так как она выделяет проблему. (У меня, выделила)
     * Если написать число начинающееся с 0, то это будет восьмеричное число, а не десятичное.
     * Поэтому 012 = 10.
     * Поэтому в сете будет больше элементов, чем предполагается
     */
}
