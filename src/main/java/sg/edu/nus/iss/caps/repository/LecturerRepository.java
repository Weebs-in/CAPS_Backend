package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.Lecturer;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT l FROM Lecturer l WHERE l.faculty.facultyId = :facultyId")
    List<Lecturer> getLecturersByFacultyId(Long facultyId);

    @Query("SELECT s.studentId, c.courseName, s.lastName, s.firstMidName, s.gender, s.gpa, cs.courseStudentGrade, cs.courseStudentStatus, c.courseId, c.courseCode, c.courseCredits " +
            "FROM CourseLecturer cl " +
            "JOIN Course c ON c.courseId = cl.course.courseId " +
            "JOIN CourseStudent cs ON cl.course.courseId = cs.course.courseId " +
            "JOIN Student s ON s.studentId = cs.student.studentId " +
            "WHERE cl.lecturer.lecturerId = :lecturerId " +
            "AND cl.courseLecturerStatus = 0")
    List<Object[]> getStudentPerformanceByLecturerId(@Param("lecturerId") Long lecturerId);

    @Query("SELECT l FROM Lecturer l WHERE l.matriculationNumber = :matriculationNumber")
    Lecturer getLecturerByMatriculationNumber(String matriculationNumber);
}
