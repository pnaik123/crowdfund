import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/app/App';
import reportWebVitals from './reportWebVitals';
import { AuthProvider } from './AuthContext';
import { MENUS } from './constants';
// import 'bootstrap/dist/css/bootstrap.min.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <AuthProvider>
        <App />
        </AuthProvider>
    </React.StrictMode>
);
reportWebVitals();
