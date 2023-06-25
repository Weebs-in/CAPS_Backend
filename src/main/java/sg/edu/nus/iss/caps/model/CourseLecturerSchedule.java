package sg.edu.nus.iss.caps.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 10:24 2023/6/25
 * @Modified by:
 */

@Data
public class CourseLecturerSchedule {
    private List<Lecturer> lecturers;
    private List<Schedule> schedules;
}
