import React, { useRef } from 'react';

function QuadroHome({ link, icona, descrizione }) {
	const iRef = useRef(null);
	const pRef = useRef(null);
	function handleClick() {
		window.location.href = link;
	}
	function overElem() {
		iRef.current.classList.add("hover");
		pRef.current.classList.add("hover");
	}
	function outElem() {
		iRef.current.classList.remove("hover");
		pRef.current.classList.remove("hover");
	}
	return (
		<div className="homeChoice">
			<i ref={iRef} className={icona} onClick={handleClick} onMouseOver={overElem} onMouseOut={outElem}></i>
			<p ref={pRef} dangerouslySetInnerHTML={{ __html: descrizione }} onClick={overElem} onMouseOver={overElem} onMouseOut={outElem}></p>
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
