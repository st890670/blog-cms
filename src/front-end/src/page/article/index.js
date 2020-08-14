import React, { useEffect, useState } from 'react'
import { Link, useHistory, useLocation } from 'react-router-dom'
import { ARTICLE_ROUTE_CONFIG, ARTICLE_ADD_ROUTE_CONFIG } from 'page/article/config'
import api from 'utils/api'
import { formObjCallback } from 'utils/form'
import { longToString } from 'utils/datetime'

export default () => {
  const history = useHistory()
  const location = useLocation()
  const [articles, setArticles] = useState([])
  const [ready, setReady] = useState(false)
  const [params, setParams] = useState({
    title: '',
    hashtag: ''
  })

  useEffect(() => {
    (async () => {
      await initData()
      !ready && setReady(true)
    })()
    // eslint-disable-next-line
  }, [location])

  async function initData () {
    const queryParams = new URLSearchParams(location.search)
    const params = {}
    queryParams.forEach((value, key) => { params[key] = value })
    const { data } = await api.get(params, 'articles')
    setArticles(data)
  }

  function search () {
    const queryParams = new URLSearchParams()
    Object.keys(params).forEach(key => queryParams.append(key, params[key]))
    history.push(`${ARTICLE_ROUTE_CONFIG.path}/?${queryParams}`)
  }

  return ready && (
    <div className='page'>
      <div className='page-search'>
        <div className='row'>
          <div className='col-3'>
            <input
              id='title'
              className='form-control'
              placeholder='請輸入文章標題'
              onChange={formObjCallback(setParams)}
            />
          </div>
          <div className='col-3'>
            <input
              id='hashtag'
              className='form-control'
              placeholder='請輸入想搜尋的hashtag'
              onChange={formObjCallback(setParams)}
            />
          </div>
          <div className='col-3' />
          <div className='col-3 d-flex'>
            <button
              className='btn btn-primary w-100 mx-1'
              onClick={search}
            >查詢
            </button>
            <button
              className='btn btn-success w-100 mx-1'
              onClick={() => history.push(ARTICLE_ADD_ROUTE_CONFIG.path)}
            >新增文章
            </button>
          </div>
        </div>
      </div>

      <div className='page-result'>{articles.length ? `總共有${articles.length}筆資料` : '找不到文章，請更換搜尋條件或新增文章'}</div>

      <table className={articles.length ? 'table' : 'd-none'}>
        <thead>
          <tr>
            <th>文章標題</th>
            <th>Hashtags</th>
            <th>最後修改時間</th>
          </tr>
        </thead>
        <tbody>
          {
            articles.map((rowData, index) => (
              <tr key={index}>
                <td>
                  <Link to={`/article/${rowData.id}`}>
                    {rowData.title}
                  </Link>
                </td>
                <td>{rowData.hashtags}</td>
                <td>{longToString(rowData.modifiedDate)}</td>
              </tr>
            ))
          }
        </tbody>
      </table>

    </div>
  )
}
