export function getToken(tokenLen){
    tokenLen = tokenLen || 16;
    let token = '';
    let chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    let charsLen = chars.length
    for(let i = 0; i < tokenLen; i++){
        token += chars.charAt(Math.floor(Math.random() * charsLen));
    }
    return token;
}