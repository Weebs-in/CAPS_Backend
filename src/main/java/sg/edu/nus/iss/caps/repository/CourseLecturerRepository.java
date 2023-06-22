package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.caps.model.CourseLecturer;

import java.util.List;

/**
 * @Author Anabell
 *
 */

@Repository
public interface CourseLecturerRepository extends JpaRepository< CourseLecturer,Long> {
    @Query("select  cl from CourseLecturer  cl where cl.course.courseId=:courseId AND  cl.lecturer.lecturerId=:LectureId")
    List<CourseLecturer> getCourseByCidandLid(Long courseId,Long LectureId);
    @Query("SELECT  cl from CourseLecturer  cl where cl.lecturer.lecturerId=:lecturerId")
    List<CourseLecturer> getCourseLecturersByLecturerId(Long lecturerId);



}
