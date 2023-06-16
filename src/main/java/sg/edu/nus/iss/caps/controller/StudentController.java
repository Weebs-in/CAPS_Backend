package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.Student;
import sg.edu.nus.iss.caps.service.StudentService;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 9:32 2023/6/15
 * @Modified by:
 */

@Tag(name = "Student API")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Get all students")
    @GetMapping("/getAllStudents")
    public R getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Get student by id")
    @GetMapping("/getStudentById")
    public R getStudentById(Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @Operation(summary = "Get students by faculty id")
    @GetMapping("/getStudentsByFacultyId")
    public R getStudentsByFacultyId(Long facultyId) {
        return studentService.getStudentsByFacultyId(facultyId);
    }

    @Operation(summary = "Save a new student")
    @PostMapping("/saveStudent")
    public R saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @Operation(summary = "Update a student")
    @PutMapping("/updateStudent")
    public R updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @Operation(summary = "Delete a student by id")
    @DeleteMapping("/deleteStudentById")
    public R deleteStudentById(Long studentId) {
        return studentService.deleteStudentById(studentId);
    }
}
