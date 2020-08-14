import React from 'react'
import { useHistory } from 'react-router-dom'
import { LOGGED_IN_LAYOUT } from 'config/router'
import { useMember } from 'reducer/member'
import api from 'utils/api'

export default () => {
  const [member, setMember] = useMember()
  const history = useHistory()

  async function logout () {
    await api.post({}, 'logout')
    window.sessionStorage.removeItem('member')
    setMember({ id: '', account: '', name: '' })
    history.push('/login')
  }

  return (
    <div className='menu'>
      <div className='menu-header'>
        <h2 className='cur-p' onClick={() => history.push('/articles')}>Jason隨筆寫寫後台</h2>
        <div className='menu-header-subtitle'>Welcome Back {member.name}!</div>
      </div>
      <div className='menu-body'>
        {LOGGED_IN_LAYOUT.filter(config => config.isMenu).map((config, index) => (
          <div
            className='menu-body-item'
            key={index}
            onClick={() => history.push(config.path)}
          >
            {config.name}
          </div>
        ))}
      </div>
      <div
        className='menu-footer'
        onClick={logout}
      >登出
      </div>
    </div>
  )
}
