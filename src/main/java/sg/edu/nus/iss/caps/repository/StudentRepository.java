package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.caps.model.Student;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 23:17 2023/6/14
 * @Modified by:
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.faculty.facultyId = :facultyId")
    List<Student> getStudentsByFacultyId(Long facultyId);

    @Query("SELECT s FROM Student s WHERE s.matriculationNumber = :matriculationNumber")
    Student getStudentByMatriculationNumber(@Param("matriculationNumber") String matriculationNumber);
}
