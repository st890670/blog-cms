import Axios from 'axios'
class Api {
  constructor () {
    this._config = {
      baseURL: `${this._URL}/api`,
      withCredentials: true
    }
  }

  get _URL () {
    return `${window.location.protocol}//${window.location.hostname}:${this._PORT}`
  }

  get _PORT () {
    return window.location.hostname === 'localhost' ? '8001' : window.location.port
  }

  pathFactory (...paths) {
    return paths.reduce((urlStr, path) => {
      const url = urlStr += `/${path}`
      return url
    }, '')
  }

  get _INSTANCE () {
    const axios = this._axios || Axios.create({
      ...this._config,
      headers: {
        account: this._ACCOUNT,
        uuid: this._UUID
      }
    })

    axios.interceptors.response.use(response => response, error => {
      if(!error.response || error.response.status ===  404 ){
         window.location.href = `${window.location.protocol}//${window.location.hostname}:${window.location.port}/404`
      }

      if(error.response.status ===  500 ){
        window.location.href = `${window.location.protocol}//${window.location.hostname}:${window.location.port}/500`
     }
      return Promise.reject(error)
    })

    return axios
  }

  get _UUID () {
    return JSON.parse(window.sessionStorage.getItem('member'))?.uuid || ''
  }

  get _ACCOUNT () {
    const name = JSON.parse(window.sessionStorage.getItem('member'))?.name || ''
    return name ? encodeURIComponent(name) : ''
  }

  get (params, ...paths) {
    this._config.params = params
    return this._INSTANCE.get(this.pathFactory(...paths), this._config)
  }

  delete (params, ...paths) {
    this._config.params = params
    return this._INSTANCE.delete(this.pathFactory(...paths), this._config)
  }

  post (params, ...paths) {
    return this._INSTANCE.post(this.pathFactory(...paths), params, this._config)
  }

  put (params, ...paths) {
    return this._INSTANCE.put(this.pathFactory(...paths), params, this._config)
  }
}

export default new Api()
