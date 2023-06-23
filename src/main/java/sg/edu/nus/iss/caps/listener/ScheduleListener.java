package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Schedule;

import java.time.LocalDateTime;

public class ScheduleListener {

    @PrePersist
    public void prePersist(Schedule schedule) {
        schedule.setGmtCreate(LocalDateTime.now());
        schedule.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Schedule schedule) {
        schedule.setGmtModified(LocalDateTime.now());
    }
}
