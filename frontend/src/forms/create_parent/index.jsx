import { useTheme } from "@mui/material";
import React, {  useState } from "react";
import { tokens } from "../../theme";
import { Box, Button, TextField } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import axios from "axios";
import ToastMessage from "../../components/ToastMessage";

const initialValues = {
  first_name: "",
  last_name: "",
  phone: "",
  email: "",
};

const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;

const userSchema = yup.object().shape({
  first_name: yup.string().required("required"),
  last_name: yup.string().required("required"),
  phone: yup
    .string()
    .matches(phoneRegExp, "Phone number is not valid.")
    .required("required"),
  email: yup.string().email("Email is not valid.").required("required"),
});

const CreateParent = ({onSave}) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [formData, setFormData] = useState({
    first_name: '',
    last_name: '',
    phone: '',
    email: '',
});
const [snackbarState, setSnackbarState] = useState({
  open: false,
  message: '',
  severity: 'info', //success, info, warning, error
})

  const fetchData = ({ url, method, data, headers }) => {
    return new Promise((resolve, reject) => {
      axios({ url, method, data, headers })
        .then(response => resolve(response.data))
        .catch(error => reject(error));
    });
  };

  const createParent = async (formData) => {
    try {
      const response = await fetchData({
        url: "/api/parents",
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

  const handleInput = (e) => {
    setFormData({
        ...formData,
        [e.target.name]: e.target.value,
    });
};

const handleSubmit = (values, { resetForm }) => {
  createParent(values)
    .then(() => {
      setSnackbarState({
        open: true,
        message: "Parent created successfully.",
        severity: "success",
      });
      console.log("Form submitted and reset successfully.");
      resetForm();
      onSave(); // Close the popup only on successful save
    })
    .catch(error => {
      setSnackbarState({
        open: true,
        message: `Error submitting form: ${error.message}`,
        severity: "error",
      });
      console.error("Error submitting form:", error.message);
      // Optionally, display an error message to the user
    });
};

  return (
    <Box m="20px">      
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
          resetForm
        }) => {
            const handleReset = () => {
                resetForm();
            };
            
            return (
          <form onSubmit={submitForm}>
             {Object.keys(errors).length > 0 && console.log("Validation errors:", errors)}             
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
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
            <Button type="reset" color="secondary" variant="outline" sx={{mr: "20px"}} onClick={handleReset}>
                Cancel
              </Button>
              <Button type="submit" color="secondary" variant="contained" >
                Create New Parent
              </Button>
            </Box>
          </form>
        )}}
      </Formik>
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default CreateParent;
