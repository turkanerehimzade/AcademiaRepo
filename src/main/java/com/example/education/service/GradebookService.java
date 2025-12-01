package com.example.education.service;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.grade.Grade;
import com.example.education.dao.entity.student.Student;
import com.example.education.dao.repository.GradeRepository;
import com.example.education.dao.repository.StudentRepository;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseWithGradebookResponse;
import com.example.education.dto.response.gradebook.GradeItemResponse;
import com.example.education.dto.response.gradebook.GradebookResponse;
import com.example.education.dto.response.student.StudentGradebookResponse;
import com.example.education.dto.response.student.StudentMiniResponse;
import com.example.education.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Transactional(readOnly = true)
    public SuccessResponse<StudentGradebookResponse> getStudentGradebook(Long studentId) {

        Student student = studentRepository.findByIdWithCourses(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        String fullName = student.getUser().getFirstName() + " " + student.getUser().getLastName();

        StudentMiniResponse studentResponse = new StudentMiniResponse(student.getId(), fullName);

        List<CourseWithGradebookResponse> courseResponses =
                student.getCourses().stream()
                        .map(course -> buildCourseResponse(student, course))
                        .toList();

        StudentGradebookResponse data = new StudentGradebookResponse(
                studentResponse,
                courseResponses,
                OffsetDateTime.now()
        );

        return SuccessResponse.createSuccessResponse(data, ResponseCode.SUCCESS);
    }

    private CourseWithGradebookResponse buildCourseResponse(Student student, Course course) {

        List<Grade> grades = gradeRepository.findByStudentIdAndCourseId(student.getId(), course.getId());

        List<GradeItemResponse> gradeItems = grades.stream()
                .map(g -> new GradeItemResponse(
                        g.getId(),
                        g.getTitle(),
                        g.getScore(),
                        g.getMaxScore(),
                        calcPercentage(g.getScore(), g.getMaxScore())
                ))
                .toList();

        double overall = calcOverall(grades);

        GradebookResponse gradebook = new GradebookResponse(overall, gradeItems);

        return new CourseWithGradebookResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                gradebook
        );
    }

    private double calcPercentage(double score, double max) {
        return max == 0 ? 0 : Math.round(((score / max) * 100) * 10) / 10.0;
    }

    private double calcOverall(List<Grade> grades) {
        double total = grades.stream().mapToDouble(Grade::getScore).sum();
        double max = grades.stream().mapToDouble(Grade::getMaxScore).sum();
        return max == 0 ? 0 : Math.round((total / max * 100) * 10) / 10.0;
    }
}
