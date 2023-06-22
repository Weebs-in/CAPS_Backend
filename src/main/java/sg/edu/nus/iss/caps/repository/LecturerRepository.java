package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.model.Student;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT l FROM Lecturer l WHERE l.faculty.facultyId = :facultyId")
    List<Lecturer> getLecturersByFacultyId(Long facultyId);

}