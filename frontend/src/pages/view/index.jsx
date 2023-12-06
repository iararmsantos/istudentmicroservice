import React from 'react';
import Header from '../../components/Header';
import { Box } from '@mui/material';

const View = () => {
    return (
        <Box m="20px">
            <Box display="flex" justifyContent="space-between" alignContent="center">
                <Header title="VIEW" subtitle="Welcome to iStudent Tracker" />
            </Box>
        </Box>
    )
}

export default View