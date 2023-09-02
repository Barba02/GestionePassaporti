import Input from "./Input";
import React, {useState} from "react";
import RadioInput from "./RadioInput";


function Registrazione() {
	const [psw, setPsw] = useState("");
	const [cie, setCie] = useState("");
	const [nome, setNome] = useState("");
	const [sesso, setSesso] = useState("");
	const [email, setEmail] = useState("");
	const [cognome, setCognome] = useState("");
	const [passaporto, setPassaporto] = useState("");
	const [diplomatico, setDiplomatico] = useState("");
	const [dataNascita, setDataNascita] = useState("");
	const [nazionalita, setNazionalita] = useState("");
	const [figliMinori, setFigliMinori] = useState("");
	const [luogoNascita, setLuogoNascita] = useState("");
	const [provinciaNascita, setProvinciaNascita] = useState("");
	const [scadenzaPassaporto, setScadenzaPassaporto] = useState("");
	function handleSubmit(event) {
		event.preventDefault();
	}
	function handleChange(event, func) {
		func(event.target.value);
	}
	return (
		<form onSubmit={handleSubmit} className="registrationForm">
			<Input type="text" value={nome}
				   required={true} placeholder="Nome"
				   onChange={(e) => handleChange(e, setNome)} />
			<Input type="text" value={cognome}
				   required={true} placeholder="Cognome"
				   onChange={(e) => handleChange(e, setCognome)} />
			<Input type="text" value={nazionalita}
				   required={true} placeholder="Nazionalità"
				   onChange={(e) => handleChange(e, setNazionalita)} />
			<Input type="date" value={dataNascita} required={true}
				   onChange={(e) => handleChange(e, setDataNascita)} />
			<Input type="text" value={luogoNascita}
				   required={true} placeholder="Luogo di nascita"
				   onChange={(e) => handleChange(e, setLuogoNascita)} />
			<Input type="text" value={provinciaNascita} length="2"
				   required={true} placeholder="Provincia di nascita"
				   onChange={(e) => handleChange(e, setProvinciaNascita)} />
			<Input type="email" value={email}
				   required={true} placeholder="Email"
				   onChange={(e) => handleChange(e, setEmail)} />
			<Input type="text" value={cie} length="9"
				   required={true} placeholder="Numero carta d'identità"
				   onChange={(e) => handleChange(e, setCie)} />
			<Input type="text" value={passaporto} length="9"
				   placeholder="Numero passaporto"
				   onChange={(e) => handleChange(e, setPassaporto)} />
			<Input type="date" value={scadenzaPassaporto}
				   onChange={(e) => handleChange(e, setScadenzaPassaporto)} />
			<Input type="password" value={psw}
				   required={true} placeholder="Password"
				   onChange={(e) => handleChange(e, setPsw)} />
			<RadioInput onChange={(e) => handleChange(e, setSesso)}
						name="Sesso" required={true}
						value={sesso} options={["M", "F"]} />
			<RadioInput onChange={(e) => handleChange(e, setDiplomatico)}
						name="Diplomatico" required={true}
						value={diplomatico} options={["Sì", "No"]} />
			<RadioInput onChange={(e) => handleChange(e, setFigliMinori)}
						name="Figli minori" required={true}
						value={figliMinori} options={["Sì", "No"]} />
			<button type="submit">REGISTRATI</button>
		</form>
	);
}

export default Registrazione;
