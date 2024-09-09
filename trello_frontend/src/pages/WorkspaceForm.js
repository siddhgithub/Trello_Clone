import React, { useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import "./workspacefromstyle.css"

const WorkspaceForm = () => {
  const [workspaceName, setWorkspaceName] = useState("")
  const [workspaceDescription, setWorkspaceDescription] = useState("")
  const [members, setMembers] = useState("")
  const [workspaces, setWorkspaces] = useState([])
  const [isFormOpen, setIsFormOpen] = useState(false)
  const navigate = useNavigate()

  useEffect(() => {
    fetchWorkspaces();
  }, []);

  const handleWorkspaceNameChange = (e) => {
    setWorkspaceName(e.target.value)
  }

  const handleWorkspaceDescriptionChange = (e) => {
    setWorkspaceDescription(e.target.value)
  }

  const handleMembersChange = (e) => {
    setMembers(e.target.value)
  }

  const handleFormSubmit = async (e) => {
    e.preventDefault()

    try {
      fetch("http://localhost:9099/workspace/save?workSpaceName=" + workspaceName 
      + "&workSpaceDetail=" + workspaceDescription + "&", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setWorkspaces([...workspaces, data])
        setWorkspaceName("")
        setWorkspaceDescription("")
        setMembers("")
        setIsFormOpen(false)
      })
      .catch(error => alert("Failed to create workspace"));
    } catch (error) {
      console.log(error)
    }
  }

  function fetchWorkspaces() {
    try {

      fetch(
        "http://localhost:9099/workspace/allWorkSpaces",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      )
      .then(response => response.json())
      .then(data => {
        setWorkspaces(data)
      })
      .catch(error => alert(error));
      
    } catch (error) {
      console.log(error)
    }
  }

  const handleFormOpen = () => {
    setIsFormOpen(true)
  }

  const handleFormClose = () => {
    setIsFormOpen(false)
  }

  const handleWorkspaceClick = (index) => {
    navigate( `/home/workspace/${ workspaces[ index ] }`, { state: { workspace: workspaces[ index ] } } )
  }

  return (
    <div className="container">
      <div className="Create-workspace">
        <button onClick={handleFormOpen}>Create new workspace</button>
      </div>
      {isFormOpen && (
        <div className="overlay">
          <div className="modal">
            <button className="close-btn" onClick={handleFormClose}>
              Close
            </button>
            <form onSubmit={handleFormSubmit}>
              <label>
                Workspace Name:
                <input
                  type="text"
                  value={workspaceName}
                  onChange={handleWorkspaceNameChange}
                />
              </label>
              <label>
                Workspace Description:
                <input
                  type="text"
                  value={workspaceDescription}
                  onChange={handleWorkspaceDescriptionChange}
                />
              </label>
              
              <div>
                <button type="submit">Create Workspace</button>
              </div>
            </form>
          </div>
        </div>
      )}
      <div>
        <h2>Existing Workspaces:</h2>
        {workspaces.map((workspace, index) => (
          <div key={index} className="button_exist">
            <button onClick={() => handleWorkspaceClick(index)}>
              {workspace.workSpaceName}
            </button>
          </div>
        ))}
      </div>
    </div>
  )
}

export default WorkspaceForm
