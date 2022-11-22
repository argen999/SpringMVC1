package com.peaksoft.service;

import com.peaksoft.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroup();
    List<Group> getAllGroup(Long courseId);
    Group getGroupById(Long id);
    void saveGroup(Long courseId, Group group);
    void updateGroup(Long id, Group group);
    void deleteGroup(Long id);
}
