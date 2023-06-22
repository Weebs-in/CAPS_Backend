package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.CourseLecturerListener;

import java.time.LocalDateTime;


@Data
@Entity
@EntityListeners(CourseLecturerListener.class)
@Table(name = "course_lecturer")
public class CourseLecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_lecturer_id")
    private Long courseLecturerId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @Column(name = "course_lecturer_status")
    private Integer courseLecturerStatus;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public CourseLecturer() {
    }

    public CourseLecturer(Course course, Lecturer lecturer) {
        this.course = course;
        this.lecturer = lecturer;
    }
}
