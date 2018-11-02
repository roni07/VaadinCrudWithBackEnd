package vaadin.crud.operation.frontend.vaadincrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private int studentId;
    private String studentName;
    private int age;
    private double cgpa;
}
