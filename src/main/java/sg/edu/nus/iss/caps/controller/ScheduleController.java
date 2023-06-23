package sg.edu.nus.iss.caps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.model.Schedule;
import sg.edu.nus.iss.caps.service.ScheduleService;

@Tag(name = "Schedule API")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "Get all schedule")
    @GetMapping("/getAllSchedule")
    public R getAllSchedule() {
        return scheduleService.getAllSchedule();
    }

    @Operation(summary = "Get schedule by id")
    @GetMapping("/getScheduleById")
    public R getScheduleById(Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @Operation(summary = "Delete a course by id")
    @DeleteMapping("/deleteScheduleById")
    public R deleteScheduleById(Long courseId) {
        return scheduleService.deleteScheduleById(courseId);
    }

    @Operation(summary = "Save a new schedule")
    @PostMapping("/saveSchedule")
    public R saveSchedule(@RequestBody Schedule schedule) {
        return scheduleService.saveSchedule(schedule);
    }

    @Operation(summary = "Update a course")
    @PutMapping("/updateSchedule")
    public R updateSchedule(@RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(schedule);
    }
}
