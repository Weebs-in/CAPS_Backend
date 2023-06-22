package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.CourseSchedule;


import java.util.List;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    @Query("SELECT cs FROM CourseSchedule cs WHERE cs.course.courseId = :courseId AND cs.schedule.scheduleId = :scheduleId")
    List<CourseSchedule> getCourseByCidAndSid(Long courseId, Long scheduleId);

    @Query("SELECT cs FROM CourseSchedule cs WHERE cs.course.courseId = :courseId")
    List<CourseSchedule> getCourseByCid(Long courseId);

}
