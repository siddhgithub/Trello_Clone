import { Navigate, useRoutes } from "react-router-dom"
import LogoOnlyLayout from "./layouts/LogoOnlyLayout"
import ForgotPassword from "./pages/ForgotPassword"
import Login from "./pages/Login"
import Register from "./pages/Register"
import AuthGuard from "./guard/AuthGuard"
import Home from "./pages/Home"
import WorkspaceForms from "./pages/WorkspaceForm"
import WorkspaceDetails from "./pages/WorkspaceDetails"
import 'bootstrap/dist/css/bootstrap.css';

export default function Router() {
  return useRoutes( [
    {
      path: "/",
      element: <LogoOnlyLayout />,
      children: [
        {
          index: true,
          element: <Navigate to="/login" />,
        },
        {
          path: "login",
          element: <Login />,
        },
        {
          path: "register",
          element: <Register />,
        },
        {
          path: "forgotpassword",
          element: <ForgotPassword />,
        },
      ],
    },
    {
      path: "/home",
      // element: <AuthGuard />,
      children: [
        {
          index: true,
          element: <Navigate to="/home/workspaceforms" replace />,
        },
        {
          path: "workspaceforms",
          element: <WorkspaceForms />,
        },
        {
          path: "workspace/:workspace",
          element: <WorkspaceDetails />,
        },
      ],
    },
  ] )
}


// import { Navigate, useRoutes } from "react-router-dom"
// import LogoOnlyLayout from "./layouts/LogoOnlyLayout"
// import ForgotPassword from "./pages/ForgotPassword"
// import Login from "./pages/Login"
// import Register from "./pages/Register"
// import AuthGuard from "./guard/AuthGuard"
// import Home from "./pages/Home"
// import WorkspaceForms from "./pages/WorkspaceForm"
// import WorkspaceDetails from "./pages/WorkspaceDetails"


// export default function Router() {
//   return useRoutes( [
//     {
//       path: "/",
//       element: <LogoOnlyLayout />,
//       children: [
//         {
//           index: true,
//           element: <Navigate to="/login" />,
//         },
//         {
//           path: "login",
//           element: <Login />,
//         },
//         {
//           path: "register",
//           element: <Register />,
//         },
//         {
//           path: "forgotpassword",
//           element: <ForgotPassword />,
//         },
//       ],
//     },
//     {
//       path: "/home",
//       // element: <AuthGuard />,
//       children: [
//         {
//           index: true,
//           element: <Home />,
//         },
//         // {
//         //   index: true,
//         //   element: <Navigate to="/home/workspaceforms" replace />,
//         // },
//         {
//           path: "workspaceforms",
//           element: <WorkspaceForms />,
//         },
//         {
//           path: "workspace/:workspace",
//           element: <WorkspaceDetails />,
//         },
//       ],
//     },
//   ] )
// }


