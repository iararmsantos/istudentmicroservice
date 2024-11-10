import { TextField } from '@mui/material'
import React from 'react'

const TextInputAdapter = ({ input, meta, label, ...rest }) => {
  return (
    <TextField
      {...input}
      {...rest}
      label={label}
      error={meta.touched && meta.error} // Show error styling on field if there's an error
      helperText={meta.touched && meta.error ? meta.error : ' '} // Show error message below field
      InputLabelProps={{
        style: { color: meta.touched && meta.error ? 'red' : 'inherit' } // Make label red if error
      }}
      FormHelperTextProps={{
        style: { color: 'red' } // Error message text color
      }}
    />
  )
}

// //component to merge this new field into react-final-form
// const TextInput = props => {
//   return <Field component={TextInputComponent} {...props}/>
// }
export default TextInputAdapter