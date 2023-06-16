package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.CourseStudent;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description: Simple listener to update gmt
 * @Date: Created at 17:19 2023/6/16
 * @Modified by:
 */
public class CourseStudentListener {

    @PrePersist
    public void prePersist(CourseStudent courseStudent) {
        courseStudent.setGmtCreate(LocalDateTime.now());
        courseStudent.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(CourseStudent courseStudent) {
        courseStudent.setGmtModified(LocalDateTime.now());
    }
}
