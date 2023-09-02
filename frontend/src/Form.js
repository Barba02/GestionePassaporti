import axios from "axios";
import React, {useState} from "react";

function LoginForm({ placeholder, length, link }) {
	const [textValue, setTextValue] = useState("");
	const [passValue, setPassValue] = useState("");
	const url = "/gestionePassaporti/" + link + "/login";
	function handleSubmit(event) {
		event.preventDefault();
		axios.post(url, [textValue, passValue])
			.then(response => {
				let field = (link === "cittadino") ? response.data.anagrafica.cf : response.data.username;
				if (field.toLowerCase() === textValue.toLowerCase()) {
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
	return (
		<form onSubmit={handleSubmit} className="loginForm">
			<input type="text" value={textValue}
				   onChange={(e) => setTextValue(e.target.value)}
				   placeholder={placeholder} minLength={length} maxLength={length} required/>
			<input type="password" value={passValue}
				   onChange={(e) => setPassValue(e.target.value)}
				   placeholder="Password" required/>
			<button type="submit">ENTRA</button>
		</form>
	);
}

export default LoginForm;
