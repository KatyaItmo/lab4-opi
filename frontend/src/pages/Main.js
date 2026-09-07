import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { setHistoryAction, addPointsAction, clearHistoryAction, setRAction } from '../redux/store';
import Header from "../components/Header.js";
import Footer from "../components/Footer.js";
import '../style.css';

function Main() {
    const navigate = useNavigate();
    const svgRef = useRef(null);
    const dispatch = useDispatch();

    const history = useSelector(state => state.history);
    const r = useSelector(state => state.currentR);

    const [tableData, setTableData] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 10;

    const [x, setX] = useState("0");
    const [y, setY] = useState("0");
    const [message, setMessage] = useState("");

    const K = 30;

    useEffect(() => {
        const fetchData = async () => {
            try {
                const resAll = await fetch('api/points/all', { credentials: 'include' });
                if (resAll.ok) {
                    const allPoints = await resAll.json();
                    dispatch(setHistoryAction(allPoints));
                }

                const resPage = await fetch(`api/points?page=${currentPage}&size=${pageSize}`, { credentials: 'include' });
                if (resPage.ok) {
                    const pagePoints = await resPage.json();
                    setTableData(pagePoints);
                }
            } catch (e) { console.error("Ошибка синхронизации", e); }
        };

        fetchData();
        const interval = setInterval(fetchData, 500);
        return () => clearInterval(interval);
    }, [dispatch, currentPage]);

    const handleInputChange = (val, setter) => {
        let normalized = val.replace(',', '.');
        if (/^-?\d*\.?\d*$/.test(normalized)) {
            if (normalized === "" || normalized === "-") { setter(normalized); return; }
            const num = parseFloat(normalized);
            if (num >= -3 && num <= 5) setter(normalized);
        }
    };

    const handleStep = (current, setter, step) => {
        const num = parseFloat(current) || 0;
        const next = num + step;
        if (next >= -3 && next <= 5) setter(String(next.toFixed(1)));
    };

    const sendPoint = async (e, customX = null, customY = null) => {
        if (e) e.preventDefault();
        const finalX = customX !== null ? String(customX) : x;
        const finalY = customY !== null ? String(customY) : y;

        try {
            const response = await fetch('api/points', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify([{ x: finalX, y: finalY, r: String(r) }]),
                credentials: 'include'
            });

            if (response.ok) {
                const result = await response.json();
                dispatch(addPointsAction(result));
                setMessage("Точка отправлена");
            }
        } catch (error) { setMessage("Нет связи"); }
    };

    const handleGraphClick = (e) => {
        const rect = svgRef.current.getBoundingClientRect();
        const calculatedX = ((e.clientX - rect.left - 150) / K).toFixed(2);
        const calculatedY = ((150 - (e.clientY - rect.top)) / K).toFixed(2);
        setX(calculatedX);
        setY(calculatedY);
        sendPoint(null, calculatedX, calculatedY);
    };

    const handleRChange = (val) => {
        let normalized = val.replace(',', '.');
        if (/^-?\d*\.?\d*$/.test(normalized)) {
            if (normalized === "" || normalized === "-") {
                dispatch(setRAction(normalized));
                return;
            }
            const num = parseFloat(normalized);
            if (num >= -3 && num <= 5) {
                dispatch(setRAction(normalized));
            }
        }
    };

    const renderFigures = () => {
        const R = parseFloat(r);
        if (isNaN(R) || R === 0) return null;
        const absR = Math.abs(R);
        const isNeg = R < 0;

        return (
            <g fill="#3498db" fillOpacity="0.3">
                <rect 
                    x={isNeg ? 150 - (absR/2) * K : 150} 
                    y={isNeg ? 150 : 150 - absR * K} 
                    width={(absR / 2) * K} 
                    height={absR * K} 
                />
                <polygon points={
                    isNeg 
                    ? `150,150 ${150 + (absR/2) * K},150 150,${150 + absR * K}`
                    : `150,150 ${150 - (absR/2) * K},150 150,${150 - absR * K}`
                } />
                <path d={
                    isNeg
                    ? `M 150 150 L 150 ${150 - (absR/2) * K} A ${(absR/2) * K} ${(absR/2) * K} 0 0 0 ${150 - (absR/2) * K} 150 Z`
                    : `M 150 150 L ${150 + (absR/2) * K} 150 A ${(absR/2) * K} ${(absR/2) * K} 0 0 1 150 ${150 + (absR/2) * K} Z`
                } />
            </g>
        );
    };

    return (
        <div>
            <Header />
            <div className="main-wrapper">
                <section className="form-section card">
                    <button className="exit-btn" onClick={() => navigate('/')}>Выйти</button>
                    <h3>Ввод координат</h3>
                    <p style={{color: 'orange'}}>{message}</p>
                    <form onSubmit={sendPoint}>
                        <div className="input-row">
                            <label>X:</label>
                            <button type="button" onClick={() => handleStep(x, setX, -1)}>-</button>
                            <input type="text" value={x} onChange={(e) => handleInputChange(e.target.value, setX)} />
                            <button type="button" onClick={() => handleStep(x, setX, 1)}>+</button>
                        </div>
                        <div className="input-row">
                            <label>Y:</label>
                            <button type="button" onClick={() => handleStep(y, setY, -1)}>-</button>
                            <input type="text" value={y} onChange={(e) => handleInputChange(e.target.value, setY)} />
                            <button type="button" onClick={() => handleStep(y, setY, 1)}>+</button>
                        </div>
                        <div className="input-row">
                            <label>R:</label>
                            <button type="button" onClick={() => {
                                const next = (parseFloat(r) || 0) - 1;
                                if (next >= -3 && next <= 5) dispatch(setRAction(next));
                            }}>-</button>

                            <input type="text" value={r} onChange={(e) => handleRChange(e.target.value)} />

                            <button type="button" onClick={() => {
                                const next = (parseFloat(r) || 0) + 1;
                                if (next >= -3 && next <= 5) dispatch(setRAction(next));
                            }}>+</button>
                        </div>
                        <div className="btn-group">
                            <button type="submit" style={{background: '#27ae60'}}>Отправить</button>
                            <button type="button" onClick={() => {
                                fetch('api/points', { method: 'DELETE', credentials: 'include' })
                                    .then(() => {
                                        dispatch(clearHistoryAction());
                                        setCurrentPage(1);
                                    })
                                    .catch(err => console.error("Ошибка при очистке", err));
                            }} style={{background: '#e74c3c'}}>Очистить</button>
                        </div>
                    </form>
                </section>

                <section className="graph-section">
                    <div className="card">
                        <svg ref={svgRef} width="300" height="300" onClick={handleGraphClick}>
                            {renderFigures()}
                            <line x1="0" y1="150" x2="300" y2="150" stroke="#333" />
                            <line x1="150" y1="0" x2="150" y2="300" stroke="#333" />
                            {history.filter(p => String(p.r) === String(r)).map((p, i) => (
                                <circle key={i} cx={150 + parseFloat(p.x) * K} cy={150 - parseFloat(p.y) * K} r="4" fill={p.hit ? "#27ae60" : "#e74c3c"} stroke="white" />
                            ))}
                        </svg>
                    </div>
                </section>

                <section className="table-section card">
                    <div className="pagination">
                        <button onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}>Назад</button>
                        <span> Страница {currentPage} </span>
                        <button onClick={() => setCurrentPage(currentPage + 1)}>Вперед</button>
                    </div>
                    <table>
                        <thead>
                            <tr><th>X</th><th>Y</th><th>R</th><th>Результат</th><th>Время</th><th>Мс</th></tr>
                        </thead>
                        <tbody>
                            {tableData.map((p, i) => (
                                <tr key={i}>
                                    <td>{p.x}</td><td>{p.y}</td><td>{p.r}</td>
                                    <td style={{ color: p.hit ? '#27ae60' : '#e74c3c', fontWeight: 'bold' }}>
                                        {p.hit ? 'Попадание' : 'Промах'}
                                    </td>
                                    <td>{p.checkTime}</td><td>{p.executionTime}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            </div>
            <Footer />
        </div>
    );
}

export default Main;