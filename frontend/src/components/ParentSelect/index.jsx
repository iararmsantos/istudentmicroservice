import React from 'react';
import { FormControl, InputLabel, Select, MenuItem } from '@mui/material';

const ParentSelect = ({
  index,
  touched,
  errors,
  parent,
  options,
  handleChange,
  handleBlur,
  handleInput,
}) => {
  return (
    <FormControl key={index} fullWidth variant="filled" sx={{ gridColumn: "span 2" }}>
      <InputLabel color="primary" sx={{
        color: touched.studentParents && errors.studentParents && errors.studentParents[index]
          ? 'error.main'
          : 'primary'
      }}>Select Parent</InputLabel>
      <Select
        label={`Parent ${index + 1}`}
        name={`studentParents[${index}].parent_id`}
        value={parent.parent_id}
        onChange={(e) => {
          handleChange(e);
          handleInput(e)
        }}
        onBlur={handleBlur}
      >
        {options && options.length > 0 ?
          (options?.map((item, index) => (
            <MenuItem key={item.id} value={index}>{`${item.first_name} ${item.last_name}`} sx</MenuItem>
          ))) : (
            <MenuItem disabled>No parents available</MenuItem>
          )}
        {/* Add more parent options dynamically */}
      </Select>
      {touched.parents && errors.parents && errors.parents[index] && (
        <div style={{ color: 'red', fontSize: '13px', margin: '5px 10px' }}>{errors.parents[index].parent_id}</div>
      )}
    </FormControl>
  );
};

export default ParentSelect;
