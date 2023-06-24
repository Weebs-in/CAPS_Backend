package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.exceptions.ResourceNotFoundException;
import sg.edu.nus.iss.caps.model.Faculty;
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.repository.FacultyRepository;
import sg.edu.nus.iss.caps.repository.LecturerRepository;


@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository, FacultyRepository facultyRepository) {
        this.lecturerRepository = lecturerRepository;
        this.facultyRepository = facultyRepository;
    }

    public R saveLecturer(Lecturer lecturer) {
        // First, we check if the faculty_id is valid
        Faculty faculty = facultyRepository.findById(lecturer.getFaculty().getFacultyId()).orElse(null);
        // if the faculty does not exist, then refuse the request
        if (faculty == null) {
            return R.error(RMessage.CREATE_FAILED + ": Faculty not found");
        }
        lecturerRepository.save(lecturer);
        return R.ok(RMessage.CREATE_SUCCESS);
    }

    public R getAllLecturers() {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", lecturerRepository.findAll());
    }

    public R getLecturersByFacultyId(Long facultyId) {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", lecturerRepository.getLecturersByFacultyId(facultyId));
    }

    public R getLecturerById(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", lecturer);
    }

    public Lecturer getLecturerByMatriculationNumber(String matricNum){
        return lecturerRepository.getLecturerByMatriculationNumber(matricNum)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer NOT FOUND"));
    }

    public R updateLecturer(Lecturer lecturer) {
        // First check lecturer
        Lecturer isLecturer = lecturerRepository.findById(lecturer.getLecturerId()).orElse(null);
        if (isLecturer == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Lecturer not found");
        }
        // then check faculty
        Faculty isFaculty = facultyRepository.findById(lecturer.getFaculty().getFacultyId()).orElse(null);
        if (isFaculty == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Faculty not found");
        }
        // All fine, then save
        lecturerRepository.save(lecturer);
        return R.ok(RMessage.UPDATE_SUCCESS);
    }

    public R deleteLecturerById(Long lecturerId) {
        lecturerRepository.deleteById(lecturerId);
        return R.ok(RMessage.DELETE_SUCCESS);
    }
}
