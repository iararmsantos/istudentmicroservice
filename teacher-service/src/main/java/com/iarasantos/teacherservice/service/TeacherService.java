package com.iarasantos.teacherservice.service;

import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.model.UpdateTeacherRequest;
import java.util.List;

public interface TeacherService {
    public Teacher create(Teacher teacher);
    public List<Teacher> getTeachers();
    public Teacher getTeacherById(long teacherId);
    public void deleteTeacher(Long teacherId);
    public void updateTeacher(Teacher teacher, UpdateTeacherRequest request);

}
