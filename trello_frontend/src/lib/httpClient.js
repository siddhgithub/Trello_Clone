import axios from "axios"

const httpClient = axios.create({
  baseURL: process.env.REACT_APP_SERVER_BASE_URL,
})

httpClient.interceptors.request.use((config) => {
  return config
})

httpClient.interceptors.response.use(
  (config) => config,
  (error) => Promise.reject(error.response.data)
)

export default httpClient
