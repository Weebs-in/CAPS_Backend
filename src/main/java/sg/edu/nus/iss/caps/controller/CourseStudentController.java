package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.service.CourseStudentService;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 19:40 2023/6/16
 * @Modified by:
 */

@Tag(name = "Course-Student API")
@RestController
@RequestMapping("/course_student")
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;

    @Operation(summary = "Student enroll a course")
    @PostMapping("/studentEnrollCourse")
    public R studentEnrollCourse(Long studentId, Long courseId) {
        return courseStudentService.studentEnrollCourse(studentId, courseId);
    }

    @Operation(summary = "Remove and ban a student from a course")
    @PostMapping("/removeStudentFromCourse")
    public R removeStudentFromCourse(Long studentId, Long courseId) {
        return courseStudentService.removeStudentFromCourse(studentId, courseId);
    }

    @Operation(summary = "View list of courses that student can enroll in")
    @PostMapping("/viewStudentCourseListEnrolment")
    public R getAvailableCoursesForStudent(Long studentId){
        return courseStudentService.getAvailableCoursesForStudent(studentId);
    }

    @Operation(summary = "View student's course grade and GPA")
    @PostMapping("/viewStudentCourseGradeAndGPA")
    public R viewStudentCoursesAndGrades(Long studentId){
        return courseStudentService.viewStudentCoursesAndGrades(studentId);
    }
}
