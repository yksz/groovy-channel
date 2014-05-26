def chan = new channel.Channel()
def quit = new channel.Channel()
Thread.start {
    sleep(1000)
    chan.send "Hello"
}
Thread.start {
    quit << "quit"
}

channel.Channels.select {
    when(chan) { msg ->
        println msg
    }
    when(quit) { msg ->
        println msg
        breaks()
    }
}
println "Finish!"
