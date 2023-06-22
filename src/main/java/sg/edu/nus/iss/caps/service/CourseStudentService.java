package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.Course;
import sg.edu.nus.iss.caps.model.CourseStudent;
import sg.edu.nus.iss.caps.model.Student;
import sg.edu.nus.iss.caps.repository.CourseRepository;
import sg.edu.nus.iss.caps.repository.CourseStudentRepository;
import sg.edu.nus.iss.caps.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import static sg.edu.nus.iss.caps.common.CourseCode.*;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 18:10 2023/6/16
 * @Modified by:
 */

@Service
    public class CourseStudentService {

        private final CourseStudentRepository courseStudentRepository;
        private final CourseRepository courseRepository;
        private final StudentRepository studentRepository;

    @Autowired
    public CourseStudentService(CourseStudentRepository courseStudentRepository,
                                CourseRepository courseRepository,
                                StudentRepository studentRepository) {
        this.courseStudentRepository = courseStudentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Student enroll for a course.
     * Successful when:
     * [course enrolling] + [course of same faculty] + [course not full] + [student not banned].
     *
     * @param studentId Specify the student id
     * @param courseId  Specify the course id
     * @return R
     */
    public R studentEnrollCourse(Long studentId, Long courseId) {
        // check student
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.CREATE_FAILED + ": Student not found");
        }
        // check course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return R.error(RMessage.CREATE_FAILED + ": Course not found");
        }
        if (course.getCourseEnrollmentStatus() == CSC_COURSE_NOT_ENROLLING) {
            return R.error(RMessage.CREATE_FAILED + ": Course not enrolling");
        }
        if (!course.getFaculty().getFacultyId().equals(student.getFaculty().getFacultyId())) {
            return R.error(RMessage.CREATE_FAILED + ": Course doesn't belongs to your faculty");
        }
        if (course.getCourseVacancy() == 0) {
            return R.error(RMessage.CREATE_FAILED + ": Course is full");
        }

        List<CourseStudent> csRecords = courseStudentRepository.getCourseByCidAndSid(courseId, studentId);
        // if no records, then always success
        if (csRecords.isEmpty()) {
            // create an enrollment record
            CourseStudent newRecord = new CourseStudent(new Course(courseId), new Student(studentId));
            newRecord.setCourseStudentStatus(CSC_STUDENT_ENROLLED);
            courseStudentRepository.save(newRecord);
            // reduce course vacancy
            course.setCourseVacancy(course.getCourseVacancy() - 1);
            courseRepository.save(course);
            return R.ok(RMessage.CREATE_SUCCESS + ": Enroll successful");
        }
        // if banned, do nothing
        if (csRecords.get(0).getCourseStudentStatus() == CSC_STUDENT_BANNED) {
            return R.error(RMessage.CREATE_FAILED + ": Student is banned from the course");
        }
        // if record already exists, do nothing
        return R.error(RMessage.CREATE_FAILED + ": Already enrolled");
    }

    /**
     * Remove a student from a course.
     * Always successful, and the student will
     * be permanently banned from the course.
     *
     * @param studentId Specify the student id
     * @param courseId  Specify the course id
     * @return R
     */
    public R removeStudentFromCourse(Long studentId, Long courseId) {
        // check course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return R.error(RMessage.CREATE_FAILED + ": Course not found");
        }
        // ban student
        CourseStudent csRecord = courseStudentRepository.getCourseByCidAndSid(courseId, studentId).get(0);
        csRecord.setCourseStudentGrade(0.0);
        csRecord.setCourseStudentStatus(CSC_STUDENT_BANNED);
        courseStudentRepository.save(csRecord);
        // increase course vacancy
        course.setCourseVacancy(course.getCourseVacancy() + 1);
        courseRepository.save(course);
        return R.ok(RMessage.CREATE_SUCCESS + ": Remove successful");
    }

}
