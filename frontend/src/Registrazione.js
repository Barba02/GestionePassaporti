import Input from "./Input";
import React, {useState} from "react";
import RadioInput from "./RadioInput";
import DateInput from "./DateInput";
import axios from "axios";

function Registrazione() {
	document.getElementsByTagName("body")[0].style.height = "100%";
	const [psw, setPsw] = useState("");
	const [cie, setCie] = useState("");
	const [nome, setNome] = useState("");
	const [sesso, setSesso] = useState("");
	const [email, setEmail] = useState("");
	const [cognome, setCognome] = useState("");
	let [passaporto, setPassaporto] = useState("");
	let [diplomatico, setDiplomatico] = useState("");
	let [figliMinori, setFigliMinori] = useState("");
	const [dataNascita, setDataNascita] = useState("");
	const [nazionalita, setNazionalita] = useState("");
	const [luogoNascita, setLuogoNascita] = useState("");
	const [provinciaNascita, setProvinciaNascita] = useState("");
	const [scadenzaPassaporto, setScadenzaPassaporto] = useState("");
	function handleSubmit(event) {
		event.preventDefault();
		let dataCF = {
			nome: nome,
			cognome: cognome,
			sesso: sesso,
			data_nascita: dataNascita,
			luogo_nascita: luogoNascita,
			provincia_nascita: provinciaNascita}
		axios.post("/gestionePassaporti/anagrafica/cf", dataCF)
			.then(response => {
				let cf = response.data;
				// eslint-disable-next-line no-restricted-globals
				if (confirm("Confermi che " + cf + " è il tuo codice fiscale?")) {
					diplomatico = (diplomatico !== "No");
					figliMinori = (figliMinori !== "No");
					passaporto = (passaporto.length === 0) ? null : passaporto;
					let data = {
						anagrafica: {cf: cf},
						email: email.toLowerCase(),
						diplomatico: diplomatico,
						figli_minori: figliMinori,
						password: {password: psw},
						cie: cie.toUpperCase(),
						passaporto: passaporto.toUpperCase(),
						scadenza_passaporto: scadenzaPassaporto};
					axios.post("/gestionePassaporti/cittadino/registra", data)
						.then(response => {
							alert("Registrazione completata");
							window.location.href = "/loginCittadino";
						})
						.catch(error => {
							alert(error.response.data.messaggio);
						});
				}
				else
					alert("Ricontrolla i tuoi dati");
			})
			.catch(error => {
				alert(error.response.data.messaggio);
			});
	}
	function handleChange(event, func) {
		func(event.target.value);
	}
	return (
		<form onSubmit={handleSubmit} className="registrationForm">
			<div>
				<Input type="text" value={nome}
					   required={true} placeholder="Nome"
					   onChange={(e) => handleChange(e, setNome)} />
				<Input type="text" value={cognome}
					   required={true} placeholder="Cognome"
					   onChange={(e) => handleChange(e, setCognome)} />
			</div>
			<div>
				<DateInput name="Data di nascita" value={dataNascita} required={true}
						   onChange={(e) => handleChange(e, setDataNascita)} />
				<Input type="text" value={luogoNascita}
					   required={true} placeholder="Luogo di nascita"
					   onChange={(e) => handleChange(e, setLuogoNascita)} />
				<Input type="text" value={provinciaNascita} length="2"
					   required={true} placeholder="Prov" id="prov"
					   onChange={(e) => handleChange(e, setProvinciaNascita)} />
			</div>
			<div>
				<RadioInput onChange={(e) => handleChange(e, setSesso)}
							name="Sesso" required={true}
							value={sesso} options={["M", "F"]} />
				<Input type="text" value={nazionalita}
					   required={true} placeholder="Nazionalità"
					   onChange={(e) => handleChange(e, setNazionalita)} />
				<RadioInput onChange={(e) => handleChange(e, setDiplomatico)}
							name="Diplomatico" required={true}
							value={diplomatico} options={["Sì", "No"]} />
				<RadioInput onChange={(e) => handleChange(e, setFigliMinori)}
							name="Figli minori" required={true}
							value={figliMinori} options={["Sì", "No"]} />
			</div>
			<div>
				<Input type="text" value={cie} length="9" classes="numeroDoc"
					   required={true} placeholder="CIE"
					   onChange={(e) => handleChange(e, setCie)} />
				<Input type="text" value={passaporto} length="9" classes="numeroDoc"
					   placeholder="Passaporto"
					   onChange={(e) => handleChange(e, setPassaporto)} />
				<DateInput name="Scadenza passaporto" value={scadenzaPassaporto}
						   onChange={(e) => handleChange(e, setScadenzaPassaporto)} />
			</div>
			<div>
				<Input type="email" value={email}
					   required={true} placeholder="Email"
					   onChange={(e) => handleChange(e, setEmail)} />
				<Input type="password" value={psw}
					   required={true} placeholder="Password"
					   onChange={(e) => handleChange(e, setPsw)} />
			</div>
			<button type="submit">REGISTRATI</button>
		</form>
	);
}

export default Registrazione;
