package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
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

    public R saveFaculty(String facultyName) {
        Faculty faculty = new Faculty();
        faculty.setFacultyName(facultyName);
        facultyRepository.save(faculty);
        return R.ok(RMessage.CREATE_SUCCESS);
    }

    public R getFacultyById(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        return (faculty != null)
                ? R.ok(RMessage.RETRIEVE_SUCCESS).put("data", faculty)
                : R.error(RMessage.RETRIEVE_FAILED);
    }

    public R updateFaculty(Long facultyId, String facultyName) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);

        if (faculty != null) {
            faculty.setFacultyName(facultyName);
            facultyRepository.save(faculty);
            return R.ok(RMessage.UPDATE_SUCCESS);
        }
        return R.error(RMessage.UPDATE_FAILED);
    }

    public R deleteFacultyById(Long facultyId) {
        facultyRepository.deleteById(facultyId);
        return R.ok(RMessage.DELETE_SUCCESS);
    }
}
