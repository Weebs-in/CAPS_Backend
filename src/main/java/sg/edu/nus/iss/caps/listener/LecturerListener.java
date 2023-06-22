package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Lecturer;

import java.time.LocalDateTime;


public class LecturerListener {

    @PrePersist
    public void prePersist(Lecturer lecturer) {
        lecturer.setGmtCreate(LocalDateTime.now());
        lecturer.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Lecturer lecturer) {
        lecturer.setGmtModified(LocalDateTime.now());
    }
}
