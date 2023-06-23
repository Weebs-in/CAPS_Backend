package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.service.CourseScheduleService;

@Tag(name = "Course-Schedule API")
@RestController
@RequestMapping("/course_schedule")
public class CourseScheduleController {

    @Autowired
    private CourseScheduleService courseScheduleService;

    @Operation(summary = "Course-Schedule create")
    @PostMapping("/createScheduleByCourse")
    public R createScheduleByCourse(Long scheduleId, Long courseId) {
        return courseScheduleService.ScheduleByCourse(scheduleId, courseId);
    }

    @Operation(summary = "Remove schedule and course")
    @PostMapping("/removeScheduleByCourse")
    public R deleteScheduleFromCourse(Long courseScheduleId) {
        return courseScheduleService.deleteScheduleCourse(courseScheduleId);
    }
}
