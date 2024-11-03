import { FormControl, MenuItem, Select, useTheme } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Box, Button, TextField } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../Header";
import InputLabel from "@mui/material/InputLabel";
import useAxios from "../../../hooks/useAxios";
import Popup from "../../popup";
import CreateParent from "../create_parent";
import ToastMessage from "../../ToastMessage";
import ParentSelect from "../../ParentSelect";

const initialValues = {  
    first_name: "",
    last_name: "",
    phone: "",
    email: "",
  
    studentParents: [
    { parent_id: "" },   
  ],
};

const transformData = (data) => {
  // Extract parents data
  const parents = [];
  Object.keys(data).forEach(key => {
    const match = key.match(/^studentParents\[(\d+)\]\.parent_id$/);
    if (match) {
      const index = parseInt(match[1], 10);
      if (!parents[index]) {
        parents[index] = {}; // Initialize the object if it does not exist
      }
      parents[index].parent_id = data[key];
    }
  });

  return {
    first_name: data['first_name'] || '',
    last_name: data['last_name'] || '',
    phone: data['phone'] || '',
    email: data['email'] || '',
    studentParents: parents
  };
};

const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;

const userSchema = yup.object().shape({
  first_name: yup.string().required("First name is required"),
  last_name: yup.string().required("Last name is required"),
  phone: yup.string().matches(phoneRegExp, "Phone number is not valid.").required("Phone number is required"),
  email: yup.string().email("Email is not valid.").required("Email is required"),
  studentParents: yup.array().of(
    yup.object().shape({
      parent_id: yup.number().required("Parent ID is required")
    })
  ).required("At least one parent must be selected")
});

const CreateStudent = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  
  const [formData, setFormData] = useState({
    'first_name': '',
    'last_name': '',
    'phone': '',
    'email': '',
    'parents[0].parent_id': '',
    'parents[1].parent_id': ''
  });
  const { response, error, loading, fetchData } = useAxios();
  const [openPopup, setOpenPopup] = useState(false)
  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  })

  const fetchParents = () => {
    fetchData({
      url: "/api/parents",
      method: "GET", headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
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

  const handleInput = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };  

  const handleSubmit = async (values, { resetForm }) => {
    const transformedData = transformData(formData);
  
    // Start the request
    await fetchData({
      url: "/api/student",
      method: "POST",
      data: transformedData,
      headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
    });
  
    // Check if there was an error
    if (error) {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.data.error || error.statusText}`,
        severity: "error",
      });
    } else if (response && response.status === 201) {
      // Handle successful response
      setSnackbarState({
        open: true,
        message: "Student created successfully.",
        severity: "success",
      });
      resetForm({ values: initialValues });
    } else {
      // Handle unexpected response
      setSnackbarState({
        open: true,
        message: `Unexpected response status: ${response?.statusText || 'Unknown error'}`,
        severity: "error",
      });
    }
  };

  return (
    <Box m="20px">
      <Header title="Create Student" subtitle="Create a New Student" />
      <Formik
        onSubmit={handleSubmit}
        initialValues={initialValues}
        validationSchema={userSchema}
      >
        {({
          values,
          errors,
          touched,
          handleBlur,
          handleChange,
          handleSubmit: submitForm,
          setFieldValue,
          resetForm,
        }) =>
            (
          <form onSubmit={submitForm}>
            <Box
              display="grid"
              gap="30px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="First Name"
                onBlur={handleBlur}
                onChange={(e) => {
                  handleChange(e);
                  handleInput(e)
                }}
                value={values.first_name}
                name="first_name"
                error={!!touched.first_name && !!errors.first_name}
                helperText={touched.first_name && errors.first_name}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Last Name"
                onBlur={handleBlur}
                onChange={(e) => {
                  handleChange(e);
                  handleInput(e)
                }}
                value={values.last_name}
                name="last_name"
                error={!!touched.last_name && !!errors.last_name}
                helperText={touched.last_name && errors.last_name}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Phone"
                onBlur={handleBlur}
                onChange={(e) => {
                  handleChange(e);
                  handleInput(e)
                }}
                value={values.phone}
                name="phone"
                error={!!touched.phone && !!errors.phone}
                helperText={touched.phone && errors.phone}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Email"
                onBlur={handleBlur}
                onChange={(e) => {
                  handleChange(e);
                  handleInput(e)
                }}
                value={values.email}
                name="email"
                error={!!touched.email && !!errors.email}
                helperText={touched.email && errors.email}
                sx={{ gridColumn: "span 2" }}
              />
              {!loading && values.studentParents.map((parent, index) => (
                <ParentSelect
                index={index}
                touched={touched}
                errors={errors}
                parent={parent}
                options={parentsResponse}
                handleChange={handleChange}
                handleBlur={handleBlur}
                handleInput={handleInput}
              />
              ))}
              <Button
                color="secondary"
                variant="contained"
                onClick={() => {
                  // Update the parents array using setFieldValue
                  setFieldValue("studentParents", [...values.studentParents, { parent_id: "" }]);
                }}
              >
                Add Another Parent
              </Button>
              <Button
                color="secondary"
                variant="contained"
                onClick={() => setOpenPopup(true)}
              >
                Create Parent
              </Button>
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Student
              </Button>
            </Box>
          </form>
        )}
      </Formik>
      <Popup openPopup={openPopup} setOpenPopup={setOpenPopup} title={"Create Parent"} refresh={fetchParents}>
        <CreateParent />
      </Popup>
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default CreateStudent;
