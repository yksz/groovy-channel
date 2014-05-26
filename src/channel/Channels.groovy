package channel

class Channels {
    class BreakException extends Exception {}

    static def select(Closure closure) {
        try {
            while (true) {
                Channels chans = new Channels()
                closure.delegate = chans
                closure()
                sleep(10)
            }
        } catch (BreakException e) {
        }
    }

    def when(Channel chan, Closure closure) {
        def msg
        synchronized (chan) {
            if (chan.peek() == null)
                return
            msg = chan.receive()
        }
        closure(msg)
    }

    def breaks() {
        throw new BreakException()
    }
}
