package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.Course;
import sg.edu.nus.iss.caps.model.Faculty;
import sg.edu.nus.iss.caps.model.Lecturer;
import sg.edu.nus.iss.caps.model.Schedule;
import sg.edu.nus.iss.caps.repository.FacultyRepository;
import sg.edu.nus.iss.caps.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public R getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", schedules);
    }

    public R getScheduleById(Long scheduleID) {
        Schedule schedules = scheduleRepository.findById(scheduleID).orElse(null);
        return R.ok(RMessage.RETRIEVE_SUCCESS).put("data", schedules);
    }

    public R deleteScheduleyId(Long scheduleID) {
        scheduleRepository.deleteById(scheduleID);
        return R.ok(RMessage.DELETE_SUCCESS);
    }

    public R saveSchedule(Schedule schedule) {
        Schedule schedules = scheduleRepository.findById(schedule.getScheduleId()).orElse(null);
        if (schedules == null) {
            return R.error(RMessage.CREATE_FAILED + ": not found");
        }
        scheduleRepository.save(schedule);
        return R.ok(RMessage.CREATE_SUCCESS);
    }


    public R updateSchedule(Schedule schedule) {
        // First check lecturer
        Schedule isSchedule = scheduleRepository.findById(schedule.getScheduleId()).orElse(null);
        if (isSchedule == null) {
            return R.error(RMessage.UPDATE_FAILED + ": Lecturer not found");
        }
        // All fine, then save
        scheduleRepository.save(schedule);
        return R.ok(RMessage.UPDATE_SUCCESS);
    }
}
