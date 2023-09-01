function Bollino({ link, icona, descrizione }) {
    function handleClick() {
        console.log(link);
    }
    return (
        <div className="bollinoLogin">
            <i className={icona} onClick={handleClick}></i>
            <p dangerouslySetInnerHTML={{ __html: descrizione }} onClick={handleClick}></p>
        </div>
    );
}

function App() {
    return (
        <div className="riquadroLogin">
          <Bollino link="login_cittadino" icona="fa-solid fa-user" descrizione="Login<br/>Cittadino" />
          <Bollino link="registrazione_cittadino" icona="fa-solid fa-user-plus" descrizione="Registrazione<br/>Cittadino" />
          <Bollino link="login_dipendente" icona="fa-solid fa-user-tie" descrizione="Login<br/>Dipendente" />
        </div>
    );
}

export default App;
