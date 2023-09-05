import React from 'react';
import Home from './Home';
import Login from './Login';
import AreaRiservata from './AreaRiservata';
import Registrazione from './Registrazione';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/loginCittadino' element={<Login />} />
                <Route path='/loginDipendente' element={<Login />} />
                <Route path='/areaCittadino' element={<AreaRiservata />} />
                <Route path='/areaDipendente' element={<AreaRiservata />} />
                <Route path='/registrazioneCittadino' element={<Registrazione />} />
                <Route path='/datiPersonali' element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;
