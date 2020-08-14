import { combineReducers, createStore } from 'redux'
import { memberReducer } from 'reducer/member'

export default createStore(combineReducers(memberReducer))
