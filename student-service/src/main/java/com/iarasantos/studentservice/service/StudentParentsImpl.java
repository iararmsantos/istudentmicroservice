package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentParentsImpl implements StudentParentsService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentParentsRepository parentRepository;

    @Override
    public void deleteStudentParents(Long studentId) {
        List<StudentParent> studentParents = parentRepository.findByStudentId(studentId);

        studentParents.forEach(item -> parentRepository.delete(item));
    }

    @Override
    public List<StudentParent> createParents(StudentVO studentVO, long studentId) {

        return studentVO.getStudentParents().stream()
                .peek(parent -> parent.setStudentId(studentId))
                .map(parentRepository::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentParent> updateParents(List<StudentParent> studentParents, long studentId) {
        List<StudentParent> parents = parentRepository.findByStudentId(studentId);
        List<StudentParent> savedParents = new ArrayList<>();

        if (parents.isEmpty()) {
            // If no parents are found, save all new ones in studentParents

            studentParents.forEach(parent -> {
                parent.setStudentId(studentId);
                savedParents.add(parentRepository.save(parent));
            });

        } else {
            // Update the existing records with new data from studentParents
            for (int i = 0; i < parents.size(); i++) {
                StudentParent existingParent = parents.get(i);
                if (i < studentParents.size()) {
                    StudentParent newParent = studentParents.get(i);
                    newParent.setStudentId(studentId);
                    // Update fields here as needed; assuming parentId is the field to update
                    existingParent.setParentId(newParent.getParentId());
                    studentParents.forEach(parent -> savedParents.add(parentRepository.save(existingParent)));

                }
            }

            // If there are more new parents than existing ones, save them
            if (studentParents.size() > parents.size()) {
                studentParents.subList(parents.size(), studentParents.size()).forEach(parentRepository::save);
            }
        }
        return savedParents;
    }


    private StudentParent mapToParent(StudentParent parent, Student student) {
        return StudentParent.builder()
                .parentId(parent.getParentId())
                .studentId(student.getId())
                .build();
    }
}
