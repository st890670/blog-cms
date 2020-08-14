import React, { useEffect, useRef, useState } from 'react'
import { useHistory } from 'react-router-dom'
import api from 'utils/api'
import { useMember } from 'reducer/member'
import { longToString } from 'utils/datetime'
import { checkRequired, formObjCallback, isEmptyObj } from 'utils/form'
import { toast } from 'react-toastify'
import UpdateWhisper from 'page/management/update-whisper'

export default () => {
  const history = useHistory()
  const [member, setMember] = useMember()
  const initFormRef = useRef({})
  const [form, setForm] = useState({
    account: '',
    name: '',
    modifiedDate: ''
  })
  const [alert, setAlert] = useState({
    name: ''
  })
  const [displayModal, setDisplayModal] = useState(false)

  useEffect(() => {
    loadData()
    // eslint-disable-next-line
  }, [])

  async function loadData () {
    const { data } = await api.get({}, 'management', member.id)
    initFormRef.current = data
    setForm(data)
  }

  async function handleUpdate () {
    const alertClone = checkRequired(form, alert, ['account', 'modifiedDate'])
    setAlert(alertClone)
    isEmptyObj(alertClone) && await update()
  }

  async function update () {
    await api.put({ name: form.name }, 'management', member.id)
    toast.success('更新成功，請重新登入')
    logout()
  }

  async function logout () {
    await api.post({}, 'logout')
    window.sessionStorage.removeItem('member')
    setMember({ id: '', account: '', name: '' })
    history.push('/login')
  }

  return (
    <>
      <div className='page'>
        <div className='page-description'>最後修改時間: {longToString(initFormRef.current.modifiedDate)}</div>

        <div className='form-row'>
          <div className='form-group col-6'>
            <label>帳號</label>
            <input
              type='text'
              className='form-control'
              defaultValue={initFormRef.current.account}
              readOnly
            />
          </div>
          <div className='form-group col-6'>
            <label>名稱</label>
            <input
              type='text'
              className='form-control'
              id='name'
              value={form.name}
              onChange={formObjCallback(setForm)}
            />
          </div>
        </div>

        <button
          className='jas-btn-danger w-100 mt-3'
          onClick={() => setDisplayModal(true)}
        >修改密碼
        </button>
        <button
          className='jas-btn-primary w-100 mt-3'
          onClick={handleUpdate}
        >更新並登出
        </button>

      </div>
      <UpdateWhisper managementId={member.id} state={[displayModal, setDisplayModal]} />
    </>
  )
}
