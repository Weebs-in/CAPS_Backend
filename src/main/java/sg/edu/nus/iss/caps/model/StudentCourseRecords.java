package sg.edu.nus.iss.caps.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 10:49 2023/6/24
 * @Modified by:
 */

@Data
@AllArgsConstructor
public class StudentCourseRecords {
    private Long courseId;
    private String courseName;
    private Double courseStudentGrade;
    private Integer courseStudentStatus;
}
