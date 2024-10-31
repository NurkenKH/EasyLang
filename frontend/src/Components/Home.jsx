import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();
  const username = localStorage.getItem('username');
  useEffect(() => {
    if(username === null) {
      navigate('/login');
    }
  }, [username, navigate]);
  console.log('username', username);
  if(username === null) {
    navigate('/login');

  }

  const handleLogout = () => {
    localStorage.removeItem('username');
    navigate('/');
  };

  return (
    <div>
      <h1>Hello {username}</h1>
      <button onClick={handleLogout}>Logout</button> 
    </div>
  );
}

export default Home;