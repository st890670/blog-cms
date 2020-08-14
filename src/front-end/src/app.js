import React, { useEffect, useState } from 'react'
import Router from 'router'
import Menu from 'layout/menu'
import { useMember } from 'reducer/member'

export default () => {
  const member = useMember()[0]
  const [isLoggedIn, setIsLoggedIn] = useState(false)

  useEffect(() => {
    setIsLoggedIn(!!member.id)
  }, [member])

  return (
    <div className='d-flex w-100 h-100'>
      <div className={isLoggedIn ? 'w-25' : 'd-none'}>
        <Menu />
      </div>
      <div className={`${isLoggedIn ? 'w-75' : 'w-100'} h-100 overflow-auto`}>
        <Router />
      </div>
    </div>
  )
}
