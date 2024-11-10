import { MenuItem, TextField } from '@mui/material'
import React from 'react'

const SelectAdapter = ({ input, meta, label, children, options, ...rest }) => (
    <TextField
      select
      {...input}
      {...rest}
      label={label}
      error={meta.error && meta.touched}
      helperText={meta.touched && meta.error ? meta.error : ''}
      variant="filled"
      fullWidth
      sx={{ gridColumn: "span 2" }}
    >
      {options.map((option) => (
      <MenuItem key={option.value} value={option.value}>
        {option.label}
      </MenuItem>
    ))}
    </TextField>
)

export default SelectAdapter