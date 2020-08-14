import React, { useState } from 'react'
import { useHistory } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUserAlt } from '@fortawesome/free-solid-svg-icons'
import { useMember } from 'reducer/member'
import { formObjCallback, handleEnter } from 'utils/form'
import Api from 'utils/api'
import { toast } from 'react-toastify'

export default () => {
  const history = useHistory()
  const setMember = useMember()[1]
  const [content, setContent] = useState({
    account: '',
    whisper: ''
  })

  async function login () {
    try {
      const { data } = await Api.post(content, 'login')
      setMember(data)
      window.sessionStorage.setItem('member', JSON.stringify(data))
      history.push('/articles')
    } catch (e) {
      toast.error(e.response?.data || '登入失敗')
    }
  }

  return (
    <div className='login'>
      <div className='login-box'>
        <h2>後台登入</h2>
        <div className='login-box-img mt-3'>
          <FontAwesomeIcon icon={faUserAlt} />
        </div>
        <input
          className='jas-input-secondary mt-3 w-100'
          type='text'
          id='account'
          placeholder='帳號'
          autoFocus
          value={content.account}
          onChange={formObjCallback(setContent)}
        />
        <input
          className='jas-input-secondary mt-1 w-100'
          type='password'
          id='whisper'
          placeholder='密碼'
          value={content.whisper}
          onChange={formObjCallback(setContent)}
          onKeyDown={e => handleEnter(e, login)}
        />
        <button
          className='jas-btn-primary mt-3 w-100'
          onClick={login}
        >登入
        </button>
      </div>
    </div>
  )
}
