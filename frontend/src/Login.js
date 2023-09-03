import axios from "axios";
import Input from "./Input";
import React, {useState} from "react";

function LoginForm({ placeholder, length, userType }) {
	const [key, setKey] = useState("");
	const [psw, setPsw] = useState("");
	const url = "/gestionePassaporti/" + userType.toLowerCase() + "/login";
	function handleSubmit(event) {
		event.preventDefault();
		axios.post(url, [key, psw])
			.then(response => {
				let field = (userType === "Cittadino") ? response.data.anagrafica.cf : response.data.username;
				if (field.toLowerCase() === key.toLowerCase()) {
					window.sessionStorage.setItem("userType", userType);
					window.sessionStorage.setItem("user", JSON.stringify(response.data));
					window.location.href = "/area" + userType;
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
		<form onSubmit={handleSubmit}>
			<Input type="text" value={key} classes="login"
				   required={true} placeholder={placeholder} length={length}
				   onChange={(e) => handleChange(e, setKey)} />
			<Input type="password" value={psw} classes="login"
				   required={true} placeholder="Password"
				   onChange={(e) => handleChange(e, setPsw)} />
			<button type="submit">ENTRA</button>
		</form>
	);
}

function Login() {
	document.getElementsByTagName("body")[0].style.height = "100%";
	let url = window.location.href;
	let userType = window.sessionStorage.getItem("userType");
	if (window.sessionStorage.getItem("user") && url.indexOf(userType) > -1)
		window.location.href = "/area" + userType;
	else {
		if (url.indexOf("Dipendente") > -1)
			return <LoginForm placeholder="Username" length="6" userType="Dipendente"/>;
		return <LoginForm placeholder="Codice fiscale" length="16" userType="Cittadino"/>;
	}
}

export default Login;
