package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.StudentListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 23:00 2023/6/14
 * @Modified by:
 */

@Data
@Entity
@EntityListeners(StudentListener.class)
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_matriculation_number")
    private String matriculationNumber;

    @Column(name = "student_password")
    private String password;

    @Column(name = "student_last_name")
    private String lastName;

    @Column(name = "student_first_mid_name")
    private String firstMidName;

    @Column(name = "student_enrollment_date")
    private LocalDateTime enrollmentDate;

    @Column(name = "student_gender")
    private String gender;

    @Column(name = "student_dob")
    private LocalDate dateOfBirth;

    @Column(name = "student_gpa")
    private Double gpa;

    @ManyToOne
    @JoinColumn(name = "student_faculty_id")
    private Faculty faculty;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public Student() {
    }

    public Student(Long studentId) {
        this.studentId = studentId;
    }
}
