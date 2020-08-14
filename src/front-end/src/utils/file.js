export const base64ToImage = (base64FileContnet, fileName) => {
  const byteCharacters = window.atob(base64FileContnet)
  const byteNumbers = new Array(byteCharacters.length)

  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i)
  }

  const byteArray = new Uint8Array(byteNumbers)
  return new File([byteArray], fileName, { type: 'image/png' })
}
