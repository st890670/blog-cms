import { faTimes } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { ARTICLE_ROUTE_CONFIG } from 'page/article/config'
import React, { useState } from 'react'
import { useHistory } from 'react-router-dom'
import { toast } from 'react-toastify'
import UploadSvg from 'svg/upload.svg'
import api from 'utils/api'
import { checkRequired, formObjCallback, isEmptyObj } from 'utils/form'
import Markdown from 'component/markdown'

export default () => {
  const history = useHistory()

  const [form, setForm] = useState({
    title: '',
    content: '',
    hashtags: [],
    image: null
  })

  const [alert, setAlert] = useState({
    title: '',
    content: '',
    hashtags: '',
    image: ''
  })

  const [currentHashTag, setCurrentHashTag] = useState('')
  const [hashtags, setHashtags] = useState([])

  function handleAddingHashTag(event) {
    if (event.key === 'Enter') {
      const value = event.target.value.toLowerCase().trim()
      !hashtags.includes(value) && !!value && addHashTag(value)
    }
  }

  function addHashTag(value) {
    setHashtags(() => {
      const hashTagsClone = [...hashtags]
      hashTagsClone.push(value)
      return hashTagsClone
    })
    setCurrentHashTag('')
  }

  function handleRemovingHashTag(index) {
    const clone = [...hashtags]
    clone.splice(index, 1)
    setHashtags(clone)
  }

  function handleCheckRequired(event, alertMsg = '必填') {
    const dom = event.target
    setAlert(prev => ({ ...prev, [dom.id]: dom.value ? '' : alertMsg }))
  }

  function handleUpload(event) {
    const files = event.target.files
    checkAndSetFile(files)
  }

  function handleDropUpload(event) {
    event.preventDefault()
    const files = event.dataTransfer.files
    checkAndSetFile(files)
  }

  function checkAndSetFile(files) {
    if (files.length) {
      if (files[0].size > 10 * 1024 * 1024) {
        setAlert(prev => ({ ...prev, image: '檔案大小超過10MB' }))
      } else {
        setAlert(prev => ({ ...prev, image: '' }))
        setForm(prev => ({ ...prev, image: files[0] }))
      }
    }
  }

  async function handleSave() {
    const alertClone = checkRequired(form, alert, ['hashtags', 'image'])
    alertClone.hashtags = hashtags.length ? '' : '至少標記一個HashTag'
    alertClone.image = form.image?.size > 0 ? '' : '必須上傳圖片'
    setAlert(alertClone)
    if (isEmptyObj(alertClone)) {
      await save()
    }
  }

  async function save() {
    const { data: articleId } = await api.post({ title: form.title, content: form.content, hashtags: [...hashtags] }, 'article')
    await uploadAttach(articleId)
    toast.success('已新增文章')
    goBack()
  }

  async function uploadAttach(articleId) {
    const formData = new FormData()
    formData.append('article', form.image, form.image.name)
    await api.put(formData, 'article', 'attach', articleId)
  }

  const goBack = () => history.push(ARTICLE_ROUTE_CONFIG.path)

  return (
    <div className='page'>
      <div className='form-row'>
        <div className='form-group col-12'>
          <label>文章標題</label>
          <input
            type='text'
            className={`form-control ${alert.title ? 'is-invalid' : ''}`}
            id='title'
            value={form.title}
            onChange={formObjCallback(setForm)}
            onBlur={handleCheckRequired}
          />
          <div className='invalid-feedback'>{alert.title}</div>
        </div>

        <div className='form-group col-12'>

          <label
            htmlFor='uploadInput'
            className={`article-upload ${alert.image ? 'article-upload-alert' : ''}`}
            onDragOver={e => e.preventDefault()}
            onDrop={handleDropUpload}
          >
            {
              form.image
                ? (
                  <img
                    className='article-upload-icon'
                    src={URL.createObjectURL(form.image)}
                    alt='預覽圖片'
                  />
                )
                : (
                  <img
                    className='article-upload-icon'
                    src={UploadSvg}
                    alt='upload svg'
                  />
                )
            }
            <div className='mb-1'>拖拉圖片就可以上傳囉</div>
            <div className='jas-btn-info article-upload-btn'>上傳圖片</div>
          </label>
          <input
            id='uploadInput'
            type='file'
            hidden
            onChange={handleUpload}
          />
          <div className='invalid-feedback d-flex'>{alert.image}</div>
        </div>

        <div className='form-group col-6'>
          <label>文章內容</label>
          <textarea
            id='content'
            className={`article-content ${alert.content ? 'is-invalid' : ''}`}
            value={form.content}
            onChange={formObjCallback(setForm)}
            onBlur={e => handleCheckRequired(e, '內容不得為空')}
          />
          <div className='invalid-feedback'>{alert.content}</div>
        </div>

        <div className='form-group col-6'>
          <label>即時預覽</label>
          <Markdown className='article-content' source={form.content} />
        </div>

        <div className='form-group col-12'>
          <label>HashTag</label>
          <div className={hashtags.length ? 'mt-1 mb-3' : ''}>
            {
              hashtags.map((hashTag, index) => (
                <span
                  key={hashTag}
                  className='d-inline-block position-relative jas-badge-primary mr-3'
                >
                  <div
                    className='article-remove'
                    onClick={() => handleRemovingHashTag(index)}
                  >
                    <FontAwesomeIcon icon={faTimes} />
                  </div>
                  {hashTag}
                </span>
              ))
            }
          </div>

          <input
            type='text'
            className={`form-control ${alert.hashtags ? 'is-invalid' : ''}`}
            placeholder='按下Enter新增HashTag'
            value={currentHashTag}
            onChange={e => setCurrentHashTag(e.target.value)}
            onKeyDown={handleAddingHashTag}
          />
          <div className='invalid-feedback'>{alert.hashtags}</div>
        </div>
      </div>

      <button
        className='jas-btn-primary w-100 mt-3'
        onClick={handleSave}
      >新增
      </button>
      <button
        className='jas-btn-secondary w-100 mt-3'
        onClick={goBack}
      >取消
      </button>
    </div>
  )
}
