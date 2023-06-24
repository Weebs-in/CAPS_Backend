package sg.edu.nus.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.nus.iss.caps.model.Lecturer;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT l FROM Lecturer l WHERE l.faculty.facultyId = :facultyId")
    List<Lecturer> getLecturersByFacultyId(Long facultyId);

    Optional<Lecturer> getLecturerByMatriculationNumber(String matricNum);

}
