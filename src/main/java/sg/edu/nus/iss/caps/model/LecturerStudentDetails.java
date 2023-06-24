package sg.edu.nus.iss.caps.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 21:31 2023/6/24
 * @Modified by:
 */

@Getter
@Setter
public class LecturerStudentDetails extends CourseStudentDetails {
    private String courseCode;
    private Integer courseCredits;

    public LecturerStudentDetails(Long studentId, String courseName, String lastName,
                                  String firstMidName, String gender, Double gpa,
                                  Double courseStudentGrade, Integer courseStudentStatus,
                                  Long courseId, String courseCode, Integer courseCredits) {
        super(courseId, studentId, courseName, lastName, firstMidName, gender, gpa, courseStudentGrade, courseStudentStatus);
        this.courseCode = courseCode;
        this.courseCredits = courseCredits;
    }
}
