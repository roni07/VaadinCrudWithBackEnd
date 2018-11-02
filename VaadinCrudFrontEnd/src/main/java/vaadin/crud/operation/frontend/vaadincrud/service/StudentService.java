package vaadin.crud.operation.frontend.vaadincrud.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vaadin.crud.operation.frontend.vaadincrud.model.Student;

import java.util.List;

@Service
public class StudentService {

    private RestTemplate restTemplate = new RestTemplate();

    public List<Student> getAllStudent(){
        ResponseEntity<List<Student>> studentListResponseEntity = restTemplate.exchange(
                "http://localhost:8085/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );
        return studentListResponseEntity.getBody();
    }

    public Student createStudent(Student student) throws Exception{
        HttpEntity<Student> studentHttpEntity = new HttpEntity<>(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:8085/student/save",
                HttpMethod.POST,
                studentHttpEntity,
                Student.class
        );
        if (studentResponseEntity.getStatusCode() == HttpStatus.CREATED){
            return studentResponseEntity.getBody();
        }
        else {
            throw new Exception(studentResponseEntity.toString());
        }
    }

    public Student editStudent(Student student, int studentId) throws Exception{
        HttpEntity<Student> studentHttpEntity = new HttpEntity<>(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:8085/student/update/" + studentId,
                HttpMethod.PUT,
                studentHttpEntity,
                Student.class
        );
        if (studentResponseEntity.getStatusCode() == HttpStatus.OK){
            return studentResponseEntity.getBody();
        }
        else{
            throw new Exception(studentResponseEntity.toString());
        }
    }

    public void deleteStudent(int studentId) {
        restTemplate.exchange(
                "http://localhost:8085/student/delete/" + studentId,
                HttpMethod.DELETE,
                null,
                String.class
        );

    }
}
