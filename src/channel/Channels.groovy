package channel

class Channels {
    class BreakException extends Exception {}

    static def forSelect(Closure closure) {
        def cloneClosure = closure.clone() // thread safe
        cloneClosure.delegate = new Channels()
        try {
            while (true) {
                cloneClosure()
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
