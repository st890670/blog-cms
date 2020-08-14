export const formObjCallback = setFunc => {
  return event => {
    const dom = event.target
    setFunc(prev => ({ ...prev, [dom.id]: dom.value }))
  }
}

export const invalidClass = alert => (alert ? 'jas-input-invalid' : '')

export const checkRequired = (formObj, alertObj, ignoreList = []) => {
  const alertClone = { ...alertObj }
  Object.keys(alertObj)
    .filter(key => !ignoreList.includes(key))
    .forEach(key => { alertClone[key] = formObj[key] ? '' : '必填' })
  return alertClone
}

export const isEmptyObj = obj => (Object.keys(obj).every(key => !obj[key]))

export const handleEnter = (event, func = () => { }) => event.key === 'Enter' && func()
