import React from "react";

function AreaPersonale() {
	return <>{window.sessionStorage.getItem("user")}</>;
}

export default AreaPersonale;
