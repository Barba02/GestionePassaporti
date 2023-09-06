import axios from "axios";
import Utilities from "./utilities";
import RadioInput from "./RadioInput";
import TabellaSlot from "./TabellaSlot";
import React, {useEffect, useState} from "react";

let homeSetter;
let reqSede, reqTipo;
const user = JSON.parse(window.sessionStorage.getItem("user"));

function Appuntamento({ slot }) {
	function disdici() {
		// eslint-disable-next-line no-restricted-globals
		if (confirm("Vuoi disdire l'appuntamento?")) {
			axios.put("/gestionePassaporti/slot/" + slot.id, {cittadino: {}, stato: "LIBERO"})
				.then(response => {
					window.location.reload();
				})
				.catch(error => {
					alert(error.response.data.messaggio);
				});
		}
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
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", setApp);
	}, []);
	return app ? (
		app.map((e, i) => (
			<Appuntamento key={i} slot={e} />
		))
	) : null;
}

function NuovoAppuntamento() {
	const [tipo, setTipo] = useState(null);
	const [sede, setSede] = useState(null);
	const [listaSedi, setListaSedi] = useState(null);
	let checkTipo = typeof tipo === 'string' || tipo instanceof String;
	useEffect(() => {
		if (!user.passaporto)
			setTipo("Rilascio");
		else
			Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", (newTipo) => {
				setTipo(newTipo ? (newTipo[newTipo.length-1].tipo === "RITIRO" ? "Rinnovo" : "Ritiro") : null);
			});
		if (checkTipo)
			Utilities.axiosGetter("/gestionePassaporti/slot/sedi?stato=libero&tipo=" + tipo, setListaSedi);
	}, [checkTipo, tipo]);
	function handleChange(event, func) {
		func(event.target.value);
	}
	function handleSubmit(event) {
		event.preventDefault();
		reqSede = sede;
		reqTipo = tipo;
		homeSetter(false);
	}
	return (
		<>
			<h1>Nuovo appuntamento</h1>
			<form className="nuovoApp" onSubmit={handleSubmit}>
				{checkTipo ? <p><b>TIPO:</b> {tipo}</p> : null}
				{listaSedi ? (listaSedi.length > 0 ?
						<><RadioInput onChange={(e) => handleChange(e, setSede)}
										 name="SEDE" required={true}
										 value={sede} options={listaSedi} />
						<button type="submit">Cerca slot</button></>
						: <p>Non ci sono slot liberi di questo tipo</p>)
					: null}
			</form>
		</>
	);
}

function CercaSlot() {
	const [list, setList] = useState(null);
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/slot/" + reqSede + "?stato=libero&tipo=" + reqTipo, setList);
		setInterval(() => {
			if (reqSede && reqTipo)
				Utilities.axiosGetter("/gestionePassaporti/slot/" + reqSede + "?stato=libero&tipo=" + reqTipo, setList);
		}, 20000);
	}, []);
	return list ? <TabellaSlot list={list} utente={user} setter={setList} tipo="Cittadino" /> : null;
}

function AreaCittadino() {
	const [showHome, setShowHome] = useState(true);
	homeSetter = setShowHome;
	return showHome ? (
			<>
				<h1>I tuoi appuntamenti precedenti</h1>
				<ListaAppuntamenti />
				<h1>Notifiche</h1>
				<NuovoAppuntamento />
			</>
		) :
		<CercaSlot />;
}

export default AreaCittadino;
