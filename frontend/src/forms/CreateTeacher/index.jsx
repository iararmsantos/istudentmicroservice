import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import Header from '../../components/Header'
import ToastMessage from '../../components/ToastMessage'
import axios from "axios";
import TeacherForm from "../TeacherForm";

const CreateTeacher = () => {    
  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  })
  const {teacherId} = useParams();
  const isEdit = teacherId > 0; 
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState({ 
    id: "", 
    first_name: "",
    last_name: "",
    phone: "",
    email: "",    
  });

  useEffect(() => {
    if (teacherId !== '0' && isEdit) {
      loadTeacher();
    }  
  }, [])

  const fetchTeacher = ({ url, method, data, headers }) => {
    return new Promise((resolve, reject) => {
      axios({ url, method, data, headers })
        .then(response => resolve(response.data))
        .catch(error => reject(error));
    });
  };

  async function loadTeacher() {
    try {
      const response = await fetchTeacher({
        url: `/api/teachers/${teacherId}`,
        method: "GET",
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
      });      
      setInitialValues({
        id: response.id,
        first_name: response.first_name,
        last_name: response.last_name,
        phone: response.phone,
        email: response.email,        
      });
    } catch (error) {
      setSnackbarState({
        open: true,
        message: `Error recovering teacher: ${error.message}`,
        severity: "error",
      });
      navigate('/teachers');  
    }
  }

  const createTeacher = async (formData) => {
    try {
      const response = await fetchTeacher({
        url: "/api/teachers",
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

  const updateTeacher = async (formData) => {
    try {
      const response = await fetchTeacher({
        url: "/api/teachers",
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
    if (isEdit) {
      updateTeacher(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Teacher updated successfully.",
        severity: "success",
      });      
      loadTeacher();
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });      
    })
    } else {
      createTeacher(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Teacher created successfully.",
        severity: "success",
      });      
      navigate('/teachers');  
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
      <Header title={isEdit ? "Edit Teacher" : "Create Teacher"} subtitle={isEdit ? "Update Teacher Information" : "Create a New Teacher"} />
      <TeacherForm onSubmit={handleSubmit} initialValues={initialValues} isEdit={isEdit}/>            
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default CreateTeacher;
