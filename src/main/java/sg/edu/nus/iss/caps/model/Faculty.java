package sg.edu.nus.iss.caps.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 9:01 2023/6/14
 * @Modified by:
 */

@Data
@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Long facultyId;

    @Column(name = "faculty_name")
    private String facultyName;
}
