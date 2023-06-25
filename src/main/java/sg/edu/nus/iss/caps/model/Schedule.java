package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import sg.edu.nus.iss.caps.listener.ScheduleListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime scheduleStartTime;

    @Column(name = "schedule_end_time")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime scheduleEndTime;

    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    public Schedule() {
    }

    public Schedule(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
