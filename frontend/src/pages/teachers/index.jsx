import React from 'react';
import Header from '../../components/Header';
import { Box } from '@mui/material';

const Teachers = () => {
    return (
        // teachers list table
        // button to create a new teacher - it will open the form
        <Box m="20px">
            <Box display="flex" justifyContent="space-between" alignContent="center">
                <Header title="TEACHERS" subtitle="Welcome to iStudent Tracker" />
            </Box>
        </Box>
    )
}

export default Teachers