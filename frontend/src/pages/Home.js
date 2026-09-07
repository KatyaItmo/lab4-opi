import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header.js";
import Footer from "../components/Footer.js";
import '../style.css';

function Home() {
    const [isRegisterMode, setIsRegisterMode] = useState(false);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const changeMode = () => {
        setIsRegisterMode(!isRegisterMode);
        setMessage("");
    };

    async function handleSubmit(e) {
        e.preventDefault();
        
        const user = {
            login: e.target.login.value,
            password: e.target.password.value
        };
        
        const url = isRegisterMode ? 'api/auth/register' : 'api/auth/login';
        
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
                credentials: 'include'
            });
            
            const data = await response.json();
            
            if (response.ok) {
                if (isRegisterMode) {
                    setMessage(data.message || "Успешная регистрация!");
                    setIsRegisterMode(false);
                } else {
                    navigate('/main');
                }
            } else {
                setMessage(data.message || "Ошибка доступа");
            }
        } catch (error) {
            setMessage("Нет связи с сервером");
        }
    }

    return (
        <div>
            <Header />
            <main>
                <form className="auth-form" onSubmit={handleSubmit}>
                    {message && <p className="status-msg">{message}</p>}
                    <h2>{isRegisterMode ? "Регистрация" : "Авторизация"}</h2>
                    
                    <div className="form-row">
                        <label className="input-label">Логин:</label>
                        <input type="text" name="login" className="text-input" required />
                    </div>
                    
                    <div className="form-row">
                        <label className="input-label">Пароль:</label>
                        <input type="password" name="password" className="text-input" required />
                    </div>
                    
                    <div className="form-row">
                        <button type="submit">{isRegisterMode ? "Зарегистрироваться" : "Войти"}</button>
                    </div>
                    
                    <div className="form-row">
                        <button type="button" className="link-btn" onClick={changeMode}>
                            {isRegisterMode ? "Уже есть аккаунт? Войти" : "Нет аккаунта? Зарегистрироваться"}
                        </button>
                    </div>
                </form>
            </main>
            <Footer />
        </div>
    );
}

export default Home;