package com.peaksoft.controller;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Instructor;
import com.peaksoft.service.CourseService;
import com.peaksoft.service.GroupService;
import com.peaksoft.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
public class CourseController {

    private final CourseService courseService;
    private final GroupService groupService;
    private final InstructorService instructorService;

    @Autowired
    public CourseController(CourseService courseService, GroupService groupService, InstructorService instructorService) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.instructorService = instructorService;
    }

    @GetMapping("/getAllCourse/{companyId}")
    public String getAllCourse(@PathVariable Long companyId, Model model) {
        model.addAttribute("getAllCourse", courseService.getAllCourse());
        model.addAttribute("companyId", companyId);
        return "/course/see_all_courses";
    }

    @GetMapping("/getAllCourseByCompanyId/{companyId}")
    public String getAllCourseByCompanyId(@PathVariable Long companyId,
                                          @ModelAttribute("group") Group group,
                                          @ModelAttribute("instructor") Instructor instructor,
                                          Model model) {
        model.addAttribute("getAllCourseByCompanyId", courseService.getAllCourse(companyId));
        model.addAttribute("companyId", companyId);
        model.addAttribute("groups", groupService.getAllGroup());
        model.addAttribute("instructors", instructorService.getAllInstructor());
        return "/course/get_all_course_by_company_id";
    }

    @GetMapping("/getCourseById/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "redirect:/getAllCourseByCompanyId";
    }

    @GetMapping("/getAllCourseByCompanyId/{companyId}/new")
    public String newCourse(@PathVariable Long companyId, Model model) {
        model.addAttribute("newCourse", new Course());
        model.addAttribute("companyId", companyId);
        return "/course/save_course";
    }

    @PostMapping("/{companyId}/save")
    public String saveCourse(@PathVariable Long companyId, @ModelAttribute("newCourse") Course course) {
        courseService.saveCourse(companyId, course);
        return "redirect:/getAllCourseByCompanyId/" + companyId;
    }

    @GetMapping("/updateCourse/{id}")
    public String updateCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("updateCourse", course);
        model.addAttribute("companyId", course.getCompany().getId());
        return "/course/update_course";
    }

    @PostMapping("/{companyId}/{id}/saveUpdateCourse")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @PathVariable("id") Long id, @ModelAttribute("updateCourse") Course course) {
        courseService.updateCourse(id, course);
        return "redirect:/getAllCourseByCompanyId/" + companyId;
    }

    @GetMapping("/{companyId}/{id}/deleteCourseById")
    public String deleteCourseById(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/getAllCourseByCompanyId/" + companyId;
    }

    @PostMapping("/{courseId}/assignGroup")
    private String assignGroup(@PathVariable Long courseId,
                               @ModelAttribute("group") Group group) throws IOException {
        System.out.println(group);
        Long id = group.getId();
        groupService.assignGroup(courseId, id);
        return "redirect:/getAllGroupByCourseId/"+courseId;
    }

    @PostMapping("/{courseId}/assignInstructor")
    private String assignInstructorToCourse(@PathVariable("courseId") Long courseId,
                                            @ModelAttribute("instructor") Instructor instructor) throws IOException {
        instructorService.assignInstructor(instructor.getId(), courseId);
        return "redirect:/getAllCourseByCompanyId/ " + courseId;
    }


}
