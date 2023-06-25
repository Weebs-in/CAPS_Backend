package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.CourseSchedule;
import sg.edu.nus.iss.caps.model.Schedule;

import java.util.List;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    @Query("SELECT cs FROM CourseSchedule cs WHERE cs.course.courseId = :courseId AND cs.schedule.scheduleId = :scheduleId")
    List<CourseSchedule> getCourseByCidAndSid(Long courseId, Long scheduleId);

    @Query("SELECT cs FROM CourseSchedule cs WHERE cs.course.courseId = :courseId")
    List<CourseSchedule> getCourseByCid(Long courseId);

    @Query("SELECT s FROM CourseSchedule cs JOIN Schedule s ON s.scheduleId = cs.schedule.scheduleId WHERE cs.course.courseId = :courseId")
    List<Schedule> getSchedulesByCourseId(@Param("courseId") Long courseId);
}
