package sg.edu.nus.iss.caps.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sg.edu.nus.iss.caps.model.Admin;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:23 2023/6/25
 * @Modified by:
 */
public class AdminListener {

    @PrePersist
    public void prePersist(Admin admin) {
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Admin admin) {
        admin.setGmtModified(LocalDateTime.now());
    }
}
