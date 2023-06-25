package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.service.CourseLecturerService;
import sg.edu.nus.iss.caps.service.CourseStudentService;
import sg.edu.nus.iss.caps.service.StudentService;
import sg.edu.nus.iss.caps.util.CalculateGpaByGrade;

@Tag(name = "Course-Lecturer API")
@RestController
@RequestMapping("/course_lecturer")
@Controller
public class CourseLecturerController {
    private final CourseLecturerService courseLecturerService;
    private final CourseStudentService courseStudentService;
    private final StudentService studentService;

    public CourseLecturerController(CourseLecturerService courseLecturerService,
                                    CourseStudentService courseStudentService,
                                    StudentService studentService) {
        this.courseLecturerService = courseLecturerService;
        this.courseStudentService = courseStudentService;
        this.studentService = studentService;
    }

    @Operation(summary = "Lecturer enroll a course")
    @PostMapping("/lecturerEnrollCourse")
    public R lecturerEnrollCourse(Long courseId, Long lecturerId) {
        return courseLecturerService.lecturerEnrollCourse(courseId, lecturerId);
    }

    @Operation(summary = "Remove a lecturer from a course")
    @PostMapping("/removeLecturerFromCourse")
    public R removeLecturerFromCourse(Long courseId, Long lecturerId) {
        return courseLecturerService.removeLecturerFromCourse(courseId, lecturerId);

    }

    @Operation(summary = "lecturer added score for the particular student for the course")
    @PostMapping("/inputScoreForTheCourse")
    public R inputScoreForIndividualStudentForTheCourseTaughtByTheLecturer(Long courseId, Long lecturerId, Long studentId, Double courseGrade) {
        return courseLecturerService.inputScoreForIndividualStudentForTheCourseTaughtByTheLecturer(courseId, lecturerId, studentId, courseGrade);
    }

    @Operation(summary = "Get all course a lecturer teach")
    @GetMapping("/getCoursesByLecturerId")
    public R getCoursesByLecturerId(Long lecturerId) {
        return courseLecturerService.getCoursesByLecturerId(lecturerId);
    }

    @Operation(summary = "Get all course a lecturer can enroll")
    @GetMapping("/getEnrollCoursesByLecturerId")
    public R getEnrollCoursesByLecturerId(Long lecturerId) {
        return courseLecturerService.getEnrollCoursesByLecturerId(lecturerId);
    }

    @Operation(summary = "Grade a student for a course, note that this includes changing their gpa")
    @PostMapping("/gradeStudentForCourse")
    public R gradeStudentForCourse(Long courseId, Long lecturerId, Long studentId, Double courseStudentGrade, Integer courseStudentStatus) {
        R gradeStatus = courseLecturerService.gradeStudentForCourse(courseId, lecturerId, studentId, courseStudentGrade, courseStudentStatus);
        if (gradeStatus.isOk()) {
            R studentStatus = courseStudentService.viewStudentCoursesAndGrades(studentId);
            Double newGrade = (Double) studentStatus.get("gpa");
            Double newGpa = CalculateGpaByGrade.convertGradeToGp(newGrade);
            return studentService.updateGpaById(studentId, newGpa);
        }
        return R.error("Grade student failed");
    }
}
