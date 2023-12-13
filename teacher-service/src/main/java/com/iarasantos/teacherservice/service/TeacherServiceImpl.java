package com.iarasantos.teacherservice.service;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import com.iarasantos.teacherservice.exceptions.ResourceNotFoundException;
import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.repository.TeacherRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Override
    public TeacherVO create(TeacherVO request) {
        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.TEACHER)
                .creationDate(new Date())
                .build();
        return DozerMapper.parseObject(repository.save(teacher), TeacherVO.class);
    }

    @Override
    public List<TeacherVO> getTeachers() {
        return DozerMapper.parseListObjects(repository.findAll(), TeacherVO.class);
    }

    @Override
    public TeacherVO getTeacherById(long teacherId) {
        Teacher teacher = repository.findById(teacherId).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id "
                        + teacherId + " not found."));
        return DozerMapper.parseObject(teacher, TeacherVO.class);
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        repository.deleteById(teacherId);
    }

    @Override
    public TeacherVO updateTeacher(TeacherVO request) {
        Teacher teacher = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id "
                        + request.getId() + " not found."));
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setRole(Role.TEACHER);

        return DozerMapper.parseObject(repository.save(teacher), TeacherVO.class);
    }

}
