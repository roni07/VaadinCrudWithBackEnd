package vaadin.crud.operation.frontend.vaadincrud.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;
import vaadin.crud.operation.frontend.vaadincrud.model.Student;
import vaadin.crud.operation.frontend.vaadincrud.service.StudentService;

import java.util.Optional;

@SpringUI
@Theme("valo")
@PushStateNavigation
public class StudentUI extends UI {

    private StudentService studentService = new StudentService();

    private Grid<Student> studentGrid = new Grid<>();


    Binder<Student> studentBinder = new Binder<>();

    public Optional<Student> getStudent() {
        Student student = new Student();
        if (studentBinder.writeBeanIfValid(student)) {
            return Optional.of(student);
        } else {
            return Optional.empty();
        }
    }


    public void setStudent(Student student) {
        studentBinder.readBean(student);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        studentGrid.setCaption("Student List");
        studentGrid.setWidth(550, Unit.PIXELS);

        studentGrid.setItems(studentService.getAllStudent());
        studentGrid.addColumn(Student::getStudentId).setCaption("Student ID");
        studentGrid.addColumn(Student::getStudentName).setCaption("Student Name");
        studentGrid.addColumn(Student::getAge).setCaption("Age");
        studentGrid.addColumn(Student::getCgpa).setCaption("CGPA");

        TextField studentIDFiled = new TextField("ID");
        TextField studentNameField = new TextField("Name");
        TextField studentAgeField = new TextField("Age");
        TextField studentCgpaField = new TextField("CGPA");
        Button addButton = new Button("+Add");
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        Button updateButton = new Button("Update");
     //   Button deleteButton = new Button("Delete");


        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(addButton, updateButton);

        FormLayout studentForm = new FormLayout();
        studentForm.addComponents(studentIDFiled, studentNameField, studentAgeField,
                studentCgpaField, buttons);
        studentForm.setCaption("Add Student");

        addButton.addClickListener(clickEvent -> {
            Optional<Student> optionalStudent = this.getStudent();
            if (optionalStudent.isPresent()) {
                Student student = null;
                try {
                    student = this.studentService.createStudent(optionalStudent.get());
                    Notification.show("Student " + student.getStudentName() + " Created ");
                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                Notification.show("Error in the form entry", Notification.TYPE_WARNING_MESSAGE);
            }
            studentBinder.readBean(null);
            studentGrid.setItems(studentService.getAllStudent());
        });

        updateButton.addClickListener(clickEvent -> {
            Optional<Student> optionalStudent = this.getStudent();
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();

                try {
                    studentService.editStudent(student, student.getStudentId());
                    Notification.show("Student Id " + student.getStudentId() + " is updated");
                } catch (Exception e) {
                    Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                Notification.show("Error", Notification.TYPE_WARNING_MESSAGE);
            }
            studentBinder.readBean(null);
            studentGrid.setItems(studentService.getAllStudent());
        });

      /*  deleteButton.addClickListener(clickEvent -> {
            Optional<Student> optionalStudent = this.getStudent();
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();

                studentService.deleteStudent(student.getStudentId());
                Notification.show("Student deleted with id = " + student.getStudentId());

            } else {
                Notification.show("Error in the form", Notification.TYPE_WARNING_MESSAGE);
            }
            studentBinder.readBean(null);
            studentGrid.setItems(studentService.getAllStudent());
        });*/


        studentGrid.addColumn(student -> {
            HorizontalLayout actionBar = new HorizontalLayout();

            Button editButton = new Button();
            editButton.setIcon(VaadinIcons.EDIT);
            editButton.addClickListener(clickEvent -> this.setStudent(student));

            Button deleteButton = new Button();
            deleteButton.setIcon(VaadinIcons.CLOSE);


            deleteButton.addClickListener(clickEvent -> {
            Optional<Student> optionalStudent = this.getStudent();
            if (optionalStudent.isPresent()) {
                Student selectedStudent = optionalStudent.get();

                studentService.deleteStudent(selectedStudent.getStudentId());
                Notification.show("Student deleted with id = " + selectedStudent.getStudentId());

            } else {
                Notification.show("Error in the form", Notification.TYPE_WARNING_MESSAGE);
            }
            studentBinder.readBean(null);
            studentGrid.setItems(studentService.getAllStudent());
        });

            actionBar.addComponents(editButton, deleteButton);

            return actionBar;
        }).setRenderer(new ComponentRenderer()).setCaption("Actions");


        studentBinder
                .forField(studentIDFiled)
                .asRequired("id cannot be empty")
                .withConverter(new StringToIntegerConverter("id should be integer"))
                .bind(Student::getStudentId, Student::setStudentId);

        studentBinder
                .forField(studentNameField)
                .asRequired("name cannot be empty")
                .bind(Student::getStudentName, Student::setStudentName);

        studentBinder
                .forField(studentAgeField)
                .asRequired("age cannot be empty")
                .withConverter(new StringToIntegerConverter("age should be integer"))
                .bind(Student::getAge, Student::setAge);

        studentBinder
                .forField(studentCgpaField)
                .asRequired("cgpa cannot be empty")
                .withConverter(new StringToDoubleConverter("cgpa should be double"))
                .bind(Student::getCgpa, Student::setCgpa);


        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponents(studentGrid, studentForm);

        verticalLayout.addComponent(horizontalLayout);

        setContent(verticalLayout);
    }
}
