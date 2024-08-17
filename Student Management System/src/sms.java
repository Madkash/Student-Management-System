import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class sms {
    private static List<Student> Students = new ArrayList<>();


    public static void addStudent(Student student1) {
        //String sql = "INSERT INTO students (id, name, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = sqlDatabase.getConnection()){
            String sql = "INSERT INTO students (id, name, age, grade) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql) ;

            pstmt.setString(1, student1.getId());
            pstmt.setString(2, student1.getName());
            pstmt.setInt(3, student1.getAge());
            pstmt.setString(4, student1.getGrade());

            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        Students.add(student1);
    }
    public static void updateStudentInformation(String id, String name, int age, String grade) {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE ID = ?";

        try (Connection conn = sqlDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, grade);
            pstmt.setString(4, id);

            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        for (Student student : Students) {
            if (student.getId().equals(id)) {
                student.setAge(age);
                student.setGrade(grade);
                student.setName(name);
                return;
            }
        }

    }

    public static int checkID(String id) {
        for (Student student : Students) {
            if (student.getId().equals(id)) {
                return 1;

            }
        }
        return 0;
    }
    public static void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = sqlDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

       // students.removeIf(student -> student.getId().equals(id));
    }
    public static Student viewStudentInformation(String id) {
        for (Student student: Students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    public static List<Student> listStudents() {
        List<Student> Students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = sqlDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String grade = rs.getString("grade");

                Students.add(new Student(id, name, age, grade));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Students;
    }
}
