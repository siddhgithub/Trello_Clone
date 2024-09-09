import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Card } from "@mui/material";
import Box from "@mui/material/Box";
import "./style.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';


const WorkspaceDetails = () => {

  const location = useLocation();
  const [ workspace, setWorkspace ] = useState( location.state.workspace );
  const [ createTask, setCreateTask ] = useState( false );
  const [ wtasks, setTasks ] = useState( [] );
  const [ boards, setBoards ] = useState( [] );
  const [ boardName, setBoardName ] = useState();
  const [ workspaceDescription, setWorkspaceDescription ] = useState();
  const [ currentBoardId, setCurrentBoardId ] = useState( -1 );
  const [ currentTask, setCurrentTask ] = useState( "" );
  const [ startdate, setStartDate ] = useState( "" );
  const [ enddate, setEndDate ] = useState( "" );
  const [ newMemberName, setNewMemberName ] = useState( "" );
  const [ members, setMembers ] = useState( [] );
  const [ selectedOption, setSelectedOption ] = useState( 'Task Status' );
  const [ selectedMember, setSelectedMember ] = useState();
  const [ searchTerm, setSearchTerm ] = useState( "" ); // For task name search
  const [ searchBoardId, setSearchBoardId] = useState(-1);
  const [ dueDateFilter, setDueDateFilter ] = useState( "" ); // For task due date filter

  var taskID = 0;

  const handleOptionClick = ( option ) => {
    setSelectedOption( option );
  };

  const handleUpdateTaskStatus = ( option, taskId ) => {
    updateTaskStatus( option, taskId );
    window.location.reload();
  };


  const handleMemberSelect = ( option ) => {
    // setSelectedMember( option );
    const user = members.find( member => member.userName == option )
    if ( user == null ) {
      return
    }

    document.getElementById( "selectMemberButton" ).innerHTML = user.userName;
    setSelectedMember( user );
  }

  useEffect( () => {
    fetchBoards();
    setWorkspaceDescription( workspace.workSpaceDetail );
    fetchMembers();
    fetchTasks();
  }, [] );

  function fetchBoards() {
    fetch(
      "http://localhost:9099/workspace/getBoards?workSpaceId=" + workspace.id,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then( ( response ) => response.json() )
      .then( ( data ) => {
        setBoards( data );
      } )
      .catch( ( error ) => {
        console.error( "Error fetching boards:", error );
        alert( "Error fetching boards. Please try again." );
      } );
  }

  function fetchTasks() {
    console.log(currentBoardId);
    fetch( "http://localhost:9099/tasks/allTasks",
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then( ( response ) => response.json() )
      .then( ( data ) => {
        setTasks( data );
      } )
      .catch( ( error ) => {
        console.error( "Error fetching tasks:", error );
        alert( "Error fetching tasks. Please try again." );
      } );
  }

  function fetchMembers() {
    fetch( "http://localhost:9099/workspace/membersName?workSpaceId=" + workspace.id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    } )
      .then( ( response ) => response.json() )
      .then( ( data ) => {
        setMembers( data ); // Update the state with the list of members
      } )
      .catch( ( error ) => {
        console.error( "Error fetching members:", error );
        alert( "Error fetching members. Please try again." );
      } );
  }

  function displayCreateTask( boardId ) {
    if ( !createTask ) {
      setCreateTask( true );
    }
    else {
      setCreateTask( false );
    }
    setCurrentBoardId( boardId );
  }

  function taskChange( e ) {
    setCurrentTask( e.target.value );
  }

  function sDate( e ) {
    setStartDate( e.target.value );
  }

  function eDate( e ) {
    setEndDate( e.target.value );
  }

  function taskform( event ) {
    event.preventDefault();
    if ( currentTask.trim() !== "" ) { // Check if the task description is not empty or contains only whitespace
      setTasks( [ currentTask, ...wtasks ] );
      setCurrentTask( "" ); // Reset currentTask to an empty string after adding a new task
      setCreateTask( false );
    }
  }

  function handleCreateBoard() {
    fetch(
      "http://localhost:9099/boards/save?workSpaceId=" +
      workspace.id +
      "&boardName=" +
      boardName,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then( ( response ) => response.json() )
      .then( ( data ) => {
        console.log( data );
        setBoards( [ ...boards, data ] );
      } )
      .catch( ( error ) => alert( error ) );
  }

  function handleDeleteBoard( boardNumber ) {
    fetch( "http://localhost:9099/boards/delete?id=" + boardNumber, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    } )
      .then( ( response ) => response.json() )
      .then( ( data ) => {
        if ( data ) {
          const updatedBoards = boards.filter( ( board ) => board.id !== boardNumber );
          setBoards( updatedBoards );
        } else {
          alert( "Error deleting board" );
        }
      } )
      .catch( ( error ) => {
        console.error( "Error deleting board:", error );
        alert( "Error deleting board. Please try again." );
      } );
  }

  function handleDeleteTask( taskId ) {
    fetch( `http://localhost:9099/tasks/delete/${ taskId }`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    } )
      .catch( ( error ) => {
        console.error( "Error deleting tasks:", error );
        alert( "Error deleting tasks. Please try again." );
      } );

    const t = wtasks.filter( ( element ) => element.id !== taskId );
    setTasks( t );
  }

  function boardNameChange( event ) {
    setBoardName( event.target.value );
  }

  function workspaceDescriptionChange( event ) {
    setWorkspaceDescription( event.target.value );
  }

  function updateWorkspace() {
    console.log( workspaceDescription );
    fetch( "http://localhost:9099/workspace/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify( {
        id: workspace.id,
        workSpaceName: workspace.workSpaceName,
        workSpaceDetail: workspaceDescription,
      } ),
    } )
      .then( ( res ) => {
        if ( !res.ok ) {
          throw new Error( "Failed to update workspace" );
        }
        return res.json();
      } )
      .then( ( data ) => {
        try {
          setWorkspace( data );
          console.log( data );
          alert( "Workspace updated." );
          setWorkspaceDescription( data.workSpaceDetail );
        } catch ( error ) {
          throw new Error( "Invalid JSON response" );
        }
      } )
      .catch( ( error ) => alert( error ) );
  }

  function handleAddMemberToWorkspace( newMember ) {
    fetch(
      "http://localhost:9099/workspace/addMemberToWorkSpace?workSpace_Id=" +
      workspace.id +
      "&email=" +
      newMember.name,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then( ( response ) => response.text() )
      .then( ( message ) => {
        console.log( message ); // You can display the success message if needed.
        // Note: The backend method does not return the updated workspace data.
        // If you need to update the frontend state, you may need to fetch the updated workspace details separately.
      } )
      .catch( ( error ) => {
        console.error( "Error adding member:", error );
      } );
  }

  function handleAddMember() {
    // Check if the newMemberName is not empty or contains only whitespace
    if ( newMemberName.trim() !== "" ) {
      const newMember = {
        name: newMemberName,
        // Add other properties of the member if needed.
      };
      handleAddMemberToWorkspace( newMember );
      setNewMemberName( "" ); // Clear the input field after adding the member
      window.location.reload();
    }
  }

  function createTasks( boardId ) {
    if ( selectedMember == null ) {
      alert( 'Please Select a member' );
      return
    }
    fetch( "http://localhost:9099/tasks/save?boardId=" + boardId, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify( {
        taskName: currentTask,
        taskStatus: selectedOption,
        startDate: startdate,
        endDate: enddate,
        user: selectedMember
      } ),
    } )
      .then( ( response ) =>
        response.json()
      )
      .then( ( data ) => {
        setTasks( [ ...wtasks, data ] )
      } )
      .catch( ( error ) => {
        console.error( "Error adding task:", error );
      } );

  }

  function updateTaskStatus( option, taskId ) {
    fetch( "http://localhost:9099/tasks/changeTaskStatus?id=" + taskId + "&newStatus=" + option, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
    } )
    // .then( ( res ) => {
    //   if ( !res.ok ) {
    //     throw new Error( "Failed to update task status" );
    //   }
    //   return res.text();
    // } )
    // .then( ( data ) => {
    //   if ( data == "Changed Task Status Successfully" ) {
    //     console.log( option );
    //     setSelectedOption( option );
    //     alert( "Task Status updated." );

    //   }
    //   else {
    //     alert( data );

    //   }
    // } )
    // .catch( ( error ) => alert( error ) );
  }

  // search and filter
  function handleSearchChange( e,id,name ) {
    console.log(name)
    setSearchTerm( e );
    setSearchBoardId(id);
    document.getElementById(id + "search_task").value = name;
  }
  function handleDueDateFilterChange( filter,boardId ) {
    setDueDateFilter( filter );
    setSearchBoardId(boardId);
  }
  


  return (
    <div className="workspace-page">
      <div className="left-side">
        {/* Code for the left-side content */ }
        <div className="workspace-name">
          <strong>Name: </strong>
          { workspace.workSpaceName }
        </div>
        <div className="members">
          <h5><b>Members: </b></h5>
          <input
            type="text"
            value={ newMemberName }
            onChange={ ( e ) => setNewMemberName( e.target.value ) }
          />
          <button onClick={ handleAddMember }>Add Members</button>
          <ul>
            {/* Display the list of members from the 'members' state */ }
            { members.map( ( member, index ) => (
              <li key={ index }>{ member.userName }</li>
            ) ) }
          </ul>
        </div>

        <div className="description">
          <h5><b>Description: </b></h5>
          <input type="text" onChange={ workspaceDescriptionChange } defaultValue={ workspaceDescription } />
          <button onClick={ updateWorkspace }>Update Description</button>
        </div>
      </div>

      <div className="right-side">
        {/* Code for the right-side content */ }
        <div className="create-board">
          <input type="text" onChange={ boardNameChange } placeholder="Enter Board Name" />
          <button onClick={ handleCreateBoard }>Create Board</button>
        </div>
        <div className="boards">
          { boards.map( ( board ) => (
            // {fetchTasks();}
            <div className="board" key={ board.id } id={ board.id }>
              <div className="board-number">{ board.boardName }</div>
              <div className="delete-create-buttons">
                <button onClick={ () => handleDeleteBoard( board.id ) } className="DeleteBoardButton">
                  Delete Board
                </button>
                <button onClick={ () => displayCreateTask( board.id )
                } className="createTaskButton">
                  Create Tasks
                </button>
              </div>
              <div className="search-filter">
                <input
                  type="text"
                  placeholder="Search Task"
                  id = {board.id + "search_task"}
                  value={board.id === searchBoardId ? searchTerm : ''}
                  onChange={(e) =>  handleSearchChange(e.target.value,board.id,board.boardName)}
                
                />
                <div className="due-date-filters">
                  <button onClick={ () => handleDueDateFilterChange( "",board.id ) }>All</button>
                  <button onClick={ () => handleDueDateFilterChange( "DueToday",board.id ) }>Due Today</button>
                  <button onClick={ () => handleDueDateFilterChange( "DueThisWeek",board.id ) }>Due This Week</button>
                  <button onClick={ () => handleDueDateFilterChange( "Overdue",board.id ) }>Overdue</button>
                </div>
              </div>
              <div className="card-content">
                { wtasks
                  .filter( ( task ) => {
                    if(searchBoardId == -1 || searchBoardId == board.id)
                    {
                      return task.taskName && task.taskName.toLowerCase().includes( searchTerm && searchTerm.toLowerCase());
                    }
                    else{
                      return true;
                    }
                    
                  } )
                  .filter( ( task ) => {
                    if(searchBoardId != -1 && searchBoardId != board.id)
                    {
                      return false
                    }
                    if ( dueDateFilter === "DueToday" ) {
                      const today = new Date();
                      return new Date( task.endDate ) <= today;
                    } else if ( dueDateFilter === "DueThisWeek" ) {
                      const today = new Date();
                      const nextWeek = new Date( today );
                      nextWeek.setDate( today.getDate() + 7 );
                      return new Date( task.endDate ) > today && new Date( task.endDate ) <= nextWeek;
                    } else if ( dueDateFilter === "Overdue" ) {
                      const today = new Date();
                      return new Date( task.endDate ) < today;
                    } else {
                      return true; // If no filter is selected, display all tasks
                    }
                  } )
                  .map( ( task ) => {if ( board.id === task.board.id ) {
                    return ( <Card id="card" key={ task.id } sx={ { minWidth: 150, minHeight: 50 } }>
                      <Box sx={ { minWidth: 120 } }>
                        <h4>{ task.taskName }</h4>
                        <hr />
                        <div>
                          <div><span style={ { fontSize: '16px', fontWeight: 'bold', color: '#333' } }>
                            Task Status:
                          </span>
                            <div className="dropdown">
                              <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                { task.taskStatus }
                              </button>
                              <ul className="dropdown-menu">
                                <li><a className="dropdown-item" onClick={ () => handleUpdateTaskStatus( 'To Do', task.id ) }>To Do</a></li>
                                <li><a className="dropdown-item" onClick={ () => handleUpdateTaskStatus( 'In Progress', task.id ) }>In Progress</a></li>
                                <li><a className="dropdown-item" onClick={ () => handleUpdateTaskStatus( 'Done', task.id ) }>Done</a></li>
                              </ul>
                            </div>
                            {/* { task.taskStatus }</p> */ }</div>
                          <p><span style={ { fontSize: '16px', fontWeight: 'bold', color: '#333' } }>
                            Start Date:
                          </span> { task.startDate }</p>
                          <p><span style={ { fontSize: '16px', fontWeight: 'bold', color: '#333' } }>
                            End Date:
                          </span> { task.endDate }</p>
                          <p><span style={ { fontSize: '16px', fontWeight: 'bold', color: '#333' } }>
                            Assigned Member:
                          </span> { task.user.userName }</p>
                        </div>
                        <hr />
                        <button type="button" onClick={ () => { handleDeleteTask( task.id ); taskID = task.id } } className="btn btn-danger">Delete</button>
                      </Box>
                    </Card>
                    );
                  }
                  return null;
                } ) }
                { ( currentBoardId == board.id && createTask ) && (
                  <div className="taskInfo">
                    <form onSubmit={ taskform }>
                      <label>Task description: </label>
                      <input
                        type="text"
                        name="description"
                        onChange={ taskChange }
                        value={ currentTask }
                        className="submitTaskButton"
                      />

                      <div className="dropdown">
                        <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                          { selectedOption }
                        </button>
                        <ul className="dropdown-menu">
                          <li><a className="dropdown-item" onClick={ () => handleOptionClick( 'To Do' ) }>To Do</a></li>
                          <li><a className="dropdown-item" onClick={ () => handleOptionClick( 'In Progress' ) }>In Progress</a></li>
                          <li><a className="dropdown-item" onClick={ () => handleOptionClick( 'Done' ) }>Done</a></li>
                        </ul>
                      </div>

                      <label>Start Date: </label>
                      <input type="date" id="start" name="trip-start" onChange={ sDate } value={ startdate } />
                      <label>End Date: </label>
                      <input type="date" id="start" name="trip-start" onChange={ eDate } value={ enddate } />
                      <div className="dropdown">
                        <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false" id="selectMemberButton">
                          Assign Member
                        </button>
                        <ul className="dropdown-menu">
                          {/* Display the list of members from the 'members' state */ }
                          { members.map( ( member, index ) => (
                            <li key={ index } className="dropdown-item" onClick={ () => {
                              handleMemberSelect( member.userName );
                            } }>
                              { member.userName }</li>
                          ) ) }
                        </ul>
                      </div>
                      <button type="submit" className="submitTaskButton" onClick={ () => {
                        createTasks( board.id );
                        // fetchTasks();
                      } }>
                        Submit
                      </button>
                    </form>
                  </div>
                ) }
              </div>
            </div>
          ) ) }
        </div>
      </div>
    </div>
  );
}



export default WorkspaceDetails;