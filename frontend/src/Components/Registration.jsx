import React, { useState } from 'react';
import axios from 'axios';
import './FormStyles.css';

function Registration() {
  const [user, setUser] = useState({
    username: '',
    password: '',
    firstname: '',
    lastname: '',
    role: 'TRANSLATOR',
  });

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // axios.post('/api/users/register', user)
    axios.post('http://localhost:8080/api/users/register', user)
      .then(response => {
        alert('Registration successful!');
      })
      .catch(error => {
        alert('Registration failed: ' + error.response.data);
      });
  };

  return (
    <div className="form-container">
      <h2>EasyLang Registration</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="firstname"
          placeholder="First Name"
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="lastname"
          placeholder="Last Name"
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="username"
          placeholder="Username"
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
          required
        />
        <select name="role" onChange={handleChange} required>
          <option value="TRANSLATOR">Translator</option>
          <option value="EDITOR">Editor</option>
          <option value="MANAGER">Manager</option>
        </select>
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Registration;