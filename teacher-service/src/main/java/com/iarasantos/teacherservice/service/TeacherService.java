package com.iarasantos.teacherservice.service;

import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface TeacherService {
    TeacherVO create(TeacherVO teacher);

    PagedModel<EntityModel<TeacherVO>> getTeachers(Pageable pageable);

    TeacherVO getTeacherById(long teacherId);

    void deleteTeacher(Long teacherId);

    TeacherVO updateTeacher(TeacherVO request);

}
