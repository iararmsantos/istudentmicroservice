package com.iarasantos.studentservice.unittests.mapper.mocks;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockStudent {

    public Student mockEntity() {
        return mockEntity(0);
    }

    public StudentVO mockVO() {
        return mockVO(0);
    }

    public List<StudentVO> mockVOList() {
        List<StudentVO> students = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            students.add(mockVO(i));
        }
        return students;
    }

    public List<Student> mockEntityList() {
        List<Student> students = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            students.add(mockEntity(i));
        }
        return students;
    }

    public Student mockEntity(Integer number) {
        Student student = new Student();
        student.setId(number.longValue());
        student.setFirstName("First Name Test" + number);
        student.setLastName("Last Name Test" + number);
        student.setEmail("Email Test" + number);
        student.setPhone("Phone Test" + number);
        student.setRole(Role.STUDENT);

        student.setCreationDate(new Date());

        return student;
    }

    public StudentVO mockVO(Integer number) {
        StudentVO student = new StudentVO();
        student.setKey(number.longValue());
        student.setFirstName("First Name Test" + number);
        student.setLastName("Last Name Test" + number);
        student.setEmail("Email Test" + number);
        student.setPhone("Phone Test" + number);
        student.setRole(Role.STUDENT);

        student.setCreationDate(new Date());

        return student;
    }
}
