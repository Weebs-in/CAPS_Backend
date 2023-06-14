package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Faculty;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description: A listener to update Faculty gmt
 * @Date: Created at 17:18 2023/6/14
 * @Modified by:
 */
public class FacultyListener {

    @PrePersist
    public void prePersist(Faculty faculty) {
        faculty.setGmtCreate(LocalDateTime.now());
        faculty.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Faculty faculty) {
        faculty.setGmtModified(LocalDateTime.now());
    }
}
