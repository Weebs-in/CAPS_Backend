package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.iss.caps.model.Faculty;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 9:31 2023/6/14
 * @Modified by:
 */

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
