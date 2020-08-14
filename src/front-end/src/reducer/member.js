
import { useSelector, useDispatch } from 'react-redux'

const REDUCER_NAME = 'MEMBER'
const initState = { id: '', account: '', name: '' }

const reducer = (state = initState, action) => {
  switch (action.type) {
    case REDUCER_NAME: {
      return action[REDUCER_NAME]
    }
    default:
      return state
  }
}

export const memberReducer = { [REDUCER_NAME]: reducer }

export const useMember = () => {
  const dispatch = useDispatch()
  const setDispatch = payload => {
    dispatch({
      type: REDUCER_NAME,
      [REDUCER_NAME]: payload
    })
  }
  return [useSelector(state => state[REDUCER_NAME]), setDispatch]
}
