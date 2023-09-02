import React from "react";

function AreaPersonale() {
	const user = window.sessionStorage.getItem("user");
	if (user === null )
		window.location.href = "/";
	return <>{user}</>;
}

export default AreaPersonale;
