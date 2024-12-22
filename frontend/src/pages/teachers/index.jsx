import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import { Box, Button, useTheme } from "@mui/material";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { tokens } from "../../theme";

import axios from "axios";
import ToastMessage from "../../components/ToastMessage";
import { useNavigate } from "react-router-dom";

const Teachers = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const navigate = useNavigate(); // Initialize navigate

  const [snackbarState, setSnackbarState] = useState({
    open: false,
    message: '',
    severity: 'info', //success, info, warning, error
  });

  const [pageState, setPageState] = useState({
    isLoading: false,
    data: [],
    total: 0,
    page: 0,
    pageSize: 25
  });

  const makeRequest = ({ url, method, data, headers, params }) => {
    console.log({ url, method, data, headers, params })
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
          url: `/api/teachers`,
          method: "GET",
          headers: { Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
          params: {
            page: pageState.page,
            limit: pageState.pageSize,
            direction: "asc",
          },
        });
        const teacherList = response?._embedded?.teacherVOList || [];
        const totalElements = response?.page?.totalElements || 0;
        setPageState((old) => ({
          ...old,
          isLoading: false,
          data: teacherList,
          total: totalElements,
        }));
      } catch (error) {
        console.error("Error fetching data:", error);
        setSnackbarState({
          open: true,
          message: `Error fetching teachers: ${error.message}`,
          severity: "error",
        })   
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
        `api/teachers/${id}`,{
        headers: { 'Authorization': `Bearer ${localStorage.getItem('accessToken')}` }}
      ).then(() => { 
      setSnackbarState({
        open: true,
        message: `Teacher deleted successfully`,
        severity: "success",
      })      
      })          
    } catch (error) {      
      setSnackbarState({
        open: true,
        message: `Error fetching teachers: ${error.message}`,
        severity: "error",
      })      
    } finally {
      // loadTeachers();
    }
  }  

  //TODO: change this to use redux
  const handleCellClick = (params) => {
    console.log(params)
    if (params.field === 'id') { // Check if the clicked cell is the 'id' field
      navigate(`/teacher/${params.value}`, { state: { teacherData: params.row } });
    }
  };
  
  const columns = [
    { field: "id", headerName: "Id", renderCell: (params) => {
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
        // TODO: use icon instead
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
      <Header title="Teachers" subtitle={"List of Teachers"} />
      <Box display="flex" alignContent="left" marginLeft="80%">
        <Button
          variant="contained"
          sx={{ backgroundColor: `${colors.greenAccent[600]}` }}
          href="/teacher/0"
        >
          Create New Teacher
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

export default Teachers;
