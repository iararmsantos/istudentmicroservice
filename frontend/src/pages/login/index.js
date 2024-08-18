import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import graduation from "../../images/graduation-cap.png";
import { Typography, Box, useTheme } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { tokens } from "../../theme";
import { inputLabelClasses } from "@mui/material/InputLabel";
import useAxios from "../../hooks/useAxios";

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
const Login = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  const [formData, setFormData] = useState({
    email: '',
    password: '',
});
  const navigate = useNavigate();
  const { response, error, loading, fetchData} = useAxios();

  //for reference
  const fetchUsers = () => {
    fetchData({url: "/api/users", method: "GET"});
  };

  //post request
  const login = () => {
    fetchData({
      url: "/login",
      method: "POST",
      data: formData
    })
  }

  const updateUser = () => {
    fetchData({
      // url: `/api/users/${userId}`
      url: `api/users`,
      method: "PUT",
      data: formData
    })
  }

  const deleteUser = () => {
    fetchData({
      // url: `/api/users/${userId}`
      url: `api/users`,
      method: "DELETE"
    })
  }

  const handleChange = (e) => {
    setFormData({
        ...formData,
        [e.target.name]: e.target.value,
    });
};

useEffect(() => {
  if (response) {
      localStorage.setItem("username", formData.email);
      localStorage.setItem("accessToken", response.headers.token);
      navigate("/");
  }
  if (error) {
      console.error(error);
      alert("Login failed. Try again!");
  }
}, [response, error]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    login();
 

    // const data = {
    //   email,
    //   password,
    // };
    // console.log({data})

    // try {
    //   const response = await axios.post("/login", data);      
    //   console.log(response)
    //   localStorage.setItem("username", email);
    //   localStorage.setItem("accessToken", response.headers.token);

    //   navigate("/");
    // } catch (err) {
    //   //TODO change this to toast message
    //   console.log(err)
    //   alert("Login failed. Try again again!");
    // }
  };
  
  return (
    <Card sx={{ m: "auto", width: 1000, height: "75%" }} alignitems="center">
      <CardContent sx={{ mt: 6 }}>
        <Grid container component="main" sx={{ height: "50vh" }}>
          <Grid
            item
            xs={false}
            sm={4}
            md={7}
            sx={{
              backgroundImage: `url(${graduation})`,
              backgroundRepeat: "no-repeat",
              //   backgroundColor: (t) =>
              //     t.palette.mode === "light"
              //       ? t.palette.grey[50]
              //       : t.palette.grey[900],
              backgroundSize: `500px 400px`,
              backgroundPosition: "center",
              width: "100%",
              height: "100%",
              mt: 4,
            }}
          />
          <Grid
            item
            xs={12}
            sm={8}
            md={5}
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
                Sign in
              </Typography>
              <Box
                component="form"
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 1 }}
              >
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Username"
                  name="email"
                  autoComplete="email"
                  autoFocus
                  onChange={handleChange}
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
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                  onChange={handleChange}
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
                <FormControlLabel
                  control={<Checkbox value="remember" color='secondary' />}
                  label="Remember me"
                  
                />
                <Button
                  color="secondary"
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                >
                  Sign In
                </Button>
                <Grid container>
                  <Grid item xs>
                    <Link href="#" variant="body2" color={colors.grey[100]}>
                      Forgot password?
                    </Link>
                  </Grid>
                  <Grid item>
                    <Link href="signup" variant="body2" color={colors.grey[100]}>
                      {"Don't have an account? Sign Up"}
                    </Link>
                  </Grid>
                </Grid>
                <Copyright sx={{ mt: 5 }} />
              </Box>
            </Box>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default Login;

// https://www.youtube.com/watch?v=lD-I3aGv1xs

// https://www.youtube.com/watch?v=MWvnmyLRUik - microservices

// file:///C:/Users/iara.santos/Downloads/1718+Reposit%C3%B3rios+do+Projeto+no+Github.html
