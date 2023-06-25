package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.*;
import sg.edu.nus.iss.caps.repository.*;

import java.util.ArrayList;
import java.util.List;


@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private final FacultyRepository facultyRepository;
    private final CourseLecturerRepository courseLecturerRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository, FacultyRepository facultyRepository, CourseLecturerRepository courseLecturerRepository, CourseStudentRepository courseStudentRepository, CourseScheduleRepository courseScheduleRepository) {
        this.lecturerRepository = lecturerRepository;
        this.facultyRepository = facultyRepository;
        this.courseLecturerRepository = courseLecturerRepository;
        this.courseStudentRepository = courseStudentRepository;
        this.courseScheduleRepository = courseScheduleRepository;
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

    public R getStudentGrades(Long lecturerId) {
        java.util.List<CourseLecturer> courseStudentList = courseLecturerRepository.getCourseLecturersByLecturerId(lecturerId);
        List<CourseStudent> allStudentList = new ArrayList<>();

        if (courseStudentList != null) {
            for (CourseLecturer course : courseStudentList) {
                List<CourseStudent> courseStudents = courseStudentRepository.getCourseStudentsByCourseId(course.getCourse().getCourseId());

                allStudentList.addAll(courseStudents);

            }
        }
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", allStudentList);
    }

    public R getScheduleByCourses(Long lecturerId) {
        java.util.List<CourseLecturer> courseStudentList = courseLecturerRepository.getCourseLecturersByLecturerId(lecturerId);
        List<CourseSchedule> scheduleList = new ArrayList<>();

        if (courseStudentList != null) {
            for (CourseLecturer course : courseStudentList) {
                List<CourseSchedule> courseStudents = courseScheduleRepository.getCourseByCid(course.getCourse().getCourseId());

                scheduleList.addAll(courseStudents);

            }
        }
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", scheduleList);
    }


    public R getStudentPerformanceByLecturerId(Long lecturerId) {
        List<Object[]> records_raw = lecturerRepository.getStudentPerformanceByLecturerId(lecturerId);
        List<LecturerStudentDetails> records = new ArrayList<>();
        for (Object[] o : records_raw) {
            LecturerStudentDetails newLSD = new LecturerStudentDetails((Long) o[0],
                    (String) o[1], (String) o[2], (String) o[3], (String) o[4], (Double) o[5], (Double) o[6], (Integer) o[7], (Long) o[8], (String) o[9], (Integer) o[10]);
            records.add(newLSD);
        }
        return R.ok(records);
    }
}
