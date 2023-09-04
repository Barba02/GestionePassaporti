import axios from "axios";
import Modal from 'react-modal';
import React, {useEffect, useState} from "react";

let user;
let setter;
let userType;

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
	function modificaStato(stato) {
		axios.put("/gestionePassaporti/slot/" + slot.id, {stato: stato})
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
			return (<>
				<button onClick={() => modificaStato("LIBERO")}>Apri slot</button>
				<span> </span>
				<button onClick={() => modificaStato("CHIUSO")}>Chiudi slot</button>
			</>);
		case "LIBERO":
			return <button onClick={() => modificaStato("CHIUSO")}>Chiudi slot</button>
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
				<SwitchAzioni slot={slot} closer={closeModal} /><br/><br/>
				<button onClick={closeModal}>Chiudi</button>
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
		weekListByTime[key][index] = (item);
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
			{list ? <TabellaSlot list={list} /> : null}
			<div className="legenda">
				{/* eslint-disable-next-line react/style-prop-object */}
				<div>Non gestito</div>
				<div>Aperto</div>
				<div>Occupato</div>
				<div>Chiuso</div>
			</div>
		</>
	);
}

function AreaCittadino() {

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
