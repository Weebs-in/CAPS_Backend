package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.course.courseId=:courseId")
    List<CourseStudent> getCourseStudentsByCourseId(Long courseId);

    @Query("SELECT cs FROM CourseStudent cs WHERE cs.course.courseId = :courseId")
    List<CourseStudent> getStudentByCid(Long courseId);
}
