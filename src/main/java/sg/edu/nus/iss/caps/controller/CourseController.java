package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.Course;
import sg.edu.nus.iss.caps.service.CourseService;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:50 2023/6/16
 * @Modified by:
 */

@Tag(name = "Course API")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Get all courses")
    @GetMapping("/getAllCourses")
    public R getAllCourses() {
        return courseService.getAllCourses();
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/getCourseById")
    public R getCourseById(Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @Operation(summary = "Get courses by faculty id")
    @GetMapping("/getCoursesByFacultyId")
    public R getCoursesByFacultyId(Long facultyId) {
        return courseService.getCoursesByFacultyId(facultyId);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Save a new course")
    @PostMapping("/saveCourse")
    public R saveCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Update a course")
    @PutMapping("/updateCourse")
    public R updateCourse(@RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Delete a course by id")
    @DeleteMapping("/deleteCourseById")
    public R deleteCourseById(Long courseId) {
        return courseService.deleteCourseById(courseId);
    }

    @Operation(summary = "Get course vacancy by id")
    @GetMapping("/getCourseVacancyById")
    public int getCourseVacancyById(Long courseId) {
        return courseService.getCourseVacancyById(courseId);
    }

    @Operation(summary = "Get course capacity by id")
    @GetMapping("/getCourseCapacityById")
    public int getCourseCapacityById(Long courseId) {
        return courseService.getCourseCapacityById(courseId);
    }

    @Operation(summary = "Get students from a course by id")
    @GetMapping("/getStudentsByCourseId")
    public R getStudentsByCourseId(Long courseId) {
        return courseService.getStudentsByCourseId(courseId);
    }

    @Operation(summary = "Get course schedule and lecturers by id")
    @GetMapping("/getCourseLecturerSchedule")
    public R getCourseLecturerSchedule(Long courseId) {
        return courseService.getCourseLecturerSchedule(courseId);
    }
}
