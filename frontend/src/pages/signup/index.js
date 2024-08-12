import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography, Box, useTheme, Select, FormControl, MenuItem } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { tokens } from "../../theme";
import InputLabel, { inputLabelClasses } from "@mui/material/InputLabel";
import axios from 'axios';

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}
const SignUp = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = {
      firstName,
      lastName,
      phone,
      email,
      role,
      password,
    };
    console.log(data);

    try {
      const response = await axios.post('/api/users', data)
      console.log(response)
      navigate("/login");
    } catch (err) {
      alert("Login failed. Try again!");
    }
  };
  return (
    <Card sx={{ m: '40px', width: 1000, height: "75%" }} alignitems="center">
      <CardContent >
        <Grid container component="main" sx={{ height: "50vh" }} direction='column'>
          <Grid
            item
            sm={8}
            component={Paper}
            elevation={6}
            square
          >
            <Box
              sx={{
                my: 8,
                mx: 4,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign Up
              </Typography>
              <Box
                component="form"
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 1 }}
              >
                <Grid container spacing={2}>
                  <Grid item xs={6}>
                    <TextField
                      margin="normal"
                      required
                      fullWidth
                      id="firstname"
                      label="First Name"
                      name="firstname"
                      autoComplete="firstname"
                      autoFocus
                      onChange={(e) => setFirstName(e.target.value)}
                      InputLabelProps={{
                        sx: {
                          // set the color of the label when not shrinked
                          color: "white",
                          [`&.${inputLabelClasses.shrink}`]: {
                            // set the color of the label when shrinked (usually when the TextField is focused)
                            color: "white"
                          }
                        }
                      }}
                    />
                    <TextField
                      margin="normal"
                      required
                      fullWidth
                      id="lastname"
                      label="Last Name"
                      name="lastname"
                      autoComplete="lastname"
                      autoFocus
                      onChange={(e) => setLastName(e.target.value)}
                      InputLabelProps={{
                        sx: {
                          // set the color of the label when not shrinked
                          color: "white",
                          [`&.${inputLabelClasses.shrink}`]: {
                            // set the color of the label when shrinked (usually when the TextField is focused)
                            color: "white"
                          }
                        }
                      }}
                    />
                    <TextField
                      margin="normal"
                      required
                      fullWidth
                      name="phone"
                      label="Phone"
                      type="phone"
                      id="phone"
                      autoComplete="phone"
                      onChange={(e) => setPhone(e.target.value)}
                      InputLabelProps={{
                        sx: {
                          // set the color of the label when not shrinked
                          color: "white",
                          [`&.${inputLabelClasses.shrink}`]: {
                            // set the color of the label when shrinked (usually when the TextField is focused)
                            color: "white"
                          }
                        }
                      }}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <TextField
                      margin="normal"
                      required
                      fullWidth
                      id="email"
                      label="Email"
                      name="email"
                      autoComplete="email"
                      autoFocus
                      onChange={(e) => setEmail(e.target.value)}
                      InputLabelProps={{
                        sx: {
                          // set the color of the label when not shrinked
                          color: "white",
                          [`&.${inputLabelClasses.shrink}`]: {
                            // set the color of the label when shrinked (usually when the TextField is focused)
                            color: "white"
                          }
                        }
                      }}
                    />                    
                    <FormControl sx={{ m: 1, minWidth: 120 }}>
                      <InputLabel sx={{
                        // set the color of the label when not shrinked
                        color: "white",
                        [`&.${inputLabelClasses.shrink}`]: {
                          // set the color of the label when shrinked (usually when the TextField is focused)
                          color: "white"
                        }}}>Role</InputLabel>
                      <Select labelId="role" id="role" value={role} label="Role" onChange={(event) => setRole(event.target.value)}>
                        <MenuItem value={'STUDENT'}>Student</MenuItem>
                        <MenuItem value={'PARENT'}>Parent</MenuItem>
                        <MenuItem value={'TEACHER'}>Teacher</MenuItem>
                      </Select>
                    </FormControl>
                    <TextField
                      margin="normal"
                      required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      autoComplete="current-password"
                      onChange={(e) => setPassword(e.target.value)}
                      InputLabelProps={{
                        sx: {
                          // set the color of the label when not shrinked
                          color: "white",
                          [`&.${inputLabelClasses.shrink}`]: {
                            // set the color of the label when shrinked (usually when the TextField is focused)
                            color: "white"
                          }
                        }
                      }}
                    />
                  </Grid>
                </Grid>
                <Button
                  color="secondary"
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                >
                  Sign Up
                </Button>

                <Copyright sx={{ mt: 5 }} />
              </Box>
            </Box>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default SignUp;

// https://www.youtube.com/watch?v=lD-I3aGv1xs

// https://www.youtube.com/watch?v=MWvnmyLRUik - microservices

// file:///C:/Users/iara.santos/Downloads/1718+Reposit%C3%B3rios+do+Projeto+no+Github.html
