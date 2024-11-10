import React, { useEffect, useState } from "react";
import { Box, Button, TextField } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import CreateParent from "../create_parent";
import { useLocation, useParams } from "react-router-dom";
import StudentForm from "../StudentForm";
import useAxios from "../../hooks/useAxios";
import Popup from '../../components/Popup'
import Header from '../../components/Header'
import ToastMessage from '../../components/ToastMessage'
import axios from "axios";

// const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;

// const userSchema = yup.object().shape({
//   first_name: yup.string().required("First name is required"),
//   last_name: yup.string().required("Last name is required"),
//   phone: yup.string().matches(phoneRegExp, "Phone number is not valid.").required("Phone number is required"),
//   email: yup.string().email("Email is not valid.").required("Email is required"),
//   studentParents: yup.array().of(
//     yup.object().shape({
//       parent_id: yup.number().required("Parent ID is required")
//     })
//   ).required("At least one parent must be selected")
// });

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
  const location = useLocation();
  const studentData = location.state?.studentData; 

  const fetchParents = () => {
    fetchData({
      url: "/api/parents",
      method: "GET", 
      headers: { 
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
        'Cache-Control': 'no-cache',
      }
    });
  };

  useEffect(() => {
    fetchParents();    
    if(error){
      console.error("Error fetching parents:", error);
      setSnackbarState({
        open: true,
        message: `Error fetching parents: ${error.status} ${error.statusText}`,
        severity: "error",
      })
  } 
  }, [])

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

  const handleSubmit = async (values, form) => {
    createStudent(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Student created successfully.",
        severity: "success",
      });
      console.log("Form submitted and reset successfully.");
      form.reset();
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });
      console.error("Error submitting form:", error.message);
    })
  };

  return (
    <Box m="20px">
      <Header title={isEdit ? "Edit Student" : "Create Student"} subtitle={isEdit ? "Update Student Information" : "Create a New Student"} />
      <StudentForm onSubmit={handleSubmit} openCreateParents={setOpenPopup} parentOptions={parentOptions}/>
      
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
