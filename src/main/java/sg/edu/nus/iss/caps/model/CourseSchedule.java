package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.CourseScheduleListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(CourseScheduleListener.class)
@Table(name = "course_schedule")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_schedule_id")
    private Long courseScheduleId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "course_schedule_classroom")
    private String courseScheduleClassroom;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;


    public CourseSchedule() {
    }

    public CourseSchedule(Course course, Schedule schedule) {
        this.course = course;
        this.schedule = schedule;
    }
}
