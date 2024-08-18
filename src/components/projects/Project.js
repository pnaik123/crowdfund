import React, { useState } from 'react';
import {
  TextField,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Button,
  Grid,
  Box,
  Container,
  Snackbar,
  Alert
} from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { sendPostRequest } from '../../ApiService';
import dayjs from 'dayjs';

const Project = () => {
  const [projectTitle, setProjectTitle] = useState('');
  const [error,setError] = useState('');
  const [projectDescription, setProjectDescription] = useState('');
  const [goalAmount, setGoalAmount] = useState('');
  const [category, setCategory] = useState('');
  const [subcategory, setSubcategory] = useState('');
  const [launchDate, setLaunchDate] = useState(null);
  const [bankDetails, setBankDetails] = useState('');
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [subcategories, setSubcategories] = useState([]);

  const handleCategoryChange = (event) => {
    const selectedCategory = event.target.value;
    setCategory(selectedCategory);
    
    // Dummy subcategories based on the selected category
    const dummySubcategories = {
      'Technology': ['Software', 'Hardware'],
      'Art': ['Painting', 'Sculpture'],
      'Science': ['Physics', 'Biology'],
    };

    setSubcategories(dummySubcategories[selectedCategory] || []);
    setSubcategory(''); // Reset subcategory when category changes
  };

  const handleSubmit = async () => {
    debugger
    const projectData = {
      title: projectTitle,
      description: projectDescription,
      goalAmount: goalAmount,
      category: category,
      subcategory: subcategory,
      launchDate: launchDate ? dayjs(launchDate).format('YYYY-MM-DD') : null,
      bankDetails: bankDetails,
    };

    try {
      await sendPostRequest(projectData, '/api/projects'); // Replace 'projects' with your endpoint
      setOpenSnackbar(true); // Show success alert
    }catch (error) {
      setError(error?.response?.data?.message);
  } finally {
      // setLoading(false);
  }
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  };

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          padding: 3,
          marginTop: 5,
          backgroundColor: 'background.paper',
          borderRadius: 2,
          boxShadow: 3,
        }}
      >
        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Project Title"
              variant="outlined"
              value={projectTitle}
              onChange={(e) => setProjectTitle(e.target.value)}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Project Description"
              variant="outlined"
              multiline
              rows={4}
              value={projectDescription}
              onChange={(e) => setProjectDescription(e.target.value)}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Goal Amount"
              variant="outlined"
              type="number"
              value={goalAmount}
              onChange={(e) => setGoalAmount(e.target.value)}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <FormControl fullWidth variant="outlined">
              <InputLabel>Category</InputLabel>
              <Select
                value={category}
                onChange={handleCategoryChange}
                label="Category"
              >
                <MenuItem value="Technology">Technology</MenuItem>
                <MenuItem value="Art">Art</MenuItem>
                <MenuItem value="Science">Science</MenuItem>
                {/* Add more categories as needed */}
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={12} md={6}>
            <FormControl fullWidth variant="outlined">
              <InputLabel>Subcategory</InputLabel>
              <Select
                value={subcategory}
                onChange={(e) => setSubcategory(e.target.value)}
                label="Subcategory"
                disabled={!category}
              >
                {subcategories.map((sub) => (
                  <MenuItem key={sub} value={sub}>
                    {sub}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={12} md={6}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                label="Target Launch Date"
                value={launchDate}
                onChange={(newDate) => setLaunchDate(newDate)}
                renderInput={(params) => <TextField {...params} fullWidth />}
              />
            </LocalizationProvider>
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Bank Details"
              variant="outlined"
              value={bankDetails}
              onChange={(e) => setBankDetails(e.target.value)}
            />
          </Grid>

          <Grid item xs={12}>
            <Button
              variant="contained"
              color="primary"
              onClick={handleSubmit}
              sx={{ marginTop: 2 }}
            >
              Submit
            </Button>
          </Grid>
        </Grid>

        <Snackbar
          open={openSnackbar}
          autoHideDuration={6000}
          onClose={handleCloseSnackbar}
        >
          <Alert onClose={handleCloseSnackbar} severity="success">
            Project created successfully!
          </Alert>
        </Snackbar>
      </Box>
    </Container>
  );
};

export default Project;
