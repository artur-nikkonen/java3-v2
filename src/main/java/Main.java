import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Test();
        List<IStudent> testStudents = createTestStudents();

        //Получаем список курсов
        List<ICourse> uniqueCourses = getUniqueCourses(testStudents);
        uniqueCourses.forEach(x -> System.out.println(x.getName()));
        System.out.println();

        //Получаем любознательных студентов
        List<IStudent> mostCuriousStudents = get3MostCuriousStudents(testStudents);
        mostCuriousStudents.forEach(student -> System.out.println(student.getName() +
                " [" + getCoursesDescription(student) + "]"));
        System.out.println();

        //Получаем список студентов, посещающих курс
        ICourse course = new Course("Course 08");
        List<IStudent> studentsOfCourse = getStudentsOfCourse(testStudents, course);
        studentsOfCourse.forEach(student -> System.out.println(student.getName() +
                " [courses: " + getCoursesNamesString(student) + "]"));
    }

    //Генерирует список студентов
    static List<IStudent> createTestStudents() {
        //Создаем 100 студентов с разным набором курсов
        //У некоторых студентов список курсов пустой
        //Некоторые студеты имеют в списке курсов null
        List<IStudent> students = IntStream.range(0, 100)
                .mapToObj(i -> new Student("Student " + i,
                        IntStream.range(i % 2, i % 13)
                                .mapToObj(j -> j == 0 ? null : new Course("Course " + String.format("%02d", j)))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        //добавляем самого ленивого студента с courses = null
        students.add(new Student("Student Null", null));

        //добавляем  студента - призрака))
        students.add(null);

        return students;
    }

    public static List<ICourse> getUniqueCourses(List<IStudent> students) {

        return students.stream()
                .filter(Objects::nonNull)
                .filter(student -> student.getAllCourses() != null)
                .flatMap(student -> student.getAllCourses().stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ICourse::getName, course -> course, (course1, course2) -> course1))
                .values().stream()
                .sorted(Comparator.comparing(ICourse::getName))
                .collect(Collectors.toList());
    }

    static List<IStudent> get3MostCuriousStudents(List<IStudent> students) {


        //сравннение студетнов по количеству not null курсов, по убыванию
        Comparator<IStudent> comparator = Comparator.comparingInt(student -> {
            if (student.getAllCourses() == null) return -1;
            return (int)student.getAllCourses().stream().filter(Objects::nonNull).count();
        }).reversed();

        //затем, по имени
        comparator = comparator.thenComparing(IStudent::getName);

        return students.stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .limit(3)
                .collect(Collectors.toList());
    }

    private static List<IStudent> getStudentsOfCourse(List<IStudent> students, ICourse course) {
        return students.stream()
                .filter(Objects::nonNull)
                .filter(student -> student.getAllCourses() != null)
                .filter(student -> student.getAllCourses().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(c -> c.getName().equals(course.getName())))
                .collect(Collectors.toList());

    }

    //Вспомогательная функция. Возвращает статистику по курсам студента
    static String getCoursesDescription(IStudent student) {
        List<ICourse> courses = student.getAllCourses();
        if (courses == null) return "Courses: null";

        int totalCount = courses.size();
        long notNullCount = courses.stream().filter(Objects::nonNull).count();
        return "Not null courses count: " + notNullCount + "; Total courses count: " + totalCount;
    }

    //Вспомогательная функция. Возпращает список курсов студента
    static String getCoursesNamesString(IStudent student) {
        return student.getAllCourses().stream()
                .filter(Objects::nonNull)
                .map(ICourse::getName)
                .collect(Collectors.joining(", "));
    }




}
