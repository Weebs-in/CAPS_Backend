package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.*;
import sg.edu.nus.iss.caps.repository.*;

import java.util.*;
import java.util.stream.Collectors;

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
    private final CourseLecturerRepository courseLecturerRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public CourseStudentService(CourseStudentRepository courseStudentRepository,
                                CourseRepository courseRepository,
                                StudentRepository studentRepository,
                                CourseLecturerRepository courseLecturerRepository,
                                LecturerRepository lecturerRepository) {
        this.courseStudentRepository = courseStudentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseLecturerRepository = courseLecturerRepository;
        this.lecturerRepository = lecturerRepository;
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
        if (course.getCourseEnrollmentStatus() == C_COURSE_NOT_ENROLLING) {
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
     * Get all courses a student ever enrolled/completed/banned
     *
     * @param studentId Specify the student id
     * @return R
     */
    public R getAllCoursesByStudentId(Long studentId) {
        List<Object[]> records_raw = courseStudentRepository.findCoursesByStudentId(studentId);
        List<StudentCourseRecords> records = new ArrayList<>();
        for (Object[] o : records_raw) {
            StudentCourseRecords newSCR = new StudentCourseRecords((Long) o[0], (String) o[1], (Double) o[2], (Integer) o[3]);
            records.add(newSCR);
        }
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", records);
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

    /**
     * Author anabell
     * View list of enrolled students for a course
     * first find course is exit or not;
     * second find  coursestudent from courseId
     * when coursestudent list is null and then return "no enrollment student"
     * create studentlist for return R data
     * and then extract student related from coursestudentslist and then  this list added to studentlist for return
     */
    public R viewEnrollmentStudent(Long courseId, Long lecturerId) {
        //check lecturer
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);
        if (lecturer == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ":lecturer is not found");
        }
        //check this course is taughted by lecturer;
        List<CourseLecturer> courseLecturers = courseLecturerRepository.getCourseLecturersByLecturerId(lecturerId);
        boolean isCourseTaughtByLecturer = courseLecturers.stream()
                .anyMatch(cl -> cl.getCourse().getCourseId().equals(courseId));

        if (!isCourseTaughtByLecturer) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Course is not taught by the lecturer");
        }
        // check course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Course not found");
        }

        List<CourseStudent> courseStudents = courseStudentRepository.getCourseStudentsByCourseId(courseId);
        if (courseStudents == null) {
            return R.ok("there is no enrollment student for this course");
        }
        List<Student> studentList = new ArrayList<>();
        for (CourseStudent courseStudent : courseStudents) {
            Student student = courseStudent.getStudent();
            studentList.add(student);
        }
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", studentList);

    }

    public R getAvailableCoursesForStudent(Long studentId) {
        // check student
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Student not found");
        }
        //Get the faculty of the student
        Faculty faculty = student.getFaculty();

        //Get all courses
        List<Course> allCourses = courseRepository.findAll();

        //Filter out completed courses
        List<CourseStudent> completedCourses = courseStudentRepository.getCourseBySidAndStatus(studentId, CSC_STUDENT_COMPLETED);
        List<CourseStudent> enrolledCourses = courseStudentRepository.getCourseBySidAndStatus(studentId, CSC_STUDENT_ENROLLED);
        List<CourseStudent> inProgressCourses = courseStudentRepository.getCourseBySidAndStatus(studentId, CSC_COURSE_IN_PROGRESS);
        Set<Long> completedCourseIds = completedCourses.stream()
                .map(courseStudent -> courseStudent.getCourse().getCourseId())
                .collect(Collectors.toSet());
        Set<Long> enrolledCourseIds = enrolledCourses.stream()
                .map(courseStudent -> courseStudent.getCourse().getCourseId())
                .collect(Collectors.toSet());
        Set<Long> inProgressCourseIds = inProgressCourses.stream()
                .map(courseStudent -> courseStudent.getCourse().getCourseId())
                .collect(Collectors.toSet());

        //Filter out courses with a different faculty or a vacancy of 0
        Set<Course> availableCourses = allCourses.stream()
                .filter(course -> !completedCourseIds.contains(course.getCourseId())) //Exclude completed courses
                .filter(course -> !enrolledCourseIds.contains(course.getCourseId())) //Exclude enrolled courses
                .filter(course -> !inProgressCourseIds.contains(course.getCourseId())) //Exclude in progress courses
                .filter(course -> course.getCourseStatus() != C_COURSE_COMPLETED) //Exclude completed courses
                .filter(course -> course.getCourseEnrollmentStatus() == C_COURSE_ENROLLING) //Exclude not enrolling courses
                .filter(course -> course.getFaculty().equals(faculty)) //Filter by faculty
                .filter(course -> course.getCourseVacancy() > 0) // Filter by vacancy
                .collect(Collectors.toSet());
        return R.ok(new ArrayList<>(availableCourses));
    }

    public R viewStudentCoursesAndGrades(Long studentId) {
        // check student
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.RETRIEVE_FAILED + ": Student not found");
        }
        // Get all course records for the student
        List<CourseStudent> courseRecords = courseStudentRepository.getCourseBySidAndECStatus(studentId, CSC_STUDENT_ENROLLED, CSC_STUDENT_COMPLETED);

        // Create a list to store the course information with grades
        List<Map<String, Object>> courseInfoList = new ArrayList<>();

        // Calculate the total grade points and credits for GPA calculation
        double totalGradePoints = 0.0;
        int totalCredits = 0;

        // Iterate over the course records
        for (CourseStudent courseStudent : courseRecords) {
            if (courseStudent.getCourseStudentGrade() == null) {
                continue;
            }
            Course course = courseStudent.getCourse();
            double grade = courseStudent.getCourseStudentGrade();

            // Create a map to store the course information with grade
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseCode", course.getCourseCode());
            courseInfo.put("courseName", course.getCourseName());
            courseInfo.put("grade", grade);

            // Calculate the grade points and credits
            if (grade >= 0) {
                double gradePoints = grade * course.getCourseCredits();
                totalGradePoints += gradePoints;
                totalCredits += course.getCourseCredits();
            }
            // Add the course information to the list
            courseInfoList.add(courseInfo);
        }
        // Calculate the GPA
        double gpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;

        // Create a map to store the result
        Map<String, Object> result = new HashMap<>();
        result.put("courses", courseInfoList);
        result.put("gpa", gpa);

        return R.ok(result);
    }

    public R updateStudentEnrollmentStatus(Long studentId, Long courseId, int enrollmentStatus) {
        // check student
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Student not found");
        }
        // check course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Course not found");
        }

        // Check if student is enrolled in the course
        CourseStudent courseStudent = courseStudentRepository.getCourseByCidAndSid(courseId, studentId).stream()
                .findFirst()
                .orElse(null);
        if (courseStudent == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Student is not enrolled in the course");
        }

        // Update enrollment status
        courseStudent.setCourseStudentStatus(enrollmentStatus);
        courseStudentRepository.save(courseStudent);

        return R.ok(RMessage.UPDATE_SUCCESS + ": Enrollment status updated successfully");
    }


    public R getAllStudentOngoingCourseList() {
        List<Object[]> details = courseStudentRepository.getAllStudentOngoingCourseList();
        List<CourseStudentDetails> records = new ArrayList<>();
        for (Object[] o : details) {
            CourseStudentDetails newSCR = new CourseStudentDetails(
                    (Long) o[0], (Long) o[1], (String) o[2], (String) o[4], (String) o[3],
                    "-", 0.0, 0.0, (Integer) o[5]);
            records.add(newSCR);
        }
        return R.ok(records);
    }
}
