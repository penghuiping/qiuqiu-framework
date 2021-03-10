import { WsMessageHandler, WsMsg } from '@/utils/ws'
import { BaseVue } from '@/BaseVue'

class NotifyTextMsg extends WsMsg {
   content: string

   // eslint-disable-next-line @typescript-eslint/camelcase
   constructor (msg_id: string, action: string, timestamp: number, content: string) {
     super(msg_id, action, timestamp)
     this.content = content
   }
}

class NotifyTextHandler implements WsMessageHandler {
  private context: BaseVue

  constructor (context: BaseVue) {
    this.context = context
  }

  getAction (): string {
    return 'notify_text'
  }

  handle (ws: WebSocket, msg: WsMsg): void {
    const msg0 = msg as NotifyTextMsg
    this.context.$message({
      type: 'success',
      message: msg0.content
    })
  }
}

export {
  NotifyTextHandler
}
