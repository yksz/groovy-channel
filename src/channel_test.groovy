def chan = new channel.Channel()
Thread.start {
    chan.send "Hello"
}
Thread.start {
    chan << "World"
}
println chan.receive()
println chan.receive()
