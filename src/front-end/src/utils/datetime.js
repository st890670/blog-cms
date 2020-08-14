import moment from 'moment'
const DEFAULT_FORMAT = 'YYYY-MM-DD hh:mm:ss'

export const longToIso = long => moment(Number(long)).format()
export const longToString = (long, format = DEFAULT_FORMAT) => moment(Number(long)).format(format)
export const longToDate = long => moment(Number(long)).toDate()

export const isoToDate = iso => moment.utc(iso).toDate()
export const isoToString = (iso, format = DEFAULT_FORMAT) => moment.utc(iso).format(format)
export const isoToLong = iso => moment.utc(iso).valueOf()

export const dateToIso = date => moment.utc(date).format()
export const dateToString = (date, format = DEFAULT_FORMAT) => moment(date).format(format)
export const dateToLong = date => moment(date).valueOf()

export const stringToIso = string => moment(string).format()
export const stringToDate = string => moment(string).toDate()
export const stringToLong = string => moment(string).valueOf()
