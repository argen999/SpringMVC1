package com.peaksoft.repository.repositoryimpl;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Instructor;
import com.peaksoft.entity.Student;
import com.peaksoft.repository.InstructorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class InstructorRepositoryImpl implements InstructorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Instructor> getAllInstructor() {
        return entityManager.createQuery("select i from Instructor i").getResultList();
    }

    @Override
    public List<Instructor> getAllInstructor(Long courseId) {
        return entityManager.createQuery("select i from Instructor i where i.course.id = : courseId",
                Instructor.class).setParameter("courseId", courseId).getResultList();
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    public void saveInstructor(Long courseId, Instructor instructor) {
        System.out.println("5");
        Course course = entityManager.find(Course.class, courseId);
        course.addInstructor(instructor);
        instructor.setCourse(course);
        entityManager.merge(instructor);
        System.out.println("6");
    }

    @Override
    public void updateInstructor(Long id, Instructor instructor) {
        Instructor instructor1 = entityManager.find(Instructor.class, id);
        instructor1.setFirstname(instructor.getFirstname());
        instructor1.setLastname(instructor.getLastname());
        instructor1.setPhoneNumber(instructor.getPhoneNumber());
        instructor1.setEmail(instructor.getEmail());
        instructor1.setSpecialization(instructor.getSpecialization());
        entityManager.merge(instructor1);
    }

    @Override
    public void deleteInstructor(Long id) {
        entityManager.remove(entityManager.find(Instructor.class, id));
    }

    @Override
    public void assignInstructor(Long id, Long courseId) throws IOException {
        Instructor instructor = entityManager.find(Instructor.class, id);
        Course course = entityManager.find(Course.class, courseId);
        if (course.getInstructors() != null) {
            for (Instructor i : course.getInstructors()) {
                if (i.getId() == id) {
                    throw new IOException("This instructor already exists!");
                }
            }
        }

        instructor.setCourse(course);
        course.addInstructor(instructor);
        entityManager.merge(course);
        entityManager.merge(instructor);
    }
}
