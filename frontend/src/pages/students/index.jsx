import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import { Box, Button, Snackbar, useTheme } from "@mui/material";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { tokens } from "../../theme";
import useAxios from "../../hooks/useAxios";
import axios from "axios";
import ToastMessage from "../../components/ToastMessage";
import { Link, useNavigate } from "react-router-dom";

const Students = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { response, error, loading, fetchData } = useAxios();
  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  });

  //when click on save of the modal fetchParents again
  const fetchStudents = () => {
    fetchData({
      url: "/api/students",
      method: "GET", headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }
    })
  };

  useEffect(() => {
    fetchStudents();
    if(error){      
      setSnackbarState({
        open: true,
        message: `Error fetching students: ${error.status} ${error.statusText}`,
        severity: "error",
      })
  }
  }, [])

  const studentsResponse = Array.isArray(response?.data) ? response.data : [];

  async function handleDelete(id) {
    try {
      await axios.delete(
        `api/students/${id}`,{
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }}
      ).then(() => { 
      setSnackbarState({
        open: true,
        message: `Student deleted successfully`,
        severity: "success",
      })      
      })          
    } catch (error) {      
      setSnackbarState({
        open: true,
        message: `Error fetching students: ${error.message}`,
        severity: "error",
      })      
    } finally {
      fetchStudents();
    }
  }

  const navigate = useNavigate(); // Initialize navigate

  const handleCellClick = (params) => {
    if (params.field === 'id') { // Check if the clicked cell is the 'id' field
      navigate(`/student/${params.value}`, { state: { studentData: params.row } });
    }
  };
  
  const columns = [
    { field: "id", headerName: "User Id", renderCell: (params) => {
      return (
        <span style={{ color: 'inherit', cursor: 'pointer', textDecoration: 'underline' }}>
          {params.value}
        </span>
      );
    }, },
    {
      field: "full_name",
      headerName: "Name",
      flex: 1,
      cellClassName: "name-column--cell",
      renderCell: (params) => `${params.row.first_name} ${params.row.last_name}`
    },
    { field: "phone", headerName: "Phone Number", flex: 1 },
    { field: "email", headerName: "Email", flex: 1 },
    {
      field: "delete",
      headerName: "Actions",
      renderCell: (params) => (
        <Button
          variant="contained"
          color="secondary"
          onClick={() => handleDelete(params.row.id)}
         
        >
          Delete
        </Button>
      ),
    },    
  ];

  return (
    <Box m="20px">
      <Header title="Students" subtitle={"List of Students"} />
      <Box display="flex" alignContent="left" marginLeft="80%">
        <Button
          variant="contained"
          sx={{ backgroundColor: `${colors.greenAccent[600]}` }}
          href="/student/0"
        >
          Create New Student
        </Button>
      </Box>
      <Box
        m="50px 0 0 0"
        height="75vh"
        sx={{
          "& .MuiDataGrid-root": {
            border: "none",
          },
          "& .MuidataGrid-cell": {
            borderBottom: "none",
            fontSize: "16px",
          },
          "& .name-column--cell": {
            color: colors.greenAccent[300],
            fontSize: "16px",
          },
          "& .MuiDataGrid-columnHeaders": {
            backgroundColor: colors.blueAccent[700],
            fontSize: "16px",
            fontWeight: "bold",
          },
          "& .MuiDataGrid-virtualScroller": {
            backgroundColor: colors.primary[400],
          },
          "& .MuiDataGrid-footerContainer": {
            borderTop: "none",
            backgroundColor: colors.blueAccent[700],
          },
          "& .MuiDataGrid-row": {
            fontSize: "16px",
          },
          "& .MuiTablePagination-root  p": {
            fontSize: "1rem",
          },
          "& .MuiInputBase-root": {
            fontSize: "1rem",
          },
          "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
            color: `${colors.grey[100]} !important`,
          },
          //if I use checkbox
          "& .MuiCheckbox-root": {
            color: `${colors.greenAccent[200]} !important`,
          },
        }}
      >
        <DataGrid
          // checkboxSelection
          rows={studentsResponse}
          columns={columns}
          slots={{ toolbar: GridToolbar }}
          onCellClick={handleCellClick} // Pass the click handler
        />
      </Box>
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default Students;
