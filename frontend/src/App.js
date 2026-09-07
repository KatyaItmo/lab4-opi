import React from 'react';
import './App.css';
import './style.css';
import { Provider } from 'react-redux';
import store from './redux/store';
import Home from './pages/Home.js';
import Main from './pages/Main.js';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';


const router = createBrowserRouter([
	{
		path: "/",
		element: <Home />
	},
	{
		path: "/main",
		element: <Main />
	},
], {
	basename: "/WebLab4"
});

function App() {
	return (
		<Provider store={store}>
            <RouterProvider router={router} />
        </Provider>
	);
}

export default App;
