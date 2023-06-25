package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.Course;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:36 2023/6/16
 * @Modified by:
 */

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.faculty.facultyId = :facultyId")
    List<Course> getCoursesByFacultyId(Long facultyId);

    @Query("SELECT c.courseVacancy FROM Course c WHERE c.courseId = :courseId")
    int getCourseVacancyById(@Param("courseId") Long courseId);

    @Query("SELECT c.courseCapacity FROM Course c WHERE c.courseId = :courseId")
    int getCourseCapacityById(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c " +
            "INNER JOIN CourseLecturer cl " +
            "ON c.courseId = cl.course.courseId " +
            "WHERE cl.lecturer.lecturerId = :lecturerId " +
            "AND cl.courseLecturerStatus = 0")
    List<Course> getCourseByLecturerId(@Param("lecturerId") Long lecturerId);

    @Query("SELECT s.studentId, c.courseName, s.lastName, s.firstMidName, s.gender, s.gpa, cs.courseStudentGrade, cs.courseStudentStatus FROM CourseStudent cs " +
            "JOIN cs.student s " +
            "JOIN cs.course c " +
            "WHERE c.courseId = :courseId")
    List<Object[]> findStudentsByCourseId(@Param("courseId") Long courseId);
}
