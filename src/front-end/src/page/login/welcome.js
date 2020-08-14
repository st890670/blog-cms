import React, { useState } from 'react'
import { checkRequired, formObjCallback, invalidClass, isEmptyObj, handleEnter } from 'utils/form'
import Api from 'utils/api'
import { toast } from 'react-toastify'

export default ({ reCountCallback }) => {
  const [content, setContent] = useState({
    account: '',
    name: '',
    whisper: '',
    reWhisper: ''
  })

  const [alert, setAlert] = useState({
    account: '',
    name: '',
    whisper: '',
    reWhisper: ''
  })

  async function handleSave () {
    let alertClone = checkRequired(content, alert)
    const { whisperAlert, reWhisperAlert } = checkWhisperAndReturnAlert(alertClone)
    alertClone = { ...alertClone, whisper: whisperAlert, reWhisper: reWhisperAlert }
    setAlert(alertClone)

    if (isEmptyObj(alertClone)) {
      await save()
    }
  }

  function checkWhisperAndReturnAlert (alert) {
    return content.whisper !== content.reWhisper ? { whisperAlert: '密碼不一致', reWhisperAlert: '密碼不一致' } : { whisperAlert: alert.whisper, reWhisperAlert: alert.reWhisper }
  }

  async function save () {
    const params = { ...content }
    delete content.reWhisper
    await Api.post(params, 'management')
    toast.success('成功建立帳號')
    reCountCallback()
  }

  return (
    <div className='welcome'>
      <div className='welcome-box'>
        <div className='welcome-box-title'>建立管理者帳號</div>
        <input
          id='account'
          className={`jas-input-secondary mt-3 ${invalidClass(alert.account)}`}
          type='text'
          placeholder='請輸入帳號'
          onChange={formObjCallback(setContent)}
        />
        <div className='jas-alert-msg'>{alert.account}</div>

        <input
          id='name'
          className={`jas-input-secondary mt-1 ${invalidClass(alert.name)}`}
          type='text'
          placeholder='請輸入管理者名稱'
          onChange={formObjCallback(setContent)}
        />
        <div className='jas-alert-msg'>{alert.name}</div>

        <input
          id='whisper'
          className={`jas-input-secondary mt-1 ${invalidClass(alert.whisper)}`}
          placeholder='請輸入密碼'
          type='password'
          onChange={formObjCallback(setContent)}
        />
        <div className='jas-alert-msg'>{alert.whisper}</div>

        <input
          id='reWhisper'
          className={`jas-input-secondary mt-1 ${invalidClass(alert.reWhisper)}`}
          placeholder='再次輸入密碼'
          type='password'
          onChange={formObjCallback(setContent)}
          onKeyPress={e => handleEnter(e, handleSave)}
        />
        <div className='jas-alert-msg'>{alert.reWhisper}</div>

        <button
          className='jas-btn-primary mt-3 w-100'
          onClick={handleSave}
        >建立帳號
        </button>

      </div>
    </div>
  )
}
