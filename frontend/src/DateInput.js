import React from "react";
import Input from "./Input";

function RadioElement({ chiave, onChange, name, required, check, val }) {
	return (
		<>
			<label htmlFor={chiave}>{val}</label>
			<Input type="radio" name={name} id={chiave}
				   value={val} required={required} checked={check}
				   onChange={onChange} />
		</>
	);
}

function RadioInput({ onChange, name, required, value, options }) {
	return (
		<>
			<label>{name}</label>
			{options.map((e, i) => (
				<RadioElement key={i}
							  val={e}
							  name={name}
							  chiave={name + i}
							  check={value === e}
							  required={required}
							  onChange={onChange} />
				))}
		</>
	);
}

export default RadioInput;
