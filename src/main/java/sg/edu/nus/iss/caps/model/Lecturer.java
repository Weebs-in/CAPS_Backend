package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.LecturerListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(LecturerListener.class)
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long lecturerId;

    @Column(name = "lecturer_matriculation_number")
    private String matriculationNumber;

    @Column(name = "lecturer_password")
    private String password;

    @Column(name = "lecturer_last_name")
    private String lastName;

    @Column(name = "lecturer_first_mid_name")
    private String firstMidName;

    @Column(name = "lecturer_gender")
    private String gender;

    @Column(name = "lecturer_dob")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "lecturer_faculty_id")
    private Faculty faculty;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public Lecturer() {
    }

    public Lecturer(Long lecturerId) {
        this.lecturerId = lecturerId;
    }
}
