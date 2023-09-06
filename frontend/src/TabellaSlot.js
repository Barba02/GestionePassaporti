import axios from "axios";
import Modal from "react-modal";
import Utilities from "./utilities";
import React, {useState} from "react";

let user;
let userType;
let setterLista;

function SwitchAzioni({ slot, closer }) {
	function modificaStato(stato, tipo=null) {
		axios.put("/gestionePassaporti/slot/" + slot.id, {stato: stato, tipo:tipo})
			.then(response => {
				Utilities.axiosGetter("/gestionePassaporti/dipendente/" + user.username + "/slots", setterLista);
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
	function prenotazione() {
		axios.put("/gestionePassaporti/cittadino/" + user.anagrafica.cf + "/riserva", [slot.id])
			.then(response => {
				window.location.reload();
			})
			.catch(error => {
				alert(error.response.data.messaggio);
			});
	}
	return (
		<>
			<Modal isOpen={modalIsOpen} onRequestClose={closeModal}>
				<h1>{userType === "Dipendente" ? "Gestisci" : "Riserva"} slot</h1>
				<h2>{Utilities.formatDate(new Date(Utilities.getDay(slot.datetime)))} {Utilities.getHour(slot.datetime)}</h2>
				{userType === "Dipendente" ?
					<SwitchAzioni slot={slot} closer={closeModal} /> :
					// eslint-disable-next-line jsx-a11y/anchor-is-valid
					<>
						<p>Si ricorda di portare con sé <a href="/moduloRichiesta.pdf" target="_blank">modulo di richiesta</a> compilato,
							marca da bollo, ricevuta del versamento su C/C postale,
							due fototessera su sfondo bianco ed eventuale passaporto precedente</p>
						<button onClick={prenotazione}>Prenota</button>
					</>}
				<button className="chiusura" onClick={closeModal}>X</button>
			</Modal>
			<td className={slot.stato} onClick={openModal}></td>
		</>
	);
}

function TabellaSlot({ list, utente, tipo, setter }) {
	user = utente;
	userType = tipo;
	setterLista = setter;
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

export default TabellaSlot;
