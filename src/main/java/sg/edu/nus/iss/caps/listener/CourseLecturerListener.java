package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.CourseLecturer;

import java.time.LocalDateTime;


public class CourseLecturerListener {

    @PrePersist
    public void prePersist(CourseLecturer courseLecturer) {
        courseLecturer.setGmtCreate(LocalDateTime.now());
        courseLecturer.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(CourseLecturer courseLecturer) {
        courseLecturer.setGmtModified(LocalDateTime.now());
    }
}
