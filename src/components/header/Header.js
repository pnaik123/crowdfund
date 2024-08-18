import React, { useEffect, useState } from "react";
import Button from '@mui/material/Button';
import AppBar from '@mui/material/AppBar';
import { useNavigate } from 'react-router-dom';

import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import { MENUS } from "../../constants";

const drawerWidth = 240;

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  }));


const Header = (props) => {

    const theme = useTheme();
    const [open, setOpen] = React.useState(false);
    const navigate = useNavigate();
    const user = props.user
    const firstName = user?.firstName || 'Guest';
    const lastName = user?.lastName || '';
    const isInnovator = user?.role?.toLowerCase() === 'innovator' || false;


    const handleAuth = () => {
        if (user) {
          // If user is logged in, log them out
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          props.setUser(null); // Update parent state to reflect logged out status
          navigate('/'); // Redirect to the login page
        } else {
          // If user is not logged in, redirect to the login page
          navigate('/signin');
        }
    };
    
    
  const handleDrawerOpen = () => {
    setOpen(true);
  };



  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleAddProject=()=>{
    navigate('/addprojects')
  }
  const handleMenu=(event)=>{
    
    var type = event.currentTarget.outerText;
    setOpen(false);
    if(type.toLowerCase()==="dashboard"){
        navigate('/');
    } 
  };

  
    return (
        <Box sx={{ flexGrow: 1 }}>
          <AppBar position="static">
            <Toolbar>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={handleDrawerOpen}
                sx={{ mr: 2 }}
              >
                <MenuIcon />
              </IconButton>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              {firstName} {lastName}
              </Typography>
              {isInnovator?<Button color="inherit" onClick={handleAddProject}>Add Project</Button>:""}
              <Button color="inherit" onClick={handleAuth}>{props.user?"Logout":"Login"}</Button>
            </Toolbar>
          </AppBar>
          <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <DrawerHeader>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List>
          {MENUS.map((text, index) => (
            <ListItem key={text} disablePadding onClick={handleMenu} >
              <ListItemButton>
                <ListItemIcon>
                  {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                </ListItemIcon>
                <ListItemText primary={text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        <Divider />
      </Drawer>
        </Box>
      );
};

export default Header;
