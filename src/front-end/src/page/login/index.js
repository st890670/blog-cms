import React, { useEffect, useState } from 'react'
import Api from 'utils/api'
import Login from 'page/login/login'
import Welcome from 'page/login/welcome'
export default () => {
  const [isManagementExist, setIsManagementExist] = useState(true)

  useEffect(() => {
    countManagement()
  }, [])

  async function countManagement () {
    const { data } = await Api.get({}, 'management', 'count')
    setIsManagementExist(data > 0)
  }

  return isManagementExist ? <Login /> : <Welcome reCountCallback={countManagement} />
}
