package com.peaksoft.repository.repositoryimpl;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.repository.GroupRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class GroupRepositoryImpl implements GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Group> getAllGroup() {
        return entityManager.createQuery("select g from Group g ").getResultList();
    }

    @Override
    public List<Group> getAllGroup(Long courseId) {
        List<Group> groups = entityManager.find(Course.class, courseId).getGroups();
        groups.forEach(System.out::println);
        return groups;
    }

    @Override
    public Group getGroupById(Long id) {
        return entityManager.find(Group.class, id);
    }

    @Override
    public void saveGroup(Long courseId, Group group) {
        Course course = entityManager.find(Course.class, courseId);
        course.addGroup(group);
        group.addCourse(course);
        entityManager.merge(group);
    }

    @Override
    public void updateGroup(Long id, Group group) {
        Group group1 = entityManager.find(Group.class, id);
        group1.setGroupName(group.getGroupName());
        group1.setDataOfStart(group.getDataOfStart());
        group1.setImage(group.getImage());
        entityManager.merge(group1);
    }

    @Override
    public void deleteGroup(Long id) {
        entityManager.remove(entityManager.find(Group.class, id));
    }

    @Override
    public void assignGroup(Long courseId, Long id) {
        System.out.println("5");
        Course course = entityManager.find(Course.class, courseId);
        System.out.println("6");
        Group group = entityManager.find(Group.class, id);
        System.out.println("7");
        course.addGroup(group);
        System.out.println("8");
        group.addCourse(course);
        System.out.println("9");
        entityManager.merge(group);
        System.out.println("10");
        entityManager.merge(course);
        System.out.println("11");
    }
}
