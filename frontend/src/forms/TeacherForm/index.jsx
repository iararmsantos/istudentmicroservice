import { Box, Button, useMediaQuery } from '@mui/material';
import React from 'react'
import { Field, Form } from 'react-final-form';
import TextInputAdapter from '../../components/TextInputAdapter';
import arrayMutators from 'final-form-arrays';
import { required, validEmail, validPhone } from '../../components/validation';

const TeacherForm = ({onSubmit, initialValues, isEdit}) => {
  const isNonMobile = useMediaQuery("(min-width:600px)");

  return (
    <Form
      onSubmit={onSubmit}
      initialValues={initialValues}
      mutators={{...arrayMutators}}//Enable array mutators to add array to values
      render={renderProps => {
        const {
          handleSubmit, form: {mutators: {push, remove},
        getState
      },
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
                name='first_name' label="First Name" component={TextInputAdapter} sx={{ gridColumn: "span 2" }} />
              <Field name='last_name'
              validate={required}
                fullWidth
                label="Last Name"
                variant="filled"
                component={TextInputAdapter} sx={{ gridColumn: "span 2" }} />
              <Field fullWidth
              validate={validPhone}
                variant="filled" name='phone' component={TextInputAdapter} label='Phone' sx={{ gridColumn: "span 2" }} />
              <Field fullWidth
              validate={validEmail}
                variant="filled" name='email' component={TextInputAdapter} label='Email' sx={{ gridColumn: "span 2" }} />   
            </Box>
            <Box display="flex" justifyContent="end" mt="50px">
              <Button type="submit" color="secondary" variant="contained">
                {isEdit ? "Save Teacher" : "Create New Teacher"}
              </Button>
            </Box>
          </form>
        )
      }}
    >
    </Form>

  )
}

export default TeacherForm