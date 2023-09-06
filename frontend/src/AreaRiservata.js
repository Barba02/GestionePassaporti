import React from "react";
import AreaCittadino from "./AreaCittadino";
import AreaDipendente from "./AreaDipendente";

let user;
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
				{(userType === "Cittadino") ? <AreaCittadino cittadino={user}/> : <AreaDipendente dipendente={user} />}
			</>
		);
	}
}

export default AreaRiservata;
