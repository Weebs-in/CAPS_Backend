package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.Course;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:36 2023/6/16
 * @Modified by:
 */

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.faculty.facultyId = :facultyId")
    List<Course> getCoursesByFacultyId(Long facultyId);

}
