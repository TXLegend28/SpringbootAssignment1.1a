package com.example.springbootassignment1_1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootassignment1_1.model.Course;
import com.example.springbootassignment1_1.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // PART A - Browser HTML (exactly as assignment requires)
    @GetMapping
    public String getCoursesHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>UFH Computer Science Courses</title>");
        html.append("<style>body{font-family:Arial,sans-serif;margin:40px;line-height:1.6;} h1,h2{color:#003087;} ul{margin-left:30px;}</style></head><body>");
        html.append("<h1>Computer Science Department Courses - University of Fort Hare</h1>");

        List<Course> all = courseService.getAllCourses();

        html.append("<h2>2 Foundation Courses</h2><ul>");
        all.stream().filter(c -> "Foundation".equals(c.getLevel())).forEach(c ->
                html.append("<li><strong>").append(c.getCourseCode()).append("</strong> - ").append(c.getName()).append("</li>"));
        html.append("</ul>");

        html.append("<h2>5 Undergraduate Courses</h2><ul>");
        all.stream().filter(c -> "Undergraduate".equals(c.getLevel())).forEach(c ->
                html.append("<li><strong>").append(c.getCourseCode()).append("</strong> - ").append(c.getName()).append("</li>"));
        html.append("</ul>");

        html.append("<h2>4 Honours Courses</h2><ul>");
        all.stream().filter(c -> "Honours".equals(c.getLevel())).forEach(c ->
                html.append("<li><strong>").append(c.getCourseCode()).append("</strong> - ").append(c.getName()).append("</li>"));
        html.append("</ul>");

        return html.toString();
    }

    // PART B - CRUD JSON endpoints with Validation
    @GetMapping("/api")
    public List<Course> getAll() { return courseService.getAllCourses(); }

    @GetMapping("/api/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @Valid @RequestBody Course course) {
        Course updated = courseService.updateCourse(id, course);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return courseService.deleteCourse(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}