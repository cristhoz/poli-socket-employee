package co.edu.poligran.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String dateOfBirth;
    private Integer salary;
    private String jobTitle;
    private String createdAt;
    private String updatedAt;
}
