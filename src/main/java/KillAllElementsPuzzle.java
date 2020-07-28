import java.util.*;

/**
 * https://habr.com/ru/company/jugru/blog/352438/
 * Java-паззлер №7: Неудержимые 2
 * Вопрос: что выведет функция removeAllElements?
 */
public class KillAllElementsPuzzle {
    static void removeAllElements(Collection<String> collection) {
        Iterator<String> iterator = collection.iterator();

        iterator.forEachRemaining(e -> {
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        });

        System.out.println(collection + " - " + collection.getClass());
    }

    //Тесты с разными коллекциями
    public static void main(String[] args) {
        removeAllElements(new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")));
        removeAllElements(new LinkedList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")));
        removeAllElements(new TreeSet<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")));
        removeAllElements(new ArrayDeque<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")));
    }

    /**
     * Объяснение:
     *
     * Всё это получилось случайно, потому что никто не думал, что так будут делать.
     * Проблему пофиксили дописав предупреждение в документации.
     * https://docs.oracle.com/javase/9/docs/api/java/lang/Iterable.html
     *
     * Написали:
     * default void forEach​(Consumer<? super T> action)
     * ...
     * The behavior of this method is unspecified if the action performs side-effects that modify the underlying
     * source of elements, unless an overriding class has specified a concurrent modification policy.
     *
     * Перевод:
     * Поведение этого метода не определено, если действие выполняет побочные эффекты, которые изменяют основной
     * источник элементов, если переопределяющий класс не указал политику одновременного изменения
     */
}
