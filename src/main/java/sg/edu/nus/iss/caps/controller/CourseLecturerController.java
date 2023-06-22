package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.service.CourseLecturerService;
@Tag(name = "Course-Lecturer API")
@RestController
@RequestMapping("/course_lecturer")
@Controller
public class CourseLecturerController {
    @Autowired
    private CourseLecturerService courseLecturerService;

    @Operation(summary = "Lecturer enroll a course")
    @PostMapping("/LecturerEnrollCourse")
    public R lecturerEnrollCourse( Long courseId,Long lecturerId) {
        return courseLecturerService.lecturerEnrollCourse(courseId ,lecturerId);
    }
    @Operation(summary = "Remove a lecturer from a course")
    @PostMapping("/removeLecturerFromCourse")
    public R removeLecturerFromCourse(Long courseId,Long lecturerId){
        return courseLecturerService.removeLecturerFromCourse(courseId,lecturerId);

    }
    @Operation(summary = "lecturer added score for the particular student for the course")
    @PostMapping("/inputScoreForTheCourse")
    public R inputScoreForIndividualStudentForTheCourseTaughtByTheLecturer(Long courseId,Long lecturerId,Long studentId,Double coursegrade ){
        return courseLecturerService.inputScoreForIndividualStudentForTheCourseTaughtByTheLecturer(courseId,lecturerId,studentId,coursegrade);
    }
}
