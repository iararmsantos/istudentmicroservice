import React, { useCallback, useEffect, useState } from "react";
import { Box } from "@mui/material";
import CreateParent from "../create_parent";
import { useNavigate, useParams } from "react-router-dom";
import StudentForm from "../StudentForm";
import useAxios from "../../hooks/useAxios";
import Popup from '../../components/Popup'
import Header from '../../components/Header'
import ToastMessage from '../../components/ToastMessage'
import axios from "axios";

const CreateStudent = () => {    
  const { response, error, loading, fetchData } = useAxios();
  const [openPopup, setOpenPopup] = useState(false)
  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  })
  const {studentId} = useParams();
  const isEdit = studentId > 0; 
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState({ 
    id: "", 
    first_name: "",
    last_name: "",
    phone: "",
    email: "",
    studentParents: [
      { parent_id: "" },   
    ],
  });

  const fetchParents = useCallback(() => 
    {
      fetchData({
        url: "/api/parents",
        method: "GET", 
        headers: { 
          'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
          'Cache-Control': 'no-cache',
        }
      });
    })

  useEffect(() => {
    if (studentId !== '0' && isEdit) {
      loadStudent();
    }

    fetchParents(); 
  
  }, [])

useEffect(() => {
  if (error) {  
    setSnackbarState({
      open: true,
      message: `Error fetching data: ${error.status} \"${error.data.path}\" ${error.statusText}`,
      severity: "error",
    });
  }
}, [error]);

  const parentsResponse = Array.isArray(response?.data) ? response.data : [];

  const parentOptions = parentsResponse.map(parent => ({
    value: parent.id,
    label: `${parent.first_name} ${parent.last_name}`
  }))
  .sort((a, b) => a.label.localeCompare(b.label));

  const fetchStudent = ({ url, method, data, headers }) => {
    return new Promise((resolve, reject) => {
      axios({ url, method, data, headers })
        .then(response => resolve(response.data))
        .catch(error => reject(error));
    });
  };

  async function loadStudent() {
    try {
      const response = await fetchStudent({
        url: `/api/students/${studentId}`,
        method: "GET",
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
      });      
      setInitialValues({
        id: response.id,
        first_name: response.first_name,
        last_name: response.last_name,
        phone: response.phone,
        email: response.email,
        studentParents: response.studentParents?.length 
        ? response.studentParents
        : [{ parent_id: "" }],
      });
    } catch (error) {
      setSnackbarState({
        open: true,
        message: `Error recovering student: ${error.message}`,
        severity: "error",
      });
      navigate('/students');  
    }
  }

  const createStudent = async (formData) => {
    try {
      const response = await fetchStudent({
        url: "/api/students",
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

  const updateStudent = async (formData) => {
    try {
      const response = await fetchStudent({
        url: "/api/students",
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
      updateStudent(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Student updated successfully.",
        severity: "success",
      });      
      loadStudent();
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });      
    })
    } else {
      createStudent(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Student created successfully.",
        severity: "success",
      });      
      form.reset();
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
      <Header title={isEdit ? "Edit Student" : "Create Student"} subtitle={isEdit ? "Update Student Information" : "Create a New Student"} />
      <StudentForm onSubmit={handleSubmit} openCreateParents={setOpenPopup} parentOptions={parentOptions} initialValues={initialValues} isEdit={isEdit}/>
      
      <Popup openPopup={openPopup} setOpenPopup={setOpenPopup} title={"Create Parent"} >
        <CreateParent onSave={() => {
          fetchParents();
          setOpenPopup(false)}}/>
      </Popup>
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default CreateStudent;
