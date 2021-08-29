interface WsMessageHandler {
  getAction(): string;

  handle(ws: WebSocket, msg: WsMsg, token: string): void;
}

class WsMsg {
   msg_id: string
   action: string
   timestamp: number

   // eslint-disable-next-line @typescript-eslint/camelcase
   constructor (msg_id: string, action: string, timestamp: number) {
     // eslint-disable-next-line @typescript-eslint/camelcase
     this.msg_id = msg_id
     this.action = action
     this.timestamp = timestamp
   }
}

class RequestAuthInfoHandler implements WsMessageHandler {
  getAction (): string {
    return 'request_auth_info'
  }

  handle (ws: WebSocket, msg: WsMsg, token: string): void {
    // ws.send(JSON.stringify({
    //   // eslint-disable-next-line @typescript-eslint/camelcase
    //   msg_id: msg.msg_id,
    //   action: 'submit_auth_info',
    //   token: token,
    //   timestamp: new Date().getTime()
    // }))
  }
}

class ReplyAuthInfoHandler implements WsMessageHandler {
  getAction (): string {
    return 'reply_auth_info'
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  handle (ws: WebSocket, msg: WsMsg, token: string): void {
    ws.send(JSON.stringify({
      // eslint-disable-next-line @typescript-eslint/camelcase
      msg_id: msg.msg_id,
      action: 'ack',
      // eslint-disable-next-line @typescript-eslint/camelcase
      reply_action: 'reply_auth_info',
      timestamp: new Date().getTime()
    }))
  }
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
class WebSocket0 {
   url: string
   token: string
   pingTimeout: number
   handlers = new Map<string, WsMessageHandler>()
   static ws: WebSocket | undefined
   timer: number | undefined

   constructor (token: string, url: string, pingTimeout: number) {
     this.url = url
     this.token = token
     this.pingTimeout = pingTimeout
     this.initWS()
   }

   registerHandler (handler: WsMessageHandler) {
     this.handlers.set(handler.getAction(), handler)
   }

   clear () {
     if (WebSocket0.ws) {
       WebSocket0.ws.close()
       clearInterval(this.timer)
     }
   }

   initWS () {
     const pingTimeout = this.pingTimeout
     const handlers = this.handlers
     const token = this.token
     const url = this.url
     this.init0(url, token, pingTimeout, handlers)
   }

   init0 (url: string, token: string, pingTimeout: number, handlers: Map<string, WsMessageHandler>) {
     console.log('url:', url, 'token:', token, 'pingTimeout:', pingTimeout)
     WebSocket0.ws = new WebSocket(url)
     WebSocket0.ws.onopen = function () {
       console.log('ws connection open')
       // 发送ws心跳包
       WebSocket0.prototype.timer = setInterval(() => {
         this.send(JSON.stringify({
           // eslint-disable-next-line @typescript-eslint/camelcase
           msg_id: WebSocket0.prototype.uuid(),
           action: 'ping'
         }))
       }, pingTimeout)
     }
     WebSocket0.ws.onmessage = function (e: MessageEvent) {
       const obj = JSON.parse(e.data)
       console.log('ws receive message:{}', obj)
       const handler0 = handlers.get(obj.action)
       if (handler0) {
         handler0.handle(this, obj, token)
       }
     }
     WebSocket0.ws.onclose = function (e: CloseEvent) {
       console.log('ws connection close. ..', e)
       if (e.code !== 1000) {
         console.log('3秒后开始重连...')
         clearInterval(WebSocket0.prototype.timer)
         setTimeout(() => {
           WebSocket0.prototype.init0(url, token, pingTimeout, handlers)
         }, 3000)
       }
     }
     WebSocket0.ws.onerror = function (e: Event) {
       console.log('ws connection error:', e)
     }
     const handler0 = new RequestAuthInfoHandler()
     const handler1 = new ReplyAuthInfoHandler()
     handlers.set(handler0.getAction(), handler0)
     handlers.set(handler1.getAction(), handler1)
   }

   uuid () {
     const s: string[] = []
     const hexDigits = '0123456789abcdef'
     for (let i = 0; i < 36; i++) {
       s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
     }
     s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
     s[19] = hexDigits.substr((parseInt(s[19]) & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
     s[8] = s[13] = s[18] = s[23] = '-'

     const uuid = s.join('')
     return uuid
   }
}

export {
  WebSocket0,
  WsMessageHandler,
  WsMsg
}
