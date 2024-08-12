import { FormControl, MenuItem, Select, useTheme } from "@mui/material";
import React from "react";
import { tokens } from "../../../theme";
import { Box, Button, TextField } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../Header";
import InputLabel from "@mui/material/InputLabel";

const initialValues = {
  firstName: "",
  lastName: "",
  phone: "",
  email: "",
  parents: [{ parent_id: "" }],
};

const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;

const userSchema = yup.object().shape({
  firstName: yup.string().required("required"),
  lastName: yup.string().required("required"),
  phone: yup
    .string()
    .matches(phoneRegExp, "Phone number is not valid.")
    .required("required"),
  email: yup.string().email("Email is not valid.").required("required"),
  parents: yup
    .array()
    .of(
      yup.object().shape({
        parent_id: yup.number().required("Parent ID is required")
      })
    )
    .required("At least one parent must be selected")
});

const CreateStudent = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const isNonMobile = useMediaQuery("(min-width:600px)");

  // TODO: fetch parents to populate select

  const handleSubmit = (values) => {
    console.log("FORM")
    console.log(values);
    const data = {
      student: {
        first_name: values.firstName,
        last_name: values.lastName,
        phone: values.phone,
        email: values.email,
      },
      parents: values.parents.map(parent => ({
        parent_id: parent.parent_id,
      }))
    };
    const username = localStorage.getItem("username");
    const token = localStorage.getItem("accessToken");
  };
  return (
    <Box m="20px">
      <Header title="Create Student" subtitle="Create a New User" />
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
          setFieldValue
        }) => (
          <form onSubmit={submitForm}>
             {Object.keys(errors).length > 0 && console.log("Validation errors:", errors)}
             {/* Your form fields */}
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
                onChange={handleChange}
                value={values.firstName}
                name="firstName"
                error={!!touched.firstName && !!errors.firstName}
                helperText={touched.firstName && errors.firstName}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Last Last"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.lastName}
                name="lastName"
                error={!!touched.lastName && !!errors.lastName}
                helperText={touched.lastName && errors.lastName}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Phone"
                onBlur={handleBlur}
                onChange={handleChange}
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
                onChange={handleChange}
                value={values.email}
                name="email"
                error={!!touched.email && !!errors.email}
                helperText={touched.email && errors.email}
                sx={{ gridColumn: "span 2" }}
              />
              {/* TODO: change font color and background color to be like the input 
              colors */}
              {/* TODO: populate select with parents first and last name */}
              {values.parents.map((parent, index) => (
                <FormControl key={index} fullWidth variant="filled" sx={{ gridColumn: "span 2" }}>
                  <InputLabel>Parent {index + 1}</InputLabel>
                  <Select
                    label={`Parent ${index + 1}`}
                    name={`parents[${index}].parent_id`}
                    value={parent.parent_id}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  >
                    <MenuItem value={1}>Parent 1</MenuItem>
                    <MenuItem value={2}>Parent 2</MenuItem>
                    {/* Add more options dynamically */}
                  </Select>
                  {touched.parents && errors.parents && errors.parents[index] && (
                    <div>{errors.parents[index].parent_id}</div>
                  )}
                </FormControl>
              ))}
              {/* TODO: call modal to create a new parent */}
              {/* TODO: Add new select to select another parent */}
              <Button
                color="secondary"
                variant="contained"
                onClick={() =>{
                  // Update the parents array using setFieldValue
                  setFieldValue("parents", [...values.parents, { parent_id: "" }]);
                }
                }
              >
                Add Another Parent
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
    </Box>
  );
};

export default CreateStudent;
