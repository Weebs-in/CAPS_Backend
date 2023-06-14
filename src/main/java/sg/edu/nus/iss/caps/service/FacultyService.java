package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.model.Faculty;
import sg.edu.nus.iss.caps.repository.FacultyRepository;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 9:32 2023/6/14
 * @Modified by:
 */

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void saveFaculty(String facultyName) {
        Faculty faculty = new Faculty();
        faculty.setFacultyName(facultyName);
        facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }

    public void updateFaculty(Long facultyId, String facultyName) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);

        if (faculty != null) {
            faculty.setFacultyName(facultyName);
            facultyRepository.save(faculty);
        }
    }

    public void deleteFacultyById(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }
}
