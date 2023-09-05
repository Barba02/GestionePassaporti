import axios from "axios";
import Modal from 'react-modal';
import Utilities from "./utilities";
import RadioInput from "./RadioInput";
import React, {useEffect, useState} from "react";

let user;
let userType;
let listaDipSetter;
let appuntamentiSetter;

function BarraPersonale() {
	function logout() {
		window.sessionStorage.setItem("user", null);
		window.sessionStorage.setItem("userType", null);
		window.location.href = "/";
	}
	return (
		<nav>
			<p onClick={() => window.location.href = "/datiPersonali"}>
				{(userType === "Cittadino") ?
					user.anagrafica.nome + " " + user.anagrafica.cognome + " [" + user.anagrafica.cf + "]" :
					user.nome + " " + user.cognome + " [" + user.username + "]"}
			</p>
			<p onClick={logout}><u>Logout</u></p>
		</nav>
	);
}

function SwitchAzioni({ slot, closer }) {
	function modificaStato(stato, tipo=null) {
		axios.put("/gestionePassaporti/slot/" + slot.id, {stato: stato, tipo:tipo})
			.then(response => {
				Utilities.axiosGetter("/gestionePassaporti/dipendente/" + user.username + "/slots", listaDipSetter);
				closer();
			})
			.catch(error => {
				alert(error.response.data.messaggio);
				closer();
			});
	}
	switch(slot.stato) {
		case "NON_GESTITO":
			return (
				<div>
					<button onClick={() => modificaStato("LIBERO", "RITIRO")}>Apri ritiro</button>
					<span> </span>
					<button onClick={() => modificaStato("LIBERO", "RILASCIO")}>Apri rilascio</button>
					<span> </span>
					<button onClick={() => modificaStato("LIBERO", "RINNOVO")}>Apri rinnovo</button>
					<span> </span>
					<button onClick={() => modificaStato("CHIUSO")}>Chiudi slot</button>
				</div>
			);
		case "LIBERO":
			return <>Tipo: {slot.tipo}<br/><button onClick={() => modificaStato("CHIUSO")}>Chiudi slot</button></>
		default:
			if (slot.cittadino)
				return <>Tipo: {slot.tipo}<br/>Cittadino: {slot.cittadino.anagrafica.cf}</>
			return <>Slot inutilizzato</>
	}
}

Modal.setAppElement('#root');
function CasellaSlot({ slot }) {
	const [modalIsOpen, setModalIsOpen] = useState(false);
	const openModal = () => {setModalIsOpen(true);}
	const closeModal = () => {setModalIsOpen(false);}
	if (!slot)
		return <td></td>;
	return (
		<>
			<Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
				<h1>Gestisci slot</h1>
				<h2>{Utilities.formatDate(new Date(Utilities.getDay(slot.datetime)))} {Utilities.getHour(slot.datetime)}</h2>
				<SwitchAzioni slot={slot} closer={closeModal} />
				<button className="chiusura" onClick={closeModal}>X</button>
			</Modal>
			<td className={slot.stato} onClick={openModal}></td>
		</>
	);
}

function TabellaSlot({ list }) {
	const [week, setWeek] = useState(Utilities.getDatesOfWeek(new Date()));
	function prevWeek() {
		const actual = new Date(week[0]);
		actual.setDate(actual.getDate() - 7);
		setWeek(Utilities.getDatesOfWeek(actual));
	}
	function nextWeek() {
		const actual = new Date(week[0]);
		actual.setDate(actual.getDate() + 7);
		setWeek(Utilities.getDatesOfWeek(actual));
	}
	let weekListByTime = {};
	list.filter(item => {
		const itemDate = new Date(Utilities.getDay(item.datetime));
		itemDate.setHours(0, 0, 0, 0);
		return itemDate >= week[0] && itemDate <= week[4];
	})
	.forEach(item => {
		const key = Utilities.getHour(item.datetime);
		if (!weekListByTime[key])
			weekListByTime[key] = new Array(5).fill(null);
		const index = new Date(Utilities.getDay(item.datetime)).getDay() - 1;
		weekListByTime[key][index] = item;
	});
	weekListByTime = Utilities.sort(weekListByTime);
	const times = Object.keys(weekListByTime);
	return (
		<table>
			<thead>
				<tr>
					<th>
						<i className="fa-solid fa-arrow-left" onClick={prevWeek}></i>
						<i className="fa-solid fa-arrow-right" onClick={nextWeek}></i>
					</th>
					<th>Lunedì<br/>{Utilities.formatDate(week[0])}</th>
					<th>Martedì<br/>{Utilities.formatDate(week[1])}</th>
					<th>Mercoledì<br/>{Utilities.formatDate(week[2])}</th>
					<th>Giovedì<br/>{Utilities.formatDate(week[3])}</th>
					<th>Venerdì<br/>{Utilities.formatDate(week[4])}</th>
				</tr>
			</thead>
			<tbody>
			{Object.values(weekListByTime).map((arrayOra, i) => (
				<tr key={"riga" + i}>
					<th>{times[i].replace('-', ':')}</th>
					{arrayOra.map((slot, i) => (
						<CasellaSlot key={"slot" + i} slot={slot} />
					))}
				</tr>
			))}
			</tbody>
		</table>
	);
}

function AreaDipendente() {
	const [list, setList] = useState(null);
	listaDipSetter = setList;
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
			{list ? <TabellaSlot list={list} /> : null}
			<div className="legenda">
				<div>Non gestito</div>
				<div>Aperto</div>
				<div>Occupato</div>
				<div>Chiuso</div>
			</div>
		</>
	);
}

function Appuntamento({ slot }) {
	function disdici() {
		// eslint-disable-next-line no-restricted-globals
		if (slot.stato === "OCCUPATO" && confirm("Vuoi disdire l'appuntamento?"))
			axios.put("/gestionePassaporti/slot/" + slot.id, {cittadino: {}, stato: "LIBERO"})
				.then(response => {
					Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", appuntamentiSetter);
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
	appuntamentiSetter = setApp;
	useEffect(() => {
		Utilities.axiosGetter("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots", setApp);
	}, []);
	return app ? (
		app.map((e, i) => (
			<Appuntamento key={i} slot={e} />
		))
	) : null;
}

function AreaCittadino() {
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

function AreaRiservata() {
	user = window.sessionStorage.getItem("user");
	userType = window.sessionStorage.getItem("userType");
	if (user === null || window.location.href.indexOf(userType) < 0)
		window.location.href = "/login" + window.location.href.slice(window.location.href.lastIndexOf("/") + 5);
	else {
		user = JSON.parse(user);
		return (
			<>
				<BarraPersonale />
				{(userType === "Cittadino") ? <AreaCittadino /> : <AreaDipendente />}
			</>
		);
	}
}

export default AreaRiservata;
