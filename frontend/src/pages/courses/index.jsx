import React from 'react';
import Header from '../../components/Header';
import { Box } from '@mui/material';

const Courses = () => {
    return (
        // Courses list table
        // button to create a new Courses - it will open the form
        <Box m="20px">
            <Box display="flex" justifyContent="space-between" alignContent="center">
                <Header title="COURSES" subtitle="Welcome to iStudent Tracker" />
            </Box>
        </Box>
    )
}

export default Courses