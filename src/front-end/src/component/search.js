import React, { useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from '@fortawesome/free-solid-svg-icons'

export default () => {
  const [expand, setExpand] = useState(false)

  function handleSearch () {
    expand ? search() : setExpand(true)
  }

  function search () {
    // TODO: 搜尋的商業邏輯
  }

  return (
    <div className={expand ? 'search' : 'search search-not-expand'}>
      <input
        className={`search-input ${expand ? '' : 'd-none'}`}
        placeholder='請輸入搜索的關鍵字'
      />
      <FontAwesomeIcon
        icon={faSearch}
        className='search-icon'
        onClick={handleSearch}
      />
    </div>
  )
}
