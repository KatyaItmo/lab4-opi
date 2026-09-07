import React from 'react';
import '../style.css';

function Header() {
	return (
		<header>
			<h1>Лабораторная работа №4</h1>
			<div className="student-info">
				<p className="info-item">ФИО: Чистякова Екатерина Александровна</p>
				<p className="info-item">Группа: P3218</p>
				<p className="info-item">Вариант: 468018</p>
			</div>
		</header>
	);
}

export default Header;