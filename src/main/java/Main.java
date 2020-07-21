import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 3, 7);
        Test1(integerStream);
    }

    public static List<Integer> Test1(Stream<Integer> stream) {

        Comparator<Integer> comparator1 = Comparator.comparing(x -> x);
        comparator1 = comparator1.reversed();
        comparator1 = comparator1.thenComparing(x -> x);

        return stream.sorted(comparator1).collect(Collectors.toList());
    }

    public static List<Integer> Test2(Stream<Integer> stream) {
        //не понимаю, как скомбинировать компаратор
        //компилятор ругается на типы, но я не понимаю, как это исправить
        Comparator<Integer> comparator1 = Comparator.comparing(x -> x).reversed().thenComparing(x -> x);

        return stream.sorted(comparator1).collect(Collectors.toList());
    }
}
