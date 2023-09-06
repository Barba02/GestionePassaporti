import Utilities from "./utilities";
import TabellaSlot from "./TabellaSlot";
import React, {useEffect, useState} from "react";

const user = JSON.parse(window.sessionStorage.getItem("user"));

function AreaDipendente() {
	const [list, setList] = useState(null);
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/dipendente/" + user.username + "/slots", setList);
		setInterval(() => {
			Utilities.axiosGetter("/gestionePassaporti/dipendente/" + user.username + "/slots", setList);
		}, 20000);
	}, []);
	return (
		<>
			<h1>I tuoi slot</h1>
			<h2>{Utilities.capitalize(user.sede)}</h2>
			{list ? <TabellaSlot list={list} utente={user} tipo="Dipendente" setter={setList} /> : null}
			<div className="legenda">
				<div>Non gestito</div>
				<div>Aperto</div>
				<div>Occupato</div>
				<div>Chiuso</div>
			</div>
		</>
	);
}

export default AreaDipendente;
