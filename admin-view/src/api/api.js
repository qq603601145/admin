import request from '@/utils/request'

export function getRequest(url) {
  return request({
    url: url,
    method: 'get'
  })
}

export function postRequest(url, data) {
  return request({
    url: url,
    method: 'post',
    data: data
  })
}

export function deleteRequest(url) {
  return request({
    url: url,
    method: 'delete'
  })
}
