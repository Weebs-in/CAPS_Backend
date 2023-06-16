package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Student;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description: Simple listener to update gmt
 * @Date: Created at 23:15 2023/6/14
 * @Modified by:
 */
public class StudentListener {

    @PrePersist
    public void prePersist(Student student) {
        student.setGmtCreate(LocalDateTime.now());
        student.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Student student) {
        student.setGmtModified(LocalDateTime.now());
    }
}
