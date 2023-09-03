import React from "react";

function AreaPersonale() {
	const user = window.sessionStorage.getItem("user");
	console.log();
	if (user === null || window.location.href.toLowerCase().indexOf(
			window.sessionStorage.getItem("userType")) < 0)
		window.location.href = "/";
	return <>{user}</>;
}

export default AreaPersonale;
