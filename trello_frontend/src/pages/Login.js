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

export default function Login() {
  const [ showPassword, setShowPassword ] = useState( false )
  const navigate = useNavigate()

  const RegisterSchema = Yup.object().shape( {
    email: Yup.string()
      .email( "Email must be a valid email address" )
      .required( "Email is required" ),
    password: Yup.string().required( "Password is required" ),
  } )

  const formik = useFormik( {
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: RegisterSchema,
    onSubmit: async ( values ) => {
      const { email, password } = values
      // console.log(email, password)

      try {
        const response = await fetch(
          "http://localhost:9099/users/loginInformationConfirmation?email=" + email + "&password=" + password,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
        if ( !response.ok ) {
          alert( "Invalid email or password" );
          return
        }

        navigate( "/home" )
      } catch ( error ) {
        console.log( error )
      }
    },
  } )

  const { errors, touched, isSubmitting, handleSubmit, getFieldProps } = formik

  return (
    <Container
      maxWidth="xs"
      sx={ {
        mt: 10,

        boxShadow: "0px 2px 10px rgba(0, 0, 0, 0.1)",
        borderRadius: "8px",
        maxHeight: "auto",
        padding: "5px",
        height: "auto",
      } }
    >
      <Box sx={ { mt: 5, textAlign: "center", mb: 5 } }>
        <Stack spacing={ 5 }>
          <Box>
            <img
              src="/images/trelloLogo.jpeg"
              alt="Trello Logo"
              className="App-logo"
              style={ { width: "200px", height: "auto" } }
            />
          </Box>

          <FormikProvider value={ formik }>
            <Form autoComplete="off" noValidate onSubmit={ handleSubmit }>
              <Typography
                variant="h6"
                sx={ {
                  textAlign: "center",
                  paddingBottom: "20px",
                } }
              >
                <b>Log in to continue</b>
              </Typography>
              <Stack spacing={ 3 }>
                <TextField
                  fullWidth
                  autoComplete="username"
                  type="email"
                  label="Email address"
                  { ...getFieldProps( "email" ) }
                  error={ Boolean( touched.email && errors.email ) }
                  helperText={ touched.email && errors.email }
                />

                <TextField
                  fullWidth
                  type={ showPassword ? "text" : "password" }
                  label="Password"
                  { ...getFieldProps( "password" ) }
                  InputProps={ {
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          edge="end"
                          onClick={ () => setShowPassword( ( prev ) => !prev ) }
                        >
                          { showPassword ? (
                            <VisibilityIcon />
                          ) : (
                            <VisibilityOffIcon />
                          ) }
                        </IconButton>
                      </InputAdornment>
                    ),
                  } }
                  error={ Boolean( touched.password && errors.password ) }
                  helperText={ touched.password && errors.password }
                />

                <Typography variant="body2" sx={ { textAlign: "center" } }>
                  <Link href="./ForgotPassword" underline="hover">
                    Can't log in?
                  </Link>
                  &nbsp; &bull; &nbsp;
                  <Link href="./Register" underline="hover">
                    Create an account
                  </Link>
                </Typography>

                <LoadingButton
                  fullWidth
                  size="large"
                  type="submit"
                  variant="contained"
                  loading={ isSubmitting }
                >
                  Log in
                </LoadingButton>
              </Stack>
            </Form>
          </FormikProvider>
        </Stack>
      </Box>
    </Container>
  )
}
