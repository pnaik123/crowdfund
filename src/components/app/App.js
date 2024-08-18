import React, { useState,useEffect } from 'react';
import Dashboard from '../dashboard/Dashboard';
import Header from '../header/Header';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import SignIn from '../signin/SignIn';
import SignUp from '../signup/SignUp';
import DonationForm from '../payment/DonationForm';
import PaymentRedirect from '../projects/PaymentRedirect';
import Project from '../projects/Project';


function App() {
  const [user, setUser] = useState(null);
  useEffect(() => {
    const savedToken = localStorage.getItem('token');
    const savedUser = localStorage.getItem('user');

    if (savedToken && savedUser) {
        if(savedToken && !isTokenExpired(savedToken)){
          const user = JSON.parse(savedUser);
          setUser(user); // Update state with user details
        }else{
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          setUser(null); 
        }
    }
}, []);

const isTokenExpired = (token) => {
  const decodedToken = jwtDecode(token);
  const currentTime = Date.now() / 1000; // Convert to seconds
  return decodedToken.exp < currentTime;
};

  return (

    <>
      <BrowserRouter>
      <Header user={user} setUser={setUser}></Header>
      <Routes>
          <Route path="/" element={
            <Dashboard user={user}/>}>
          </Route>
          <Route path="/signin" element={
            <SignIn setUser={setUser}/>}>
          </Route>
          <Route path="/signup" element={
            <SignUp setUser={setUser}/>}>
          </Route>
          <Route path="/paymentRedirect" element={
            <PaymentRedirect setUser={setUser}/>}>
          </Route>
          <Route path="/addprojects" element={<Project projectId={6}/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
