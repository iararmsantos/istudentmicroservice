package com.iarasantos.teacherservice.mapper.mocks;

import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockTeacher {

    public Teacher mockEntity() {
        return mockEntity(0);
    }

    public TeacherVO mockVO() {
        return mockVO(0);
    }

    public List<TeacherVO> mockVOList() {
        List<TeacherVO> teachers = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            teachers.add(mockVO(i));
        }
        return teachers;
    }

    public List<Teacher> mockEntityList() {
        List<Teacher> teachers = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            teachers.add(mockEntity(i));
        }
        return teachers;
    }

    public Teacher mockEntity(Integer number) {
        Teacher teacher = new Teacher();
        teacher.setId(Long.valueOf(number));
        teacher.setFirstName("First Name Test" + number);
        teacher.setLastName("Last Name Test" + number);
        teacher.setEmail("Email Test" + number);
        teacher.setPhone("Phone Test" + number);
        teacher.setRole(Role.TEACHER);

        teacher.setCreationDate(new Date());

        return teacher;
    }

    public TeacherVO mockVO(Integer number) {
        TeacherVO teacher = new TeacherVO();
        teacher.setKey(Long.valueOf(number));
        teacher.setFirstName("First Name Test" + number);
        teacher.setLastName("Last Name Test" + number);
        teacher.setEmail("Email Test" + number);
        teacher.setPhone("Phone Test" + number);
        teacher.setRole(Role.TEACHER);

        teacher.setCreationDate(new Date());

        return teacher;
    }
}
