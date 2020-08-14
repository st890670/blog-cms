class StoragePlus {
    constructor(storage) {
        this._storage = storage
    }

    json(name) {
        const __EXIST__ = () => !!(this._storage[name] && JSON.parse(this._storage[name]))
        const __SET__ = (json) => this._storage[name] = JSON.stringify(json)

        return {
            exist: __EXIST__,
            remove: () => this._storage.removeItem(name),
            get: () => JSON.parse(this._storage[name]),
            set: __SET__,
            append: (column, val) => {
                let json = __EXIST__() ? JSON.parse(this._storage[name]) : {}
                json[column] = val

                __SET__(json)
            }
        }
    }

    with(name) {
        const __EXIST__ = () => !!this._storage[name]
        const __SET__ = (val) => this._storage[name] = val

        return {
            exist: __EXIST__,
            remove: () => this._storage.removeItem(name),
            get: () => __EXIST__() ? this._storage[name] : '',
            set: __SET__
        }
    }
}


export const local = new StoragePlus(localStorage)
export const session = new StoragePlus(sessionStorage)