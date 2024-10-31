import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Registration from './Components/Registration';
import Login from './Components/Login';
import './Components/FormStyles.css';
import Home from './Components/Home';

const App = () => (
  <Router>
    <div >
      <nav >
        <h1>EasyLang</h1>
        <Link to="/register">Register</Link> | <Link to="/login">Login</Link>
      </nav>
      <Routes>
        <Route path="/register" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Login />} />
      </Routes>
    </div>
  </Router>
);

export default App;
