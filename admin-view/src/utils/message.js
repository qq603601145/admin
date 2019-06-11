export function success(message) {
  const data = {
    type: 'success',
    message: message || '操作成功'
  }
  return data
}

export function info(obj, message) {
  obj.$message({
    type: 'info',
    message: message || '已取消'
  })
}
