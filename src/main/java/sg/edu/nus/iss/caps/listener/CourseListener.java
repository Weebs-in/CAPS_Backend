package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Course;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:19 2023/6/16
 * @Modified by:
 */
public class CourseListener {

    @PrePersist
    public void prePersist(Course course) {
        course.setGmtCreate(LocalDateTime.now());
        course.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Course course) {
        course.setGmtModified(LocalDateTime.now());
    }
}
