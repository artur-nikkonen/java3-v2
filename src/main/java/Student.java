import java.util.ArrayList;
import java.util.List;

public class Student implements IStudent {

    private String name;
    private List<ICourse> courses;

    public Student(String name, List<ICourse> courses) {
        this.name = name;
        this.courses = courses;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ICourse> getAllCourses() {

        return courses;
    }
}
