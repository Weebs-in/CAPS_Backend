package sg.edu.nus.iss.caps.util;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 12:12 2023/6/25
 * @Modified by:
 */
public class CalculateGpaByGrade {

    public static double convertGradeToGp(double grade) {
        double fullGrade = 100.0;
        double fullGp = 5.0;
        double gradeToGpScale = fullGp / fullGrade;
        if (!Double.isNaN(grade)) {
            return Math.round(grade * gradeToGpScale * 100.0) / 100.0;
        } else {
            return 0.0;
        }
    }
}
