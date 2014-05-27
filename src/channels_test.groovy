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
