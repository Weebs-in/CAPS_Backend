package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.Schedule;


import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("SELECT cs FROM Schedule cs WHERE cs.scheduleId = :scheduleId")
    List<Schedule> getCourseByCidAndSid(Long scheduleId);

}
