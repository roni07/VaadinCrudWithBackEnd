package vaadin.crud.operation.backend.vaadincrud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vaadin.crud.operation.backend.vaadincrud.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
}
