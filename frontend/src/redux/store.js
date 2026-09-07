import { createStore } from 'redux';

const initialState = {
    history: [],
    currentR: "1"
};

const SET_HISTORY = 'SET_HISTORY';
const ADD_POINTS = 'ADD_POINTS';
const CLEAR_HISTORY = 'CLEAR_HISTORY';
const SET_R = 'SET_R';

function rootReducer(state = initialState, action) {
    switch (action.type) {
        case SET_HISTORY:
            return { ...state, history: action.payload };
        case ADD_POINTS:

            return { ...state, history: [...action.payload, ...state.history] };
        case CLEAR_HISTORY:
            return { ...state, history: [] };
        case SET_R:
            return { ...state, currentR: action.payload };
        default:
            return state;
    }
}

export const setHistoryAction = (data) => ({ type: SET_HISTORY, payload: data });
export const addPointsAction = (data) => ({ type: ADD_POINTS, payload: data });
export const clearHistoryAction = () => ({ type: CLEAR_HISTORY });
export const setRAction = (r) => ({ type: SET_R, payload: r });

const store = createStore(rootReducer);

export default store;