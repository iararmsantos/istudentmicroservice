package com.iarasantos.teacherservice.service;

import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.model.UpdateTeacherRequest;
import com.iarasantos.teacherservice.repository.TeacherRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherRepository repository;

    @Override
    public Teacher create(Teacher teacher) {
        teacher.setRole(Role.TEACHER);
        teacher.setCreationDate(new Date());
        return repository.save(teacher);
    }

    @Override
    public List<Teacher> getTeachers() {
        return (List<Teacher>) repository.findAll();
    }

    @Override
    public Teacher getTeacherById(long teacherId) {
        return repository.findTeacherById(teacherId);
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        repository.deleteById(teacherId);
    }

    @Override
    public void updateTeacher(Teacher teacher, UpdateTeacherRequest request) {
        setTeacherUpdate(teacher, request);
        repository.save(teacher);
    }

    private void setTeacherUpdate(Teacher teacher, UpdateTeacherRequest request) {
        if (request.getFirstName() != null) {
            teacher.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            teacher.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            teacher.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            teacher.setEmail(request.getEmail());
        }

        if (request.getRole() != null) {
            teacher.setRole(Role.TEACHER);
        }
    }
}
