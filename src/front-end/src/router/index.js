import React, { useEffect } from 'react'
import { Route, Switch, Redirect, matchPath, useLocation, useHistory } from 'react-router-dom'
import { OPEN_LAYOUT, LOGGED_IN_LAYOUT } from 'config/router'
import { LOGIN_ROUTE_CONFIG } from 'page/login/config'
import { ERROR_404_ROUTE_CONFIG } from 'page/error/config'
import { useMember } from 'reducer/member'
import PrivateRoute from 'router/private-route'

export default () => {
  const [member, setMember] = useMember()
  const currentPathName = useLocation().pathname
  const history = useHistory()

  useEffect(() => {
    if (!member.id) {
      const memberInSessionStorage = window.sessionStorage.getItem('member')
      if (memberInSessionStorage) {
        setMember(JSON.parse(memberInSessionStorage))
        matchPath(LOGIN_ROUTE_CONFIG.path, currentPathName) ? LOGGED_IN_LAYOUT.length && history.push(LOGGED_IN_LAYOUT[0].path) : history.push(currentPathName)
      }
    }
    // eslint-disable-next-line
  }, [])

  return (
    <Switch>
      {OPEN_LAYOUT.map((page, i) =>
        <Route
          key={i}
          {...page}
        />
      )}

      {LOGGED_IN_LAYOUT.map((page, i) =>
        <PrivateRoute
          key={i}
          auth={!!member.id}
          {...page}
        />
      )}

      <Redirect from='/' to={LOGIN_ROUTE_CONFIG.path} />
      <Redirect from='*' to={ERROR_404_ROUTE_CONFIG.path} />
    </Switch>
  )
}
