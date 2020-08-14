import React from 'react'
import { Redirect, Route } from 'react-router-dom'
import { ERROR_404_ROUTE_CONFIG } from 'page/error/config'

export default ({ component: Component, ...rest }) => {
  return (
    <Route
      {...rest}
      render={props => rest.auth ? <Component {...props} /> : <Redirect to={ERROR_404_ROUTE_CONFIG.path} />}
    />
  )
}
