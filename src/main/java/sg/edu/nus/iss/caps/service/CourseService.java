package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.*;
import sg.edu.nus.iss.caps.repository.CourseLecturerRepository;
import sg.edu.nus.iss.caps.repository.CourseRepository;
import sg.edu.nus.iss.caps.repository.CourseScheduleRepository;
import sg.edu.nus.iss.caps.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:37 2023/6/16
 * @Modified by:
 */

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final CourseLecturerRepository courseLecturerRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         FacultyRepository facultyRepository,
                         CourseLecturerRepository courseLecturerRepository,
                         CourseScheduleRepository courseScheduleRepository) {
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
        this.courseLecturerRepository = courseLecturerRepository;
        this.courseScheduleRepository = courseScheduleRepository;
    }

    public R saveCourse(Course course) {
        Faculty faculty = facultyRepository.findById(course.getFaculty().getFacultyId()).orElse(null);
        if (faculty == null) {
            return R.error(RMessage.CREATE_FAILED + ": Faculty not found");
        }
        courseRepository.save(course);
        return R.ok(RMessage.CREATE_SUCCESS);
    }

    public R getAllCourses() {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", courseRepository.findAll());
    }

    public R getCoursesByFacultyId(Long facultyId) {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", courseRepository.getCoursesByFacultyId(facultyId));
    }

    public R getCourseById(Long courseId) {
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", courseRepository.findById(courseId).orElse(null));
    }

    public R updateCourse(Course course) {
        Course isCourse = courseRepository.findById(course.getCourseId()).orElse(null);
        if (isCourse == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Course not found");
        }
        Faculty isFaculty = facultyRepository.findById(course.getFaculty().getFacultyId()).orElse(null);
        if (isFaculty == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Faculty not found");
        }
        // All fine, then save
        courseRepository.save(course);
        return R.ok(RMessage.UPDATE_SUCCESS);
    }

    public R deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
        return R.ok(RMessage.DELETE_SUCCESS);
    }

    public int getCourseVacancyById(Long courseId) {

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getCourseVacancy();
        }
        return 0;
    }

    public int getCourseCapacityById(Long courseId) {

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getCourseCapacity();
        }
        return 0;
    }

    public R getStudentsByCourseId(Long courseId) {
        List<Object[]> records_raw = courseRepository.findStudentsByCourseId(courseId);
        List<CourseStudentDetails> records = new ArrayList<>();
        for (Object[] o : records_raw) {
            CourseStudentDetails newCSD = new CourseStudentDetails(courseId, (Long) o[0],
                    (String) o[1], (String) o[2], (String) o[3], (String) o[4], (Double) o[5], (Double) o[6], (Integer) o[7]);
            records.add(newCSD);
        }
        return R.ok(records);
    }

    public R getCourseLecturerSchedule(Long courseId) {
        // set schedules
        CourseLecturerSchedule courseLecturerSchedule = new CourseLecturerSchedule();
        List<Schedule> schedule_records = courseScheduleRepository.getSchedulesByCourseId(courseId);
        courseLecturerSchedule.setSchedules(schedule_records);
        // set lecturers
        List<Lecturer> lecturer_records = courseLecturerRepository.getLecturersByCourseId(courseId);
        courseLecturerSchedule.setLecturers(lecturer_records);
        return R.ok(courseLecturerSchedule);
    }
}
