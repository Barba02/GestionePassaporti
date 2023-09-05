import axios from "axios";
import Modal from 'react-modal';
import React, {useEffect, useState} from "react";
import RadioInput from "./RadioInput";

let user;
let setter;
let userType;
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
				fetchDataAndSetList();
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
	const dt = slot.datetime;
	return (
		<>
			<Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
				<h1>Gestisci slot</h1>
				<h2>{formatDate(new Date(dt.slice(0, 10)))} {dt.slice(11).replace('-', ':')}</h2>
				<SwitchAzioni slot={slot} closer={closeModal} />
				<button className="chiusura" onClick={closeModal}>X</button>
			</Modal>
			<td className={slot.stato} onClick={openModal}></td>
		</>
	);
}

function formatDate(date) {
	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0'); // Mese è basato su zero, quindi aggiungi 1
	const year = date.getFullYear();
	return `${day}/${month}/${year}`;
}
function getDatesOfWeek(day) {
	day.setHours(0, 0, 0, 0);
	const g = day.getDay();
	if (g !== 1)
		day.setDate(day.getDate() - ((g === 0) ? 6 : g - 1));
	const dates = [];
	for (let i = 1; i <= 5; i++) {
		const date = new Date(day);
		date.setDate(day.getDate() + i - 1);
		dates.push(date);
	}
	return dates;
}
function sort(dict) {
	const chiavi = Object.keys(dict);
	chiavi.sort();
	const dizionarioOrdinato = {};
	for (const chiave of chiavi)
		dizionarioOrdinato[chiave] = dict[chiave];
	return dizionarioOrdinato;
}
function TabellaSlot({ list }) {
	const [week, setWeek] = useState(getDatesOfWeek(new Date()));
	function prevWeek() {
		const actual = new Date(week[0]);
		actual.setDate(actual.getDate() - 7);
		setWeek(getDatesOfWeek(actual));
	}
	function nextWeek() {
		const actual = new Date(week[0]);
		actual.setDate(actual.getDate() + 7);
		setWeek(getDatesOfWeek(actual));
	}
	let weekListByTime = {};
	list.filter(item => {
		const itemDate = new Date(item.datetime.slice(0, 10));
		itemDate.setHours(0, 0, 0, 0);
		return itemDate >= week[0] && itemDate <= week[4];
	})
	.forEach(item => {
		const key = item.datetime.slice(11);
		if (!weekListByTime[key])
			weekListByTime[key] = new Array(5).fill(null);
		const index = new Date(item.datetime.slice(0, 10)).getDay() - 1;
		weekListByTime[key][index] = item;
	});
	weekListByTime = sort(weekListByTime);
	const times = Object.keys(weekListByTime);
	return (
		<table>
			<thead>
				<tr>
					<th>
						<i className="fa-solid fa-arrow-left" onClick={prevWeek}></i>
						<i className="fa-solid fa-arrow-right" onClick={nextWeek}></i>
					</th>
					<th>Lunedì<br/>{formatDate(week[0])}</th>
					<th>Martedì<br/>{formatDate(week[1])}</th>
					<th>Mercoledì<br/>{formatDate(week[2])}</th>
					<th>Giovedì<br/>{formatDate(week[3])}</th>
					<th>Venerdì<br/>{formatDate(week[4])}</th>
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

function capitalize(str) {
	const capitalizedWords = str.split("_").map((word) => {
		if (!word.trim()) return word;
		return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
	});
	return capitalizedWords.join(" ");
}
async function fetchDataAndSetList() {
	await axios.get("/gestionePassaporti/dipendente/" + user.username + "/slots")
		.then(response => {
			setter(response.data);
		})
		.catch(error => {
			alert(error.response.data.messaggio);
			setter(null);
		});
}
function AreaDipendente() {
	const [list, setList] = useState(null);
	setter = setList;
	useEffect(() => {
		fetchDataAndSetList();
		setInterval(() => {
			fetchDataAndSetList();
		}, 20000);
	}, []);
	return (
		<>
			<h1>I tuoi slot</h1>
			<h2>{capitalize(user.sede)}</h2>
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
					getAppuntamenti();
				})
				.catch(error => {
					alert(error.response.data.messaggio);
				});
	}
	return (
		<div>
			{slot.datetime}
			{slot.tipo}
			{slot.stato}
			{slot.stato === "OCCUPATO" ? <button onClick={disdici}>Disdici</button> : null}
		</div>
	);
}
async function getAppuntamenti() {
	await axios.get("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots")
		.then(response => {
			appuntamentiSetter(response.data);
		})
		.catch(error => {
			alert(error.response.data.messaggio);
			appuntamentiSetter(null);
		});
}
function ListaAppuntamenti() {
	const [app, setApp] = useState(null);
	appuntamentiSetter = setApp;
	useEffect(() => {
		getAppuntamenti();
	}, []);
	return app ? (
		app.map((e, i) => (
			<Appuntamento key={i} slot={e} />
		))
	) : null;
}

async function getTipo(set) {
	await axios.get("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/slots")
		.then(response => {
			if (response.data[response.data.length - 1].tipo === "RITIRO")
				set("RINNOVO");
			else
				set("RITIRO");
		})
		.catch(error => {
			alert(error.response.data.messaggio);
		});
}
function AreaCittadino() {
	const [tipo, setTipo] = useState(null);
	const [sede, setSede] = useState(null);
	function handleChange(event, func) {
		func(event.target.value);
	}
	useEffect(() => {
		if (!user.passaporto)
			setTipo("RILASCIO");
		else
			getTipo(setTipo);
	}, []);
	function getSedi() {
		return [" f", " f"];
	}
	return (
		<>
			<h1>I tuoi appuntamenti precedenti</h1>
			<ListaAppuntamenti />
			<h1>Notifiche</h1>
			<h1>Nuovo appuntamento</h1>
			<div>
				<p>{tipo}</p>
				<RadioInput onChange={(e) => handleChange(e, setSede())}
							name="Sede" required={true}
							value={sede} options={getSedi()} />
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
