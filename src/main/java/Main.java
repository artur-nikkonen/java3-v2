import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        //создаем тестовых студентов
        List<IStudent> testStudents = createTestStudents();

        //Получаем список курсов
        List<String> uniqueCourses = getUniqueCourses(testStudents);
        System.out.println("Список куров:");
        uniqueCourses.forEach(System.out::println);
        System.out.println();

        //Получаем любознательных студентов
        List<IStudent> mostCuriousStudents = getMostCuriousStudents(testStudents, 3);
        System.out.println("Список любознательных студентов:");
        mostCuriousStudents.forEach(student -> System.out.println(student.getName() + " [" + getCoursesDescription(student) + "]"));
        System.out.println();

        //Получаем список студентов, посещающих курс
        String courseName = "Course 08";
        System.out.println("Список студентов курса '" + courseName + "':");
        List<IStudent> studentsOfCourse = getStudentsOfCourse(testStudents, courseName);
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

    public static List<String> getUniqueCourses(List<IStudent> students) {

        return students.stream()
                .filter(Objects::nonNull)
                .filter(student -> student.getAllCourses() != null)
                .flatMap(student -> student.getAllCourses().stream())
                .filter(Objects::nonNull)
                .map(ICourse::getName)
                .distinct()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    static List<IStudent> getMostCuriousStudents(List<IStudent> students, int count) {
        return students.stream()
                .filter(Objects::nonNull)
                //сортируем по количеству непустых курсов, а потом по имени
                .sorted(Comparator.comparing((IStudent student) -> {
                            List<ICourse> allCourses = student.getAllCourses();
                            if (allCourses == null) return 0;
                            return (int) allCourses.stream().filter(Objects::nonNull).count();
                        }
                ).reversed().thenComparing(IStudent::getName))
                .limit(count)
                .collect(Collectors.toList());
    }

    private static List<IStudent> getStudentsOfCourse(List<IStudent> students, String courseName) {
        return students.stream()
                .filter(Objects::nonNull)
                .filter(student -> student.getAllCourses() != null)
                .filter(student -> student.getAllCourses().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(course -> course.getName().equals(courseName)))
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
