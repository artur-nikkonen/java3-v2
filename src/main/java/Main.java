import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

public class Main {

    public static void main(String[] args) {
        Person[] people = new Person[]{
                new Person("Ivan", 10),
                new Person("Petr", 8),
                new Person("Sasha", 12),
        };

        Stream<Person> stream = Arrays.stream(people);

        //РЕШЕНИЕ: https://stackoverflow.com/a/24437207

        //Работает
        Stream<Person> sorted1 = stream.sorted(Comparator.comparing(x -> x.getName()));
        //Работает!
        Stream<Person> sorted2 = stream.sorted(Comparator.comparing(Person::getName).reversed().thenComparing(Person::getAge));
        //Работает
        Stream<Person> sorted4 = stream.sorted(Comparator.comparing((Person x) -> x.getName()).reversed().thenComparing(Person::getAge));
        //Снова работает!
        Stream<Person> sorted5 = stream.sorted(Comparator.comparing(Person::getName).reversed().thenComparing(x -> x.getAge()));
        //Не работает
        Stream<Person> sorted3 = stream.sorted(Comparator.comparing(x -> x.getName()).reversed().thenComparing(Person::getAge));
    }
}
