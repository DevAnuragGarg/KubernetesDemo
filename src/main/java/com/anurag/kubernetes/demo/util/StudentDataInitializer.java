package com.anurag.kubernetes.demo.util;

import com.anurag.kubernetes.demo.entity.Student;
import com.anurag.kubernetes.demo.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

// We will be using Kubernetes feature to initialize the data,
// so we can comment this class to avoid duplicate data initialization

@Component
public class StudentDataInitializer {

    private final StudentRepository studentRepository;

    public StudentDataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void initializeData() {
        if (studentRepository.count() > 0) {
            return;
        }

        List<Student> students = List.of(
                Student.builder().name("Anurag").age(22).gender("Male").score(88).build(),
                Student.builder().name("Ravi").age(20).gender("Male").score(75).build(),
                Student.builder().name("Priya").age(25).gender("Female").score(92).build(),
                Student.builder().name("Sahana").age(24).gender("Female").score(99).build(),
                Student.builder().name("Rocky").age(29).gender("Male").score(56).build(),
                Student.builder().name("Naisha").age(19).gender("Female").score(100).build(),
                Student.builder().name("Amit").age(21).gender("Male").score(78).build(),
                Student.builder().name("Sneha").age(23).gender("Female").score(85).build(),
                Student.builder().name("Vikram").age(27).gender("Male").score(90).build(),
                Student.builder().name("Anjali").age(26).gender("Female").score(95).build()
        );
        studentRepository.saveAll(students);
    }
}

