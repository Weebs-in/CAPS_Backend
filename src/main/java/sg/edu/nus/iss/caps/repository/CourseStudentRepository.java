package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.CourseStudent;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 18:09 2023/6/16
 * @Modified by:
 */
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.course.courseId = :courseId AND cs.student.studentId = :studentId")
    List<CourseStudent> getCourseByCidAndSid(Long courseId, Long studentId);

    @Query("SELECT c.courseId, c.courseName, cs.courseStudentGrade, cs.courseStudentStatus " +
            "FROM CourseStudent cs " +
            "JOIN cs.course c " +
            "WHERE cs.student.studentId = :studentId")
    List<Object[]> findCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.course.courseId=:courseId")
    List<CourseStudent> getCourseStudentsByCourseId(Long courseId);

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.student.studentId = :studentId AND cs.courseStudentStatus =:status")
    List<CourseStudent> getCourseBySidAndStatus(Long studentId, int status);

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.student.studentId = :studentId AND (cs.course.courseStatus = :enrolledStatus OR cs.course.courseStatus = :completedStatus)")
    List<CourseStudent> getCourseBySidAndECStatus(@Param("studentId") Long studentId, @Param("enrolledStatus") int enrolledStatus, @Param("completedStatus") int completedStatus);

    @Query("SELECT c.courseId, s.studentId, c.courseName, s.firstMidName, s.lastName, cs.courseStudentStatus" +
            " FROM CourseStudent cs " +
            "JOIN cs.course c ON c.courseId = cs.course.courseId " +
            "JOIN cs.student s ON s.studentId = cs.student.studentId " +
            "WHERE (cs.courseStudentStatus = 0 OR cs.courseStudentStatus = 1)")
    List<Object[]> getAllStudentOngoingCourseList();
}
