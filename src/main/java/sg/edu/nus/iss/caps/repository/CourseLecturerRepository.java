package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.CourseLecturer;

import java.util.List;

public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, Long> {

    @Query("SELECT cl FROM CourseLecturer cl WHERE cl.course.courseId = :courseId AND cl.lecturer.lecturerId = :lecturerId")
    List<CourseLecturer> getCourseByCidAndLid(Long courseId, Long lecturerId);

    @Query("SELECT cl from CourseLecturer cl where cl.lecturer.lecturerId = :lecturerId")
    List<CourseLecturer> getCourseLecturersByLecturerId(Long lecturerId);
}
