import axios from "axios";
import Modal from "react-modal";
import Utilities from "./utilities";
import React, {useEffect, useState} from "react";

let user;
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

function AreaDipendente({ dipendente }) {
	user = dipendente;
	const [list, setList] = useState(null);
	setterLista = setList;
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

export default AreaDipendente;
