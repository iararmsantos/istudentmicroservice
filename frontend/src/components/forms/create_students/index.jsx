import { useTheme } from '@mui/material';
import React from 'react'
import { tokens } from '../../../theme';

const CreateStudent = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    return (
        <div>CreateStudent</div>
    )
}

export default CreateStudent