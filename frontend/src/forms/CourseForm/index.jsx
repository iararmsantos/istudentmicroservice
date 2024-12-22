import { Box, Button, useMediaQuery } from '@mui/material';
import React from 'react'
import { Field, Form } from 'react-final-form';
import TextInputAdapter from '../../components/TextInputAdapter';
import arrayMutators from 'final-form-arrays';
import { required, validYear } from '../../components/validation';
import SelectAdapter from '../../components/SelectAdapter';
import _ from 'lodash';
import { Section } from '../../data/Section.ts'

const CourseForm = ({ onSubmit, initialValues, isEdit, teachersOptions }) => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const sectionOptions = Object.entries(Section).map(([key, value]) => ({
    value: key, // Send the backend-compatible enum value
    label: value, // Display the user-friendly label
  }));

  return (
    <Form
      onSubmit={onSubmit}
      initialValues={initialValues}
      mutators={{ ...arrayMutators }}//Enable array mutators to add array to values
      render={renderProps => {
        const {
          handleSubmit,
        } = renderProps;
        return (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="30px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >
              <Field fullWidth
                variant="filled"
                validate={required}
                name='title' label="Title" component={TextInputAdapter} sx={{ gridColumn: "span 2" }} />
              <Field
                name='section'
                component={SelectAdapter}
                label={`Select Section`}
                options={sectionOptions}
                validate={required}
              />
              <Field fullWidth
                validate={validYear}
                variant="filled" name='year' inputProps={{ maxLength: 4 }} component={TextInputAdapter} label='Year' sx={{ gridColumn: "span 2" }} />              
              <Field
                name='teacher_id'
                component={SelectAdapter}
                label={`Select Teacher`}
                options={teachersOptions}
                validate={required}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="50px">
              <Button type="submit" color="secondary" variant="contained">
                {isEdit ? "Save Course" : "Create New Course"}
              </Button>
            </Box>
          </form>
        )
      }}
    >
    </Form>

  )
}

export default CourseForm