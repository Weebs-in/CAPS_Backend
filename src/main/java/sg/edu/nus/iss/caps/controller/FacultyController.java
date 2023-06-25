package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.service.FacultyService;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 9:39 2023/6/14
 * @Modified by:
 */

@Tag(name = "Faculty API")
@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Operation(summary = "Get all Faculties")
    @GetMapping("/getAllFaculties")
    public R getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @Operation(summary = "Get Faculty By Id")
    @GetMapping("/getFacultyById")
    public R getFacultyById(Long facultyId) {
        return facultyService.getFacultyById(facultyId);
    }

    @Operation(summary = "Save a New Faculty")
    @PostMapping("/saveFaculty")
    public R saveFaculty(String facultyName) {
        return facultyService.saveFaculty(facultyName);
    }

    @Operation(summary = "Update a Faculty by id")
    @PutMapping("/updateFaculty")
    public R updateFaculty(Long facultyId, String newFacultyName) {
        return facultyService.updateFaculty(facultyId, newFacultyName);
    }

    @PreAuthorize("hasAuthority('sys:admin')")
    @Operation(summary = "Delete a Faculty by id")
    @DeleteMapping("/deleteFacultyById")
    public R deleteFacultyById(Long facultyId) {
        return facultyService.deleteFacultyById(facultyId);
    }
}
