import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import App from 'app'
import store from 'reducer/root-reducer'
import { Provider } from 'react-redux'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import 'styles/main.scss'

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter basename='/'>
      <ToastContainer />
      <App />
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
)
