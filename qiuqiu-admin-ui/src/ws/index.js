import { WsMsg } from '@/utils/ws'
class NotifyTextMsg extends WsMsg {
  // eslint-disable-next-line @typescript-eslint/camelcase
  constructor (msg_id, action, timestamp, content) {
    super(msg_id, action, timestamp)
    this.content = content
  }
}
class NotifyTextHandler {
  constructor (context) {
    this.context = context
  }

  getAction () {
    return 'notify_text'
  }

  handle (ws, msg) {
    const msg0 = msg
    this.context.$message({
      type: 'success',
      message: msg0.content
    })
  }
}
export { NotifyTextHandler }
// # sourceMappingURL=index.js.map
