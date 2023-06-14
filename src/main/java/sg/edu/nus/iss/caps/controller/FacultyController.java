package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.model.Faculty;
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

    @Operation(summary = "Get Faculty By Id")
    @GetMapping("/getFacultyById")
    public Faculty getFacultyById(@Parameter(name = "facultyId", description = "Id of the faculty") Long facultyId) {
        return facultyService.getFacultyById(facultyId);
    }

    @Operation(summary = "Save a New Faculty")
    @PostMapping("/saveFaculty")
    public void saveFaculty(String facultyName) {
        facultyService.saveFaculty(facultyName);
    }

    @Operation(summary = "Update a Faculty by id")
    @PutMapping("/updateFaculty")
    public void updateFaculty(Long facultyId, String newFacultyName) {
        facultyService.updateFaculty(facultyId, newFacultyName);
    }

    @Operation(summary = "Delete a Faculty by id")
    @DeleteMapping("/deleteFacultyById")
    public void deleteFacultyById(Long facultyId) {
        facultyService.deleteFacultyById(facultyId);
    }
}
