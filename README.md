What is this?
==============

This is a golang channel implemented with groovy.

Example
=======

```groovy
def chan = new channel.Channel()
Thread.start {
    chan.send "Hello"
}
Thread.start {
    chan << "World"
}
println chan.receive()
println chan.receive()
```

When used golang's `for select`

```groovy
def chan = new channel.Channel(2)
def quit = new channel.Channel()
Thread.start {
    chan.send "Hello"
    sleep(1000)
    chan.send "World"
}
Thread.start {
    quit << "quit"
}

channel.Channels.forSelect {
    when(chan) { msg ->
        println msg
    }
    when(quit) { msg ->
        println msg
        breaks()
    }
}
println "Finish!"
```
