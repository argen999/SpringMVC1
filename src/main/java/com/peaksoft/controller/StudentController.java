package com.peaksoft.controller;

import com.peaksoft.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getAllStudentByCourseId/{groupId}")
    public String getAllStudentByCourseId(@PathVariable Long groupId, Model model) {
        model.addAttribute("getAllStudentByCourseId", studentService.getAllStudent(groupId));
        model.addAttribute("groupId", groupId);
        return "/student/get_all_student_by_group_id";
    }

    @GetMapping("/getStudentById/{id}")
    public String getStudentById(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "redirect:/getAllStudentByCourseId";
    }

}
