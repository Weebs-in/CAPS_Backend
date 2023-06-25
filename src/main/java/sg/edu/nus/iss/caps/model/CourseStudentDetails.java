package sg.edu.nus.iss.caps.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 18:28 2023/6/24
 * @Modified by:
 */

@Data
@AllArgsConstructor
public class CourseStudentDetails {
    private Long courseId;
    private Long studentId;

    private String courseName;
    private String lastName;
    private String firstMidName;
    private String gender;
    private Double gpa;

    private Double courseStudentGrade;
    private Integer courseStudentStatus;
}
