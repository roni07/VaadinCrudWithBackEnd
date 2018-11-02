package vaadin.crud.operation.backend.vaadincrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Student {
    @Id
    private int studentId;
    private String studentName;
    private int age;
    private double cgpa;
}
