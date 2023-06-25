package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.Admin;
import sg.edu.nus.iss.caps.model.Student;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:26 2023/6/25
 * @Modified by:
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT ad FROM Admin ad WHERE ad.adminUsername = :username")
    Admin getAdminByUsername(@Param("username") String username);
}
