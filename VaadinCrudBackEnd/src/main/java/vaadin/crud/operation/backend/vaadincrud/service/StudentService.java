package vaadin.crud.operation.backend.vaadincrud.service;

import org.springframework.stereotype.Service;
import vaadin.crud.operation.backend.vaadincrud.exception.ResourceAlreadyExistException;
import vaadin.crud.operation.backend.vaadincrud.exception.ResourceDoesnotExistException;
import vaadin.crud.operation.backend.vaadincrud.model.Student;
import vaadin.crud.operation.backend.vaadincrud.repository.StudentRepository;

import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());
        if (optionalStudent.isPresent()){
             throw new ResourceAlreadyExistException("id "+student.getStudentId()+" is already exist with");
        }
        else {
            return studentRepository.save(student);
        }
    }


    public void deleteStudent(int studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()){
             studentRepository.deleteById(studentId);
        }
        else{
            throw new ResourceDoesnotExistException("id " +studentId + " is not present");
        }
    }


    public Student updateStudent(Student student, int studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()){
            student.setStudentId(studentId);
            return studentRepository.save(student);
        }
        else{
            throw new ResourceDoesnotExistException("id "+ studentId +" is not present");
        }
    }

    public Iterable<Student> getAll(){
        return studentRepository.findAll();
    }
}
