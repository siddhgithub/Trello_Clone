import * as Yup from "yup"
import { useState } from "react"
import { Form, FormikProvider, useFormik } from "formik"
import LoadingButton from "@mui/lab/LoadingButton"
import {
  Container,
  Stack,
  TextField,
  Box,
  Typography,
  InputAdornment,
  IconButton,
  Link,
} from "@mui/material"
import VisibilityIcon from "@mui/icons-material/Visibility"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import { useNavigate } from "react-router-dom"
import { useDispatch } from "react-redux"
import { toast } from "react-toastify"
import { registerUser } from "../store/slices/user/UserThunk"

export default function Register() {
  const [showPassword, setShowPassword] = useState(false)
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const RegisterSchema = Yup.object().shape({
    userName: Yup.string()
      .min(2, "Too Short!")
      .max(50, "Too Long!")
      .required("First name required"),
    email: Yup.string()
      .email("Email must be a valid email address")
      .required("Email is required"),
    password: Yup.string().required("Password is required"),
    securityAnswer: Yup.string().required("Security Answer required"),
  })

  const formik = useFormik({
    initialValues: {
      userName: "",
      email: "",
      password: "",
      securityAnswer: "",
    },
    validationSchema: RegisterSchema,
    onSubmit: async (values, { setSubmitting, resetForm }) => {
      try {
        const response = await fetch("http://localhost:9099/users/save", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            userEmailAddress: values.email,
            userPassword: values.password,
            userName: values.userName,
            securityAnswer: values.securityAnswer,
          }),
        })

        if (response.ok) {
          // Signup successful
          console.log("Signup successful!")
          alert("Signup successful")
          navigate("/login")
          // Perform any additional actions or redirect the user
        } else if (response.status === 400) {
          // Signup failed due to validation error
          console.log("Invalid Password Format or Passwords do not match")
          alert("Invalid Password Format or Passwords do not match")
        } else {
          // Signup failed
          console.error("Signup failed!")
          alert("Signup failed!")
        }
      } catch (error) {
        console.error("Error:", error)
      } finally {
        setSubmitting(false)
        resetForm()
      }
    },
  })

  const { errors, touched, isSubmitting, handleSubmit, getFieldProps } = formik

  return (
    <Container
      maxWidth="xs"
      sx={{
        mt: 10,
        boxShadow: "0px 2px 10px rgba(0, 0, 0, 0.1)",
        borderRadius: "8px",
        maxHeight: "auto",
        padding: "5px",
        height: "auto",
      }}
    >
      <Box sx={{ mt: 5, textAlign: "center", mb: 5 }}>
        <Stack spacing={5}>
          <Box>
            <img
              src="/images/trelloLogo.jpeg"
              alt="Trello Logo"
              className="App-logo"
              style={{ width: "200px", height: "auto" }}
            />
          </Box>

          <FormikProvider value={formik}>
            <Form autoComplete="off" noValidate onSubmit={handleSubmit}>
              <Typography
                variant="h6"
                sx={{
                  textAlign: "center",
                  paddingBottom: "20px",
                }}
              >
                <b>Sign up to continue</b>
              </Typography>
              <Stack spacing={3}>
                <TextField
                  fullWidth
                  label="User name"
                  {...getFieldProps("userName")}
                  error={Boolean(touched.userName && errors.userName)}
                  helperText={touched.userName && errors.userName}
                />

                <TextField
                  fullWidth
                  autoComplete="username"
                  type="email"
                  label="Email address"
                  {...getFieldProps("email")}
                  error={Boolean(touched.email && errors.email)}
                  helperText={touched.email && errors.email}
                />

                <TextField
                  fullWidth
                  type={showPassword ? "text" : "password"}
                  label="Password"
                  {...getFieldProps("password")}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          edge="end"
                          onClick={() => setShowPassword((prev) => !prev)}
                        >
                          {showPassword ? (
                            <VisibilityIcon />
                          ) : (
                            <VisibilityOffIcon />
                          )}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                  error={Boolean(touched.password && errors.password)}
                  helperText={touched.password && errors.password}
                />

                <TextField
                  fullWidth
                  label="Security Answer"
                  {...getFieldProps("securityAnswer")}
                  error={Boolean(
                    touched.securityAnswer && errors.securityAnswer
                  )}
                  helperText={touched.securityAnswer && errors.securityAnswer}
                />

                <Typography variant="body2" sx={{ textAlign: "center" }}>
                  Already have an account?{" "}
                  <Link href="./Login" underline="hover">
                    Log in
                  </Link>
                </Typography>

                <LoadingButton
                  fullWidth
                  size="large"
                  type="submit"
                  variant="contained"
                  loading={isSubmitting}
                >
                  Register
                </LoadingButton>
              </Stack>
            </Form>
          </FormikProvider>
        </Stack>
      </Box>
    </Container>
  )
}
