package com.iarasantos.teacherservice.service;

import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import java.util.List;

public interface TeacherService {
    public TeacherVO create(TeacherVO teacher);

    public List<TeacherVO> getTeachers();

    public TeacherVO getTeacherById(long teacherId);

    public void deleteTeacher(Long teacherId);

    public TeacherVO updateTeacher(TeacherVO request);

}
