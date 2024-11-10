import { Box, Button, useMediaQuery } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { Field, Form } from 'react-final-form';
import TextInputAdapter from '../../components/TextInputAdapter';
import SelectAdapter from '../../components/SelectAdapter';
import useAxios from '../../hooks/useAxios';
import {FieldArray} from 'react-final-form-arrays';
import arrayMutators from 'final-form-arrays';
import { required, validEmail, validPhone } from '../../components/validation';

const initialValues = {  
  first_name: "",
  last_name: "",
  phone: "",
  email: "",

  studentParents: [
  { parent_id: "" },   
],
};

const StudentForm = ({onSubmit, openCreateParents, parentOptions}) => {
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
        const studentParents = getState().values.studentParents || [];
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
              <FieldArray name="studentParents">
            {({ fields }) =>
              fields.map((name, index) => (
                <div key={name}>
                  <Field
                    name={`${name}.parent_id`}
                    component={SelectAdapter}
                    label={`Select Parent ${index + 1}`}
                    options={parentOptions}
                    validate={required}
                  />
                </div>
              ))
            }            
          </FieldArray>   
          <Box sx={{ gridColumn: "span 4", justifyContent: 'center' }} display="flex" gap="10px" mt="20px">
          {studentParents.length > 1 && (
          <Button
            color="secondary"
            variant="contained"
            onClick={() => remove('studentParents', studentParents.length - 1)}
            style={{ marginLeft: '10px' }}
          >
            Remove Parent
          </Button>
        )}
              <Button
                color="secondary"
                variant="contained"
                onClick={() => push('studentParents', { parent_id: "" })}
              >
                Add Another Parent
              </Button>
              <Button
                color="secondary"
                variant="contained"
                onClick={
                  openCreateParents
                }
              >
                Create Parent
              </Button>
          </Box>
            </Box>
            <Box display="flex" justifyContent="end" mt="50px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Student
              </Button>
            </Box>
          </form>
        )
      }}
    >
    </Form>

  )
}

export default StudentForm