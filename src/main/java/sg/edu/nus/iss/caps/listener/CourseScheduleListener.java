package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.CourseSchedule;

import java.time.LocalDateTime;

public class CourseScheduleListener {

    @PrePersist
    public void prePersist(CourseSchedule courseSchedule) {
        courseSchedule.setGmtCreate(LocalDateTime.now());
        courseSchedule.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(CourseSchedule courseSchedule) {
        courseSchedule.setGmtModified(LocalDateTime.now());
    }
}
