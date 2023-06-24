package sg.edu.nus.iss.caps.common;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 19:14 2023/6/16
 * @Modified by:
 */
public abstract class CourseCode {
    // Course - Student
    public static final int CSC_STUDENT_BANNED = -1;
    public static final int CSC_STUDENT_ENROLLED = 0;
    public static final int CSC_COURSE_IN_PROGRESS = 1;
    public static final int CSC_STUDENT_COMPLETED = 2;
    public static final int CSC_STUDENT_FAILED = 3;

    // Course
    public static final int C_COURSE_NOT_ENROLLING = -1;
    public static final int C_COURSE_ENROLLING = 0;
    public static final int C_COURSE_IN_PROGRESS = 1;
    public static final int C_COURSE_COMPLETED = 2;

    // Lecturer
    public static final int CSC_LECTURER_ENROLLED = 0;
    public static final int CSC_LECTURER_REMOVED = 1;

    // Schedule
    public static final String CSC_COURSE_SCHEDULE = "";
}
