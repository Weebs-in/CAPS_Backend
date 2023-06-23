package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.iss.caps.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> getAdminByUsername(String username);
}
