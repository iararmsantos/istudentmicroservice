import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import { Box, Button, Snackbar, useTheme } from "@mui/material";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { tokens } from "../../theme";

import axios from "axios";
import ToastMessage from "../../components/ToastMessage";
import { useNavigate } from "react-router-dom";

const Students = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  });

  const [pageState, setPageState] = useState({
    isLoading: false,
    data: [],
    total: 0,
    page: 1,
    pageSize: 25
  });

  const makeRequest = ({ url, method, data, headers, params }) => {
    return new Promise((resolve, reject) => {
      axios({ url, method, data, headers, params })
        .then(response => resolve(response.data))
        .catch(error => reject(error));
    });
  };                    

  useEffect(() => {
    const fetchData = async () => {
      setPageState((old) => ({
        ...old,
        isLoading: true,
      }));
  
      try {
        const response = await makeRequest({
          url: `/api/students`,
          method: "GET",
          headers: { Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
          params: {
            page: pageState.page,
            limit: pageState.pageSize,
            direction: "asc",
          },
        });
  
        setPageState((old) => ({
          ...old,
          isLoading: false,
          data: response._embedded.studentVOList,
          total: response.page.totalElements,
        }));
      } catch (error) {
        console.error("Error fetching data:", error);
        setPageState((old) => ({
          ...old,
          isLoading: false,
        }));
      }
    };
  
    fetchData();
  }, [pageState.page, pageState.pageSize]);

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
      // loadStudents();
    }
  }

  const navigate = useNavigate(); // Initialize navigate

  const handleCellClick = (params) => {
    console.log(params)
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
          rows={pageState.data}
          rowCount={pageState.total}
          loading={pageState.isLoading}          
          pagination
          page={pageState.page - 1}
          pageSize={pageState.pageSize}
          rowsPerPageOptions={[25, 50, 100]} 
          paginationMode="server"
          onPaginationModelChange={(newModel) => {
            console.log("Pagination Model Changed:", newModel);
            setPageState((old) => ({
              ...old,
              page: newModel.page + 1, // Convert to one-based index for backend
              pageSize: newModel.pageSize,
            }));
          }}
          onPageSizeChange={(newPageSize) => setPageState(old => ({...old, pageSize: newPageSize}))}
          columns={columns}
          onCellClick={handleCellClick}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: 25, // Default to 10 rows per page
                page: 0, // Start from first page
              },
            },
          }}
          slots={{ toolbar: GridToolbar }}
        />
      </Box>
      <ToastMessage message={snackbarState.message} isOpen={snackbarState.open} severity={snackbarState.severity} onClose={() => setSnackbarState(prev => ({...prev, open:false}))}/>
    </Box>
  );
};

export default Students;
