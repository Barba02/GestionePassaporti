import axios from "axios";
import Utilities from "./utilities";
import RadioInput from "./RadioInput";
import React, {useEffect, useState} from "react";

let user;
let setterAppuntamenti;

function Appuntamento({ slot }) {
	function disdici() {
		// eslint-disable-next-line no-restricted-globals
		if (slot.stato === "OCCUPATO" && confirm("Vuoi disdire l'appuntamento?"))
			axios.put("/gestionePassaporti/slot/" + slot.id, {cittadino: {}, stato: "LIBERO"})
				.then(response => {
					Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", setterAppuntamenti);
				})
				.catch(error => {
					alert(error.response.data.messaggio);
				});
	}
	return (
		<div className="appuntamento">
			<p>{Utilities.formatDate(new Date(Utilities.getDay(slot.datetime)))} {Utilities.getHour(slot.datetime)}</p>
			<p>{Utilities.capitalize(slot.sede)}</p>
			<p>{slot.tipo}</p>
			<p>{slot.stato}</p>
			{slot.stato === "OCCUPATO" ? <button onClick={disdici}>DISDICI</button> : null}
		</div>
	);
}

function ListaAppuntamenti() {
	const [app, setApp] = useState(null);
	setterAppuntamenti = setApp;
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", setApp);
	}, []);
	return app ? (
		app.map((e, i) => (
			<Appuntamento key={i} slot={e} />
		))
	) : null;
}

function AreaCittadino({ cittadino }) {
	user = cittadino;
	const [tipo, setTipo] = useState(null);
	const [sede, setSede] = useState(null);
	const [listaSedi, setListaSedi] = useState([]);
	useEffect(() => {
		if (!user.passaporto)
			setTipo("RILASCIO");
		else {
			Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", setTipo);
		}
	}, []);
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/slot/sedi", setListaSedi);
	}, []);
	function handleChange(event, func) {
		func(event.target.value);
	}
	return (
		<>
			<h1>I tuoi appuntamenti precedenti</h1>
			<ListaAppuntamenti />
			<h1>Notifiche</h1>
			<h1>Nuovo appuntamento</h1>
			<div className="sedeRadio">
				<p>{tipo ? (tipo[tipo.length-1].tipo === "RITIRO" ? "RINNOVO" : "RITIRO") : null}</p>
				<RadioInput onChange={(e) => handleChange(e, setSede)}
							name="Sede" required={true}
							value={sede} options={listaSedi ? listaSedi : []} />
			</div>
		</>
	);
}

export default AreaCittadino;
