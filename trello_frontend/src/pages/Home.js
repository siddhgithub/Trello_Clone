import React from "react"
import { useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"
import {
  Container,
  Typography,
  Stack,
  AppBar,
  Toolbar,
  IconButton,
  Box,
  Button,
} from "@mui/material"
import MenuIcon from "@mui/icons-material/Menu"

// export default function Home() {
//   const navigate = useNavigate()
//   const user = useSelector((state) => state.user.data)

//   return (
//     <>
//       <AppBar position="static">
//         <Toolbar>
//           <IconButton
//             size="large"
//             edge="start"
//             color="inherit"
//             aria-label="menu"
//             sx={{ mr: 2 }}
//           >
//             <MenuIcon />
//           </IconButton>
//           <Typography variant="h6" component="div">
//             Trello Clone
//           </Typography>
//           <Box sx={{ flexGrow: 1, display: "flex", ml: 4 }}>
//             <Button sx={{ color: "white", fontWeight: "bold" }}>
//               Workspace
//             </Button>
//             <Button sx={{ color: "white", fontWeight: "bold" }}>Profile</Button>
//           </Box>
//         </Toolbar>
//       </AppBar>

//       <Container>
//         <Typography variant="h2">Home</Typography>

//         {/* <Stack>
//           <Typography>First Name: {user["firstName"]}</Typography>
//           <Typography>Last Name: {user["lastName"]}</Typography>
//           <Typography>Email: {user["email"]}</Typography>
//         </Stack> */}
//       </Container>
//     </>
//   )
// }
function Home() {
  const navigate = useNavigate()
  const user = useSelector( ( state ) => state.user.data )

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={ { mr: 2 } }
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div">
            Trello Clone
          </Typography>
          <Box sx={ { flexGrow: 1, display: "flex", ml: 4 } }>
            <Button sx={ { color: "white", fontWeight: "bold" } }>
              Workspace
            </Button>
            <Button sx={ { color: "white", fontWeight: "bold" } }>Profile</Button>
          </Box>
        </Toolbar>
      </AppBar>

      <Container>
        <Typography variant="h2">Home</Typography>

        {/* <Stack>
          <Typography>First Name: {user["firstName"]}</Typography>
          <Typography>Last Name: {user["lastName"]}</Typography>
          <Typography>Email: {user["email"]}</Typography>
        </Stack> */}
      </Container>
    </>
  )
}

export default Home;