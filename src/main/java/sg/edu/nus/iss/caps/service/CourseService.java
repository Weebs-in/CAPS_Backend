package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.Course;
import sg.edu.nus.iss.caps.model.Faculty;
import sg.edu.nus.iss.caps.repository.CourseRepository;
import sg.edu.nus.iss.caps.repository.FacultyRepository;

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

    @Autowired
    public CourseService(CourseRepository courseRepository, FacultyRepository facultyRepository) {
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
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

    public R courseCapacityById(Long courseId){
        //Get course by id
        R courseCapacity = getCourseById(courseId);
        //Check if the course exist
        if (courseCapacity.get("data") == null) {
            //Create a new course capacity if it doesn't exist
            Course newCourseCapacity = new Course();
            newCourseCapacity.setCourseCapacity(1);
            courseCapacity.put("data", newCourseCapacity);
        }else {
            //Increment the course capacity by 1
            Course existingCourseCapacity = (Course) courseCapacity.get("data");
            int currCapacity = existingCourseCapacity.getCourseCapacity();
            existingCourseCapacity.setCourseCapacity(currCapacity + 1);
        }
        //save course
        courseCapacity = saveCourse((Course) courseCapacity.get("data"));
        return courseCapacity;
    }
}
