import React, { useState } from 'react'
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap'
import api from 'utils/api'
import { checkRequired, formObjCallback, isEmptyObj } from 'utils/form'
import { toast } from 'react-toastify'

export default ({ managementId, state }) => {
  const [display, setDisplay] = state
  const [form, setForm] = useState({
    whisper: '',
    rewhisper: ''
  })
  const [alert, setAlert] = useState({
    whisper: '',
    rewhisper: ''
  })

  const toggle = () => setDisplay(!display)

  async function handleUpdateWhisper () {
    const alertClone = validWhisper()
    setAlert(alertClone)

    if (isEmptyObj(alertClone)) {
      await updateWhisper()
    }
  }

  function validWhisper () {
    return form.whisper !== form.rewhisper ? { whisper: form.whisper ? '密碼不一致' : '必填', rewhisper: form.rewhisper ? '密碼不一致' : '必填' } : checkRequired(form, alert)
  }

  async function updateWhisper () {
    await api.put({ whisper: form.whisper }, 'management', managementId, 'whisper')
    toast.success('修改密碼成功')
    toggle()
  }

  return (
    <Modal isOpen={display} toggle={toggle} size='sm'>
      <ModalHeader toggle={toggle}>修改密碼</ModalHeader>
      <ModalBody>
        <div className='form-row'>
          <div className='form-group col-12'>
            <input
              id='whisper'
              type='password'
              className={`form-control ${alert.whisper ? 'is-invalid' : ''}`}
              placeholder='請輸入新密碼'
              onChange={formObjCallback(setForm)}
            />
            <div className='invalid-feedback'>{alert.whisper}</div>
          </div>

          <div className='col-12'>
            <input
              id='rewhisper'
              type='password'
              className={`form-control ${alert.rewhisper ? 'is-invalid' : ''}`}
              placeholder='再次輸入新密碼'
              onChange={formObjCallback(setForm)}
            />
            <div className='invalid-feedback'>{alert.rewhisper}</div>
          </div>

        </div>
      </ModalBody>
      <ModalFooter>
        <button
          className='jas-btn-danger'
          onClick={handleUpdateWhisper}
        >確定修改
        </button>
        <button
          className='jas-btn-secondary'
          onClick={toggle}
        >
          取消
        </button>
      </ModalFooter>
    </Modal>
  )
}
