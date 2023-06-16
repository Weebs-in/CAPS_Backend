package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.CourseStudentListener;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 17:18 2023/6/16
 * @Modified by:
 */

@Data
@Entity
@EntityListeners(CourseStudentListener.class)
@Table(name = "course_student")
public class CourseStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_student_id")
    private Long courseStudentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "course_student_grade")
    private Double courseStudentGrade;

    @Column(name = "course_student_status")
    private Integer courseStudentStatus;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public CourseStudent() {
    }

    public CourseStudent(Course course, Student student) {
        this.course = course;
        this.student = student;
    }
}
