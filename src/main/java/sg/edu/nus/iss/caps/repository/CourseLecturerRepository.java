package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.Course;
import sg.edu.nus.iss.caps.model.CourseLecturer;
import sg.edu.nus.iss.caps.model.Lecturer;

import java.util.List;

public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, Long> {

    @Query("SELECT cl FROM CourseLecturer cl WHERE cl.course.courseId = :courseId AND cl.lecturer.lecturerId = :lecturerId")
    List<CourseLecturer> getCourseByCidAndLid(Long courseId, Long lecturerId);

    @Query("SELECT cl from CourseLecturer cl where cl.lecturer.lecturerId = :lecturerId")
    List<CourseLecturer> getCourseLecturersByLecturerId(Long lecturerId);

    @Query("SELECT l " +
            "FROM CourseLecturer cl " +
            "JOIN Lecturer l " +
            "ON l.lecturerId = cl.lecturer.lecturerId " +
            "WHERE cl.course.courseId = :courseId")
    List<Lecturer> getLecturersByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c WHERE c.faculty.facultyId = " +
            "(SELECT l.faculty.facultyId FROM Lecturer l WHERE l.lecturerId = :lecturerId)" +
            " AND c.courseId NOT IN " +
            "(SELECT cl.course.courseId FROM CourseLecturer cl WHERE cl.lecturer.lecturerId = :lecturerId" +
            " AND cl.courseLecturerStatus = 0) " +
            "AND c.courseStatus <> 2")
    List<Course> getEnrollCoursesByLecturerId(@Param("lecturerId") Long lecturerId);
}
