import { createAsyncThunk } from "@reduxjs/toolkit"
import httpClient from "../../../lib/httpClient"

export const authenticateUser = createAsyncThunk(
  "users/loginInformationConfirmation",
  async ({ email, password }) => {
    let user = null
    try {
      user = await httpClient.post("users/loginInformationConfirmation", {
        email,
        password,
      })
    } catch (e) {
      console.error(e)
    }
    console.log(user)
    return user
  }
)

export const registerUser = createAsyncThunk(
  "users/save",
  async ({ email, password, userName, securityAnswer }) => {
    let user = null
    try {
      user = await httpClient.post("users/save", {
        email,
        password,
        userName,
        securityAnswer,
      })
    } catch (e) {
      console.error(e)
    }
    console.log(user)
    return user
  }
)
