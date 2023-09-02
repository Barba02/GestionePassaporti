import React from 'react';
import Home from './Home';
import LoginCittadino from './LoginCittadino';
import LoginDipendente from './LoginDipendente';
import RegistrazioneCittadino from './RegistrazioneCittadino';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/loginCittadino' element={<LoginCittadino />} />
                <Route path='/loginDipendente' element={<LoginDipendente />} />
                <Route path='/registrazioneCittadino' element={<RegistrazioneCittadino />} />
            </Routes>
        </Router>
    );
}

export default App;
