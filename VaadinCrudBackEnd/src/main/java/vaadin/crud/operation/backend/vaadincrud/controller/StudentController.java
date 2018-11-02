package vaadin.crud.operation.backend.vaadincrud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vaadin.crud.operation.backend.vaadincrud.model.Student;
import vaadin.crud.operation.backend.vaadincrud.service.StudentService;

@RestController
@RequestMapping(value = "student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "save")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            ResponseEntity<Student> studentResponseEntity = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdStudent);
            return studentResponseEntity;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "delete/{studentId}")
    public ResponseEntity deleteStudentById(@PathVariable int studentId) {
        try {
            studentService.deleteStudent(studentId);
            ResponseEntity responseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body("deleted");
            return responseEntity;
        }
        catch (Exception e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping(value = "update/{studentId}")
    public ResponseEntity<Student> updateStudentById(@RequestBody Student student,
                                                     @PathVariable int studentId){
        try {
            Student updatedStudent = studentService.updateStudent(student, studentId);
            ResponseEntity<Student> studentResponseEntity = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedStudent);
            return studentResponseEntity;
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "all")
    public Iterable<Student> getAllStudent() {
        return studentService.getAll();
    }
}
