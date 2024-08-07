import React, { useState } from "react";
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
import api from "../../services/api";

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
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = {
      username,
      password,
    };
    console.log(data);

    try {
      const response = await api.post("auth/signin", data);
      localStorage.setItem("username", username);
      localStorage.setItem("accessToken", response.data.token);

      navigate("/dashboard");
    } catch (err) {
      alert("Login failed. Try again!");
    }
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
                  id="username"
                  label="Username"
                  name="username"
                  autoComplete="username"
                  autoFocus
                  onChange={(e) => setUsername(e.target.value)}
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
                  onChange={(e) => setPassword(e.target.value)}
                />
                <FormControlLabel
                  control={<Checkbox value="remember" color="primary" />}
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
                    <Link href="#" variant="body2" color={colors.grey[100]}>
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
