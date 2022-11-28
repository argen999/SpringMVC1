package com.peaksoft.repository.repositoryimpl;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Instructor;
import com.peaksoft.entity.Student;
import com.peaksoft.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudent() {
        return entityManager.createQuery("select s from Student s").getResultList();
    }

    @Override
    public List<Student> getAllStudent(Long groupId) {
        return entityManager.createQuery("select s from Student s where s.group.id = : groupId",
                Student.class).setParameter("groupId", groupId).getResultList();
    }

    @Override
    public Student getStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public void saveStudent(Long groupId, Student student) {
        Group group = entityManager.find(Group.class, groupId);
        group.addStudent(student);
        student.setGroup(group);
        for (Course c : student.getGroup().getCourses()) {
            c.getCompany().plus();
        }
        for (Course c : student.getGroup().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
        entityManager.merge(student);
    }

    @Override
    public void updateStudent(Long id, Student student) {
        Student student1 = entityManager.find(Student.class, id);
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        entityManager.merge(student1);
    }

    @Override
    public void deleteStudent(Long id) {
        Group group = entityManager.find(Group.class, id);
        for (Course c : group.getCourses()) {
            c.getCompany().minus();
        }
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
    }

    @Override
    public void assignStudent(Long id, Long groupId) throws IOException {
        Student student = entityManager.find(Student.class, id);
        Group group = entityManager.find(Group.class, groupId);
        if (group.getStudents() != null) {
            for (Student s : group.getStudents()) {
                if (s.getId() == id) {
                    throw new IOException("This student already exists!");
                }
            }
        }
        student.setGroup(group);
        group.addStudent(student);
        entityManager.merge(group);
        entityManager.merge(student);
    }
}
