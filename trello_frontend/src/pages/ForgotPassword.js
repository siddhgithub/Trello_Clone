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

export default function ForgotPassword() {
  const [passwordUpdated, setPasswordUpdated] = useState(false)
  const [showPassword, setShowPassword] = useState(false)
  const navigate = useNavigate()

  async function forgotPasswordUserHandler(
    email,
    securityQuestion,
    newPassword
  ) {
    try {
      const response = await fetch(
        "http://localhost:9099/users/passwordReset?email=" + email + "&securityAnswer=" + securityQuestion + "&newPassword=" + newPassword,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      )

      if (!response.ok) {
        alert("Invalid security Answer or email");
        return
      }
      
      navigate("/login")
    } catch (error) {
      console.log(error)
    }
  }

  const ForgotPasswordSchema = Yup.object().shape({
    email: Yup.string()
      .email("Email must be a valid email address")
      .required("Email is required"),
    securityQuestion: Yup.string().required("Security question is required"),
    newPassword: Yup.string().required("New password is required"),
  })

  const formik = useFormik({
    initialValues: {
      email: "",
      securityQuestion: "",
      newPassword: "",
    },
    validationSchema: ForgotPasswordSchema,
    onSubmit: async (values) => {
      const { email, securityQuestion, newPassword } = values
      forgotPasswordUserHandler(email, securityQuestion, newPassword)
      setPasswordUpdated(true)
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
                <b>Forgot Password</b>
              </Typography>
              {!passwordUpdated ? (
                <Stack spacing={3}>
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
                    label="Security Answer"
                    {...getFieldProps("securityQuestion")}
                    error={Boolean(
                      touched.securityQuestion && errors.securityQuestion
                    )}
                    helperText={
                      touched.securityQuestion && errors.securityQuestion
                    }
                  />

                  <TextField
                    fullWidth
                    type={showPassword ? "text" : "password"}
                    label="New Password"
                    {...getFieldProps("newPassword")}
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

                  <LoadingButton
                    fullWidth
                    size="large"
                    type="submit"
                    variant="contained"
                    loading={isSubmitting}
                  >
                    Reset Password
                  </LoadingButton>
                </Stack>
              ) : (
                <Box textAlign="center">
                  <Typography variant="h5">Password updated!</Typography>
                  <Typography>
                    Your password has been successfully updated.
                  </Typography>
                  <br></br>
                  <Link href="./Login" underline="hover">
                    Return to log in
                  </Link>
                </Box>
              )}
            </Form>
          </FormikProvider>
        </Stack>
      </Box>
    </Container>
  )
}
