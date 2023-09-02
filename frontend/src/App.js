import React from 'react';
import Home from './Home';
import Login from './Login';
import AreaPersonale from './AreaPersonale';
import RegistrazioneCittadino from './RegistrazioneCittadino';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/loginCittadino' element={<Login />} />
                <Route path='/loginDipendente' element={<Login />} />
                <Route path='/areaPersonale' element={<AreaPersonale />} />
                <Route path='/registrazioneCittadino' element={<RegistrazioneCittadino />} />
            </Routes>
        </Router>
    );
}

export default App;
