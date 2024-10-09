// App.js
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './Home';
import Projects from './Projects';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/projects" element={<Projects />} />
    </Routes>
  );
}

export default App;
