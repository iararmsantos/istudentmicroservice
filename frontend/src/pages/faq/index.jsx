import React from 'react';
import Header from '../../components/Header';
import { Box } from '@mui/material';

const FAQ = () => {
    return (
        <Box m="20px">
            <Box display="flex" justifyContent="space-between" alignContent="center">
                <Header title="FAQ" subtitle="Welcome to iStudent Tracker" />
            </Box>
        </Box>
    )
}

export default FAQ