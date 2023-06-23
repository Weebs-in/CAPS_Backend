package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.*;
import sg.edu.nus.iss.caps.repository.*;

import java.util.List;

import static sg.edu.nus.iss.caps.common.CourseCode.*;

@Service
public class CourseLecturerService {

    private final CourseLecturerRepository courseLecturerRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Autowired
    public CourseLecturerService(CourseLecturerRepository courseLecturerRepository, CourseRepository courseRepository, LecturerRepository lecturerRepository, StudentRepository studentRepository, CourseStudentRepository courseStudentRepository) {
        this.courseLecturerRepository = courseLecturerRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.courseStudentRepository = courseStudentRepository;
    }

    /**
     * Author anabell
     * Check Course and lecturer is exits or not
     *
     * @param lecturerId
     * @param courseId
     * @return
     */
    public R lecturerEnrollCourse(Long courseId, Long lecturerId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        //Check Course
        if (course == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Course not found");
        }
        //Check Lecturer
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        if (lecturer == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Lecturer not found ");
        }
        if (course.getCourseEnrollmentStatus() == CSC_COURSE_NOT_ENROLLING) {
            return R.error(RMessage.CREATE_FAILED + ": Course not enrolling");
        }
        if (!course.getFaculty().getFacultyId().equals(lecturer.getFaculty().getFacultyId())) {
            return R.error(RMessage.CREATE_FAILED + ": Course doesn't belongs to this lecturer");
        }
        List<CourseLecturer> courseLecturers = courseLecturerRepository.getCourseByCidAndLid(courseId, lecturerId);
        if (courseLecturers.isEmpty()) {
            //create an enrollment record
            CourseLecturer newRecord = new CourseLecturer(new Course(courseId), new Lecturer(lecturerId));
            newRecord.setCourseLecturerStatus(CSC_LECTURER_ENROLLED);
            courseLecturerRepository.save(newRecord);
            return R.ok(RMessage.CREATE_SUCCESS + ": Enroll successful");
        }
        return R.error(RMessage.CREATE_FAILED + ": Already enrolled");
    }

    /**
     * @param courseId
     * @param lecturerId
     * @return
     * @Author Anabell
     */
    public R removeLecturerFromCourse(Long courseId, Long lecturerId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        //Check Course
        if (course == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Course not found");
        }
        //Check Lecturer
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        if (lecturer == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Lecturer not found ");
        }
        CourseLecturer clRecord = courseLecturerRepository.getCourseByCidAndLid(courseId, lecturerId).get(0);
        clRecord.setCourseLecturerStatus(CSC_LECTURER_NOT_ENROLLING);
        courseLecturerRepository.save(clRecord);
        return R.ok(RMessage.CREATE_SUCCESS + ": Remove successful");
    }

    /**
     * @param courseId
     * @param lecturerId
     * @param studentId
     * @param coursegrade
     * @return
     * @Anabell
     */
    public R inputScoreForIndividualStudentForTheCourseTaughtByTheLecturer(Long courseId, Long lecturerId, Long studentId, Double coursegrade) {

        Course course = courseRepository.findById(courseId).orElse(null);
        //Check Course
        if (course == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Course not found");
        }
        //Check Lecturer
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        if (lecturer == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Lecturer not found ");
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":Student not found");
        }
        List<CourseLecturer> courseLecturers = courseLecturerRepository.getCourseLecturersByLecturerId(lecturerId);
        //Check course is taught by lecturer
        boolean isCourseTaughtByLecturer = courseLecturers.stream()
                .anyMatch(cl -> cl.getCourse().getCourseId().equals(courseId));

        if (!isCourseTaughtByLecturer) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Course is not taught by the lecturer");
        }

        List<CourseStudent> courseStudents = courseStudentRepository.getCourseStudentsByCourseId(courseId);
        //Check student is in this course;
        boolean isStudentEnrolled = courseStudents.stream()
                .anyMatch(cs -> cs.getStudent().getStudentId().equals(studentId));

        if (!isStudentEnrolled) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Student is not enrolled in the course");
        }
        //get coursestudent form studentId;
        CourseStudent courseStudent = courseStudents.stream()
                .filter(cs -> cs.getStudent().getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        courseStudent.setCourseStudentGrade(coursegrade);
        courseStudentRepository.save(courseStudent);

        return R.ok(RMessage.UPDATE_SUCCESS + "updating student marks for the course taught by this lecturer is ok");
    }
}