import React from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

function Dashboard(props) {
    // Null checks for props and props.user
    const firstName = props?.user?.firstName || '';
    const lastName = props?.user?.lastName || '';
  return (
    <div className="dashboard">
      <h2>Welcome {firstName} {lastName}!</h2>
      <p>This is the dashboard where you can create and fund projects.</p>
    </div>
  );
}

export default Dashboard;
