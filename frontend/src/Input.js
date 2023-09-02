import React from "react";

function Input({ type, value, onChange, id=null, name=null, length=null, required=false, placeholder=null }) {
	return (
		<input id={id}
			   type={type}
			   name={name}
			   value={value}
			   minLength={length}
			   maxLength={length}
			   onChange={onChange}
			   required={required}
			   placeholder={placeholder} />
	);
}

export default Input;
