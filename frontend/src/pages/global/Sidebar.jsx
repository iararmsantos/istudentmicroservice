import React, { useState } from 'react';
import { ProSidebar, Menu, MenuItem } from 'react-pro-sidebar';
import 'react-pro-sidebar/dist/css/styles.css';
import { Box, IconButton, Typography, useTheme } from '@mui/material';
import { Link } from 'react-router-dom';
import { tokens } from '../../theme';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import CalendarTodayOutlinedIcon from '@mui/icons-material/CalendarTodayOutlined';
import HelpOutlinedIcon from '@mui/icons-material/HelpOutlined';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import SchoolOutlinedIcon from '@mui/icons-material/SchoolOutlined';//student
import HistoryEduOutlinedIcon from '@mui/icons-material/HistoryEduOutlined';//teacher
import SquareFootOutlinedIcon from '@mui/icons-material/SquareFootOutlined';//course
import GradeOutlinedIcon from '@mui/icons-material/GradeOutlined';//grade
import PageViewOutlinedIcon from '@mui/icons-material/PageviewOutlined';//view
import ManageAccountsOutlinedIcon from '@mui/icons-material/ManageAccountsOutlined';//user account

const Item = ({ title, to, icon, selected, setSelected }) => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    return (
        <MenuItem
            active={selected === title}
            style={{ color: colors.grey[100] }}
            onClick={() => setSelected(title)}
            icon={icon}
        >
            <Typography variant='h4'>{title}</Typography>
            <Link to={to} />
        </MenuItem>
    )
}
const Sidebar = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const [isCollapsed, setIsCollapsed] = useState(false);
    const [selected, setSelected] = useState("Dashboard");

    return (
        <Box
            sx={{
                "& .pro-sidebar-inner": {
                    background: `${colors.primary[400]} !important`
                },
                "& .pro-icon-wrapper": {
                    backgroundColor: "transparent !important"
                },
                "& .pro-inner-item": {
                    padding: "5px 35px 20px 20px !important"
                },
                "& .pro-inner-item-hover": {
                    color: "#868dfb !important"
                },
                "& .pro-menu-item.active": {
                    color: "#6870fa !important"
                }
            }}
        >
            <ProSidebar collapsed={isCollapsed}>
                <Menu iconShape='square'>
                    {/* LOGO AND MENU ICON */}
                    <MenuItem
                        onClick={() => setIsCollapsed(!isCollapsed)}
                        icon={isCollapsed ? <MenuOutlinedIcon /> : undefined}
                        style={{
                            margin: "10px 0 20px 0",
                            color: colors.grey[100]
                        }}
                    >
                        {!isCollapsed && (
                            <Box
                                display="flex"
                                justifyContent="space-between"
                                alignItems="center"
                                ml="15px"
                            >
                                <Typography variant='h3' color={colors.grey[100]}>
                                    iStudent Tracker
                                </Typography>
                                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                                    <MenuOutlinedIcon />
                                </IconButton>
                            </Box>
                        )}
                    </MenuItem>
                    {/* USER */}
                    {!isCollapsed && (
                        <Box mb="25px">
                            <Box display="flex" justifyContent="center" alignItems="center">
                                {/* //useraccount management */}
                                {/*TODO: or I can get first letter of first name and last name and make a typography */}
                                <IconButton>
                                    <ManageAccountsOutlinedIcon style={{ fontSize: '3rem' }} />
                                    <Link to="/account" />
                                </IconButton>
                                {/* <img
                                    alt='profile-user'
                                    width="100px"
                                    height="100px"
                                    src={'../../assets/user.png'}
                                    style={{ cursor: "pointer", borderRadius: "50%" }}
                                /> */}
                            </Box>
                            <Box textAlign="center">
                                <Typography
                                    variant='h2'
                                    color={colors.grey[100]}
                                    fontWeight="bold"
                                    sx={{ m: "10px 0 0 0" }}
                                >
                                    {/*TODO: Username and lastname of user logged */}
                                    Iara Santos
                                </Typography>
                                <Typography
                                    variant='h5'
                                    color={colors.greenAccent[500]}
                                >
                                    {/*TODO: role of the user logged */}
                                    Developer
                                </Typography>
                            </Box>
                        </Box>
                    )}
                    {/* MENU ITEMS */}
                    <Box paddingLeft={isCollapsed ? undefined : "10%"}>
                        <Item
                            title="Dashboard"
                            to="/"
                            icon={<HomeOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Typography
                            variant='h5'
                            color={colors.grey[300]}
                            sx={{ m: "15px 0 15px 20px" }}
                        >Data</Typography>
                        <Item
                            title="Student"
                            to="/students"
                            icon={<SchoolOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Item
                            title="Teachers"
                            to="/teachers"
                            icon={<HistoryEduOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Item
                            title="Courses"
                            to="/courses"
                            icon={<SquareFootOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Item
                            title="Grades"
                            to="/grades"
                            icon={<GradeOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Typography
                            variant='h5'
                            color={colors.grey[300]}
                            sx={{ m: "15px 0 15px 20px" }}
                        >Display</Typography>
                        <Item
                            title="View"
                            to="/view"
                            icon={<PageViewOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Item
                            title="Calendar"
                            to="/calendar"
                            icon={<CalendarTodayOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                        <Item
                            title="FAQ Page"
                            to="/faq"
                            icon={<HelpOutlinedIcon />}
                            selected={selected}
                            setSelected={setSelected}
                        />
                    </Box>
                </Menu>
            </ProSidebar>
        </Box>
    )
}

export default Sidebar;