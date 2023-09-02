import React from "react";
import Form from "./Form";

function Login() {
	if (window.location.href.indexOf("Dipendente") > -1) {
		if (window.sessionStorage.getItem('userType') !== "dipendente")
			return <Form placeholder="Username" length="6" link="dipendente"/>;
		window.location.href = "/areaPersonale";
	}
	else {
		if (window.sessionStorage.getItem('userType') !== "cittadino")
			return <Form placeholder="Codice fiscale" length="16" link="cittadino"/>;
		window.location.href = "/areaPersonale";
	}
}

export default Login;
