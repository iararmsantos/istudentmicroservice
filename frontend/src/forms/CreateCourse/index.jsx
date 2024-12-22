import React, { useCallback, useEffect, useState } from "react";
import { Box } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import Header from '../../components/Header'
import ToastMessage from '../../components/ToastMessage'
import axios from "axios";
import CourseForm from "../CourseForm";

const CreateCourse = () => {        
  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  })
  const { courseId } = useParams();
  const isEdit = courseId > 0; 
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState({ 
    id: "",
    title: "",
    year: "",
    section: "",
    teacher_id: "",   
  });
  const [teachersResponse, setTeachersResponse] = useState();

  useEffect(() => {
    if (courseId !== '0' && isEdit) {
      loadCourse();
    }  
  }, [])

  const fetchData = ({ url, method, data, headers }) => {
    return new Promise((resolve, reject) => {
      axios({ url, method, data, headers })
        .then(response => resolve(response.data))
        .catch(error => reject(error));
    });
  };

  const fetchTeachers = useCallback(async () =>
    {      
      try {
        const response = await fetchData({
          url: "/api/teachers",
          method: "GET", 
          headers: { 
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Cache-Control': 'no-cache',
          }
        });
        setTeachersResponse(response);
      } catch(error) {
        console.log({error})
        setSnackbarState({
          message: `Error fetching data: ${error.status} \"${error.data.path}\" ${error.statusText}`,
          severity: "error",
        });
      }
      
    });

    useEffect(() => {        
        fetchTeachers();   
      }, []);
      
      const teachers = Array.isArray(teachersResponse?._embedded.teacherVOList) ? teachersResponse._embedded.teacherVOList : [];
      const teachersOptions = teachers.map(teacher => ({
        value: teacher.id,
        label: `${teacher.first_name} ${teacher.last_name}`
      }))
      .sort((a, b) => a.label.localeCompare(b.label));
      
  async function loadCourse() {
    try {
      const response = await fetchData({
        url: `/api/courses/${courseId}`,
        method: "GET",
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
      });      
      setInitialValues({
        id: response.id,
        title: response.title,
        year: response.year,
        section: response.section,
        teacher_id: response.teacher_id,        
      });
    } catch (error) {
      setSnackbarState({
        open: true,
        message: `Error recovering courses: ${error.message}`,
        severity: "error",
      });
      navigate('/courses');  
    }
  }

  const createCourse = async (formData) => {
    try {
      const response = await fetchData({
        url: "/api/courses",
        method: "POST",
        data: formData,
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
      });
      return response;
    } catch (error) {
      console.error("Error creating parent:", error);
      throw error; // Allows handleSubmit to catch this
    }
  };

  const updateCourse = async (formData) => {
    try {
      const response = await fetchData({
        url: "/api/courses",
        method: "PUT",
        data: formData,
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
      });
      return response;
    } catch (error) {
      console.error("Error creating parent:", error);
      throw error; // Allows handleSubmit to catch this
    }
  };

  const handleSubmit = async (values, form) => {
    console.log({values})
    if (isEdit) {
      updateCourse(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Course updated successfully.",
        severity: "success",
      });      
      // loadCourse();
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });      
    })
    } else {
      createCourse(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Course created successfully.",
        severity: "success",
      });      
      // navigate('/courses');  
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });      
    })
    }
  };


  return (
    <Box m="20px">
      <Header title={isEdit ? "Edit Course" : "Create Course"} subtitle={isEdit ? "Update Course Information" : "Create a New Course"} />
      <CourseForm onSubmit={handleSubmit} initialValues={initialValues} isEdit={isEdit} teachersOptions={teachersOptions}/>            
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default CreateCourse;
