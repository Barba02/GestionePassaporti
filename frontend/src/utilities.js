import axios from "axios";

export default class Utilities {
	static capitalize(str) {
		const capitalizedWords = str.split("_").map((word) => {
			if (!word.trim()) return word;
			return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
		});
		return capitalizedWords.join(" ");
	}
	static getDay(datetime) {
		return datetime.slice(0, 10);
	}
	static getHour(datetime) {
		return datetime.slice(11).replace('-', ':');
	}
	static formatDate(date) {
		const day = String(date.getDate()).padStart(2, '0');
		const month = String(date.getMonth() + 1).padStart(2, '0'); // Mese è basato su zero, quindi aggiungi 1
		const year = date.getFullYear();
		return `${day}/${month}/${year}`;
	}
	static getDatesOfWeek(day) {
		day.setHours(0, 0, 0, 0);
		const g = day.getDay();
		if (g !== 1)
			day.setDate(day.getDate() - ((g === 0) ? 6 : g - 1));
		const dates = [];
		for (let i = 1; i <= 5; i++) {
			const date = new Date(day);
			date.setDate(day.getDate() + i - 1);
			dates.push(date);
		}
		return dates;
	}
	static sort(dict) {
		const chiavi = Object.keys(dict);
		chiavi.sort();
		const dizionarioOrdinato = {};
		for (const chiave of chiavi)
			dizionarioOrdinato[chiave] = dict[chiave];
		return dizionarioOrdinato;
	}
	static async axiosGetter(url, setter) {
		await axios.get(url)
			.then(response => {
				setter(response.data);
			})
			.catch(error => {
				alert(error.response.data.messaggio ? error.response.data.messaggio : error);
				setter(null);
			});
	}
}
