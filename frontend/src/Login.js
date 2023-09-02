import axios from "axios";
import Input from "./Input";
import React, {useState} from "react";

function LoginForm({ placeholder, length, link }) {
	const [key, setKey] = useState("");
	const [psw, setPsw] = useState("");
	const url = "/gestionePassaporti/" + link + "/login";
	function handleSubmit(event) {
		event.preventDefault();
		axios.post(url, [key, psw])
			.then(response => {
				let field = (link === "cittadino") ? response.data.anagrafica.cf : response.data.username;
				if (field.toLowerCase() === key.toLowerCase()) {
					window.sessionStorage.setItem("userType", link);
					window.sessionStorage.setItem("user", JSON.stringify(response.data));
					window.location.href = "/areaPersonale";
				}
				else
					alert("Si è verificato un errore");
			})
			.catch(error => {
				alert(error.response.data.messaggio);
			});
	}
	function handleChange(event, func) {
		func(event.target.value);
	}
	return (
		<form onSubmit={handleSubmit} className="loginForm">
			<Input type="text" value={key}
				   required={true} placeholder={placeholder} length={length}
				   onChange={(e) => handleChange(e, setKey)} />
			<Input type="password" value={psw}
				   required={true} placeholder="Password"
				   onChange={(e) => handleChange(e, setPsw)} />
			<button type="submit">ENTRA</button>
		</form>
	);
}

function Login() {
	if (window.location.href.indexOf("Dipendente") > -1) {
		if (window.sessionStorage.getItem('userType') !== "dipendente")
			return <LoginForm placeholder="Username" length="6" link="dipendente"/>;
		window.location.href = "/areaPersonale";
	}
	else {
		if (window.sessionStorage.getItem('userType') !== "cittadino")
			return <LoginForm placeholder="Codice fiscale" length="16" link="cittadino"/>;
		window.location.href = "/areaPersonale";
	}
}

export default Login;
