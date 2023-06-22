package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.nus.iss.caps.listener.ScheduleListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(ScheduleListener.class)
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "schedule_day_of_week")
    private int scheduleDayOfWeek;

    @Column(name = "schedule_start_time")
    private LocalDate scheduleStartTime;

    @Column(name = "schedule_end_time")
    private LocalDate scheduleEndTime;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public Schedule() {
    }

    public Schedule(Long courseId) {
        this.scheduleId = scheduleId;
    }
}
