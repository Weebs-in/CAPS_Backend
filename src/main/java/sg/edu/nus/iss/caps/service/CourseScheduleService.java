package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.common.R;
import sg.edu.nus.iss.caps.common.RMessage;
import sg.edu.nus.iss.caps.model.*;
import sg.edu.nus.iss.caps.repository.*;
import sg.edu.nus.iss.caps.model.CourseSchedule;

import java.util.List;

import static sg.edu.nus.iss.caps.common.CourseCode.*;

@Service
public class CourseScheduleService {

    private final CourseRepository courseRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    @Autowired
    public CourseScheduleService(CourseRepository courseRepository,
                                 ScheduleRepository scheduleRepository,
                                 CourseScheduleRepository courseScheduleRepository) {
        this.courseRepository = courseRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public R ScheduleByCourse(Long scheduleId, Long courseId) {
        // check schedule
        Schedule schedules = scheduleRepository.findById(scheduleId).orElse(null);
        if (schedules == null) {
            return R.error(RMessage.CREATE_FAILED + ": Student not found");
        }
        // check course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return R.error(RMessage.CREATE_FAILED + ": Course not found");
        }
        List<CourseSchedule> csSchedule = courseScheduleRepository.getCourseByCidAndSid(courseId, scheduleId);

        if (csSchedule.isEmpty()) {
            // create an enrollment record
            CourseSchedule newRecord = new CourseSchedule(new Course(courseId), new Schedule(scheduleId));
            newRecord.setCourseScheduleClassroom(CSC_COURSE_SCHEDULE);
            courseScheduleRepository.save(newRecord);
            return R.ok(RMessage.CREATE_SUCCESS + ": Enroll successful");
        }

        // if record already exists, do nothing
        return R.error(RMessage.CREATE_FAILED + ": Already");
    }

    public R deleteScheduleCourse(Long courseScheduleId) {
        courseScheduleRepository.deleteById(courseScheduleId);
        return R.ok(RMessage.DELETE_SUCCESS);
    }
}
