// Home.js
import React from 'react';
import logo from './logo.svg';
import './App.css';
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate(); // This will work as long as Home is within Router context

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p className='Title'>
          Abbishek Kamak
        </p>
        <button onClick={() => navigate("/projects")}>Click here to open my projects</button>
        <p>
          Software - AI - UI/UX designer 
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Download my resume 
        </a>
      </header>
    </div>
  );
}

export default Home;
