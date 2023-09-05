import React from "react";
import Input from "./Input";
import Utilities from "./utilities";

function RadioElement({ chiave, onChange, name, required, check, val }) {
	return (
		<div>
			<Input type="radio" name={name} id={chiave}
				   value={val} required={required} checked={check}
				   onChange={onChange} />
			<label htmlFor={chiave}>
				<div>{Utilities.capitalize(val)}</div>
			</label>
		</div>
	);
}

function RadioInput({ onChange, name, required, value, options }) {
	return (
		<div className="radioInput">
			<label>{name}</label>
			<div className="radioOptions">
				{options.map((e, i) => (
					<RadioElement key={i}
								  val={e}
								  name={name}
								  chiave={name + i}
								  check={value === e}
								  required={required}
								  onChange={onChange} />
					))}
			</div>
		</div>
	);
}

export default RadioInput;
