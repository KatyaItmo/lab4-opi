import React, {useEffect, useState} from 'react';
import '../style.css';

function Footer() {
	const [vers, setVers] = useState("?");

	useEffect(() => {
		fetch('api/version')
			.then(response => response.json())
			.then(data => {
				setVers(data.message);
			})
			.catch(error => {
				console.error("Ошибка при получении версии:", error);
				setVers("ошибка");
			});
	}, []);

	return (
		<footer>
			<p className="footer-info">Дединсад @_^</p>
			<p className="footer-info">Версия {vers}</p>
		</footer>
	);
}

export default Footer;