import React from "react";

function QuadroHome({ link, icona, descrizione }) {
	function handleClick() {
		window.location.href = link;
	}
	return (
		<div className="homeChoice">
			<i className={icona} onClick={handleClick}></i>
			<p dangerouslySetInnerHTML={{ __html: descrizione }} onClick={handleClick}></p>
		</div>
	);
}
function Home() {
	return (
		<div className="homeBox">
			<QuadroHome link="/loginCittadino" icona="fa-solid fa-user" descrizione="Login<br/>Cittadino" />
			<QuadroHome link="/registrazioneCittadino" icona="fa-solid fa-user-plus" descrizione="Registrazione<br/>Cittadino" />
			<QuadroHome link="/loginDipendente" icona="fa-solid fa-user-tie" descrizione="Login<br/>Dipendente" />
		</div>
	);
}

export default Home;
