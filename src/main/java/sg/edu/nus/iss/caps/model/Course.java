package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.CourseListener;

import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:18 2023/6/16
 * @Modified by:
 */

@Data
@Entity
@EntityListeners(CourseListener.class)
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_credits")
    private int courseCredits;

    @Column(name = "course_capacity")
    private int courseCapacity;

    @Column(name = "course_vacancy")
    private int courseVacancy;

    @Column(name = "course_enrollment_status")
    private int courseEnrollmentStatus;

    @ManyToOne
    @JoinColumn(name = "course_faculty_id")
    private Faculty faculty;

    @Column(name = "course_status")
    private int courseStatus;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public Course() {
    }

    public Course(Long courseId) {
        this.courseId = courseId;
    }
}
