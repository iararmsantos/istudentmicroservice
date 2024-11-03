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

const initialValues = {
  student: {
    first_name: "",
    last_name: "",
    phone: "",
    email: "",
  },
  parents: [
    { parent_id: "" },   
  ],
};

const transformData = (data) => {
  // Extract student data
  const student = {
    first_name: data['student.first_name'] || '',
    last_name: data['student.last_name'] || '',
    phone: data['student.phone'] || '',
    email: data['student.email'] || ''
  };

  // Extract parents data
  const parents = [];
  Object.keys(data).forEach(key => {
    const match = key.match(/^parents\[(\d+)\]\.parent_id$/);
    if (match) {
      const index = parseInt(match[1], 10);
      if (!parents[index]) {
        parents[index] = {}; // Initialize the object if it does not exist
      }
      parents[index].parent_id = data[key];
    }
  });

  return {
    student,
    parents
  };
};

const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;

const userSchema = yup.object().shape({
  student: yup.object().shape({
    first_name: yup.string().required("First name is required"),
    last_name: yup.string().required("Last name is required"),
    phone: yup.string().matches(phoneRegExp, "Phone number is not valid.").required("Phone number is required"),
    email: yup.string().email("Email is not valid.").required("Email is required"),
  }),
  parents: yup.array().of(
    yup.object().shape({
      parent_id: yup.number().required("Parent ID is required")
    })
  ).required("At least one parent must be selected")
});

const CreateStudent = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  
  const [formData, setFormData] = useState({
    'student.first_name': '',
    'student.last_name': '',
    'student.phone': '',
    'student.email': '',
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

  //when click on save of the modal fetchParents again
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
    console.log(e.target.name)
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };  

  const handleSubmit = (values, {resetForm}) => {    
    const transformedData = transformData(formData);

    fetchData({
      url: "/api/students/parents",
      method: "POST",
      data: transformedData,
      headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
    })
    if(error){
      console.error("Error submitting form:", error);
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.statusText}`,
        severity: "error",
      })
  } else {
      console.log("Form submitted and reset successfully.");
      setSnackbarState({
        open: true,
        message: "Student created successfully.",
        severity: "success",
      })
      resetForm({ values: initialValues });
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
                value={values.student.first_name}
                name="student.first_name"
                error={!!touched.student?.first_name && !!errors.student?.first_name}
                helperText={touched.student?.first_name && errors.student?.first_name}
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
                value={values.student.last_name}
                name="student.last_name"
                error={!!touched.student?.last_name && !!errors.student?.last_name}
                helperText={touched.student?.last_name && errors.student?.last_name}
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
                value={values.student.phone}
                name="student.phone"
                error={!!touched.student?.phone && !!errors.student?.phone}
                helperText={touched.student?.phone && errors.student?.phone}
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
                value={values.student.email}
                name="student.email"
                error={!!touched.student?.email && !!errors.student?.email}
                helperText={touched.student?.email && errors.student?.email}
                sx={{ gridColumn: "span 2" }}
              />
              {!loading && values.parents.map((parent, index) => (
                <FormControl key={index} fullWidth variant="filled" sx={{ gridColumn: "span 2" }}>
                  <InputLabel color="primary" sx={{
                    color: touched.parents && errors.parents && errors.parents[index]
                      ? 'error.main'
                      : 'primary'
                  }}>Select Parent</InputLabel>
                  <Select
                    label={`Parent ${index + 1}`}
                    name={`parents[${index}].parent_id`}
                    value={parent.parent_id}
                    onChange={(e) => {
                      handleChange(e);
                      handleInput(e)
                    }}
                    onBlur={handleBlur}
                  >
                    {parentsResponse && parentsResponse.length > 0 ?
                      (parentsResponse?.map((item, index) => (
                        <MenuItem key={index} value={index}>{`${item.first_name} ${item.last_name}`} sx</MenuItem>
                      ))) : (
                        <MenuItem disabled>No parents available</MenuItem>
                      )}
                    {/* Add more parent options dynamically */}
                  </Select>
                  {touched.parents && errors.parents && errors.parents[index] && (
                    <div style={{ color: 'red', fontSize: '13px', margin: '5px 10px' }}>{errors.parents[index].parent_id}</div>
                  )}
                </FormControl>
              ))}
              <Button
                color="secondary"
                variant="contained"
                onClick={() => {
                  // Update the parents array using setFieldValue
                  setFieldValue("parents", [...values.parents, { parent_id: "" }]);
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
