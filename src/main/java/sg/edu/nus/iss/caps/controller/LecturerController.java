package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.service.LecturerService;
import sg.edu.nus.iss.caps.util.EncryptPassword;

@Tag(name = "Lecturer API")
@RestController
@RequestMapping("/lecturer")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @Operation(summary = "Get all lecturers")
    @PreAuthorize("hasAuthority('sys:admin')")
    @GetMapping("/getAllLecturers")
    public R getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @Operation(summary = "Get lecturer by id")
    @GetMapping("/getLecturerById")
    public R getLecturerById(Long lecturerId) {
        return lecturerService.getLecturerById(lecturerId);
    }

    @Operation(summary = "Get lecturers by faculty id")
    @GetMapping("/getLecturersByFacultyId")
    public R getLecturersByFacultyId(Long facultyId) {
        return lecturerService.getLecturersByFacultyId(facultyId);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Save a new lecturer")
    @PostMapping("/saveLecturer")
    public R saveLecturer(@RequestBody Lecturer lecturer) {
        lecturer.setPassword(EncryptPassword.EncodePassword(lecturer.getPassword()));
        return lecturerService.saveLecturer(lecturer);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Update a lecturer")
    @PutMapping("/updateLecturer")
    public R updateLecturer(@RequestBody Lecturer lecturer) {
        lecturer.setPassword(EncryptPassword.EncodePassword(lecturer.getPassword()));
        return lecturerService.updateLecturer(lecturer);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Delete a lecturer by id")
    @DeleteMapping("/deleteLecturerById")
    public R deleteLecturerById(Long lecturerId) {
        return lecturerService.deleteLecturerById(lecturerId);
    }

    @Operation(summary = "Get all student by grade")
    @GetMapping("/getAllStudentByLecturerId")
    public R getStudentGradeById(Long lecturerId) {
        return lecturerService.getStudentGrades(lecturerId);
    }

    @Operation(summary = "Get all courses with schedule")
    @GetMapping("/getAllScheduleByLecturerId")
    public R getScheduleCoursesById(Long lecturerId) {
        return lecturerService.getScheduleByCourses(lecturerId);
    }

    @Operation(summary = "Get student performance")
    @GetMapping("/getStudentPerformanceByLecturerId")
    public R getStudentPerformanceByLecturerId(Long lecturerId) {
        return lecturerService.getStudentPerformanceByLecturerId(lecturerId);
    }
}
