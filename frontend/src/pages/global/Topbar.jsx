import React, { useContext } from 'react';
import { Box, IconButton, useTheme } from '@mui/material';
import InputBase from "@mui/material/InputBase";
import { ColorModeContext, tokens } from '../../theme';
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import NotificationsOutlinedIcon from '@mui/icons-material/NotificationsOutlined';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import SearchIcon from '@mui/icons-material/Search';

const TopBar = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const colorMode = useContext(ColorModeContext);
    return (
        <Box display="flex" justifyContent="space-between" p={2}>
            {/* SEARCH BAR */}
            <Box
                display="flex"
                backgroundColor={colors.primary[400]}
                borderRadius="3px"
                width="300px"
            >
                <InputBase sx={{ ml: 2, flex: 1 }} placeholder='Search' />
                <IconButton type='button' sx={{ p: 1 }}>
                    <SearchIcon />
                </IconButton>
            </Box>

            {/* ICONS */}
            <Box display="flex">
                <IconButton size='large' onClick={() => {
                    return colorMode.toggleColorMode()
                }}>
                    {theme.palette.mode === 'dark' ? (
                        <DarkModeOutlinedIcon style={{ fontSize: '1.5rem' }} />
                    ) : (
                        <LightModeOutlinedIcon style={{ fontSize: '1.5rem' }} />
                    )}
                </IconButton>
                <IconButton>
                    <NotificationsOutlinedIcon style={{ fontSize: '1.5rem' }} />
                </IconButton>
                <IconButton>
                    <SettingsOutlinedIcon style={{ fontSize: '1.5rem' }} />
                </IconButton>
                <IconButton>
                    <PersonOutlinedIcon style={{ fontSize: '1.5rem' }} />
                </IconButton>
            </Box>
        </Box>
    );
};

export default TopBar;