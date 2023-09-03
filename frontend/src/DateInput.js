import React from "react";
import Input from "./Input";

function DateInput({ onChange, name, required, value }) {
	return (
		<div className="dateInput">
			<label htmlFor={name}>{name}</label>
			<Input type="date" name={name} id={name}
				   value={value} required={required} onChange={onChange} />
		</div>
	);
}

export default DateInput;
