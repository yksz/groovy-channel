What is this?
==============

This is a golang channel implemented with groovy.

Example1
========

__Groovy__ channel's example

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

__Go__ channel's example

```go
package main

import (
	"fmt"
)

func main() {
	ch := make(chan string)
	go func() {
		ch <- "Hello"
	}()
	go func() {
		ch <- "World"
	}()
	fmt.Println(<-ch)
	fmt.Println(<-ch)
}
```

Example2
====================

When used `for select` ...

__Groovy__ channel's example

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

__Go__ channel's example

```go
package main

import (
	"fmt"
	"time"
)

func main() {
	ch := make(chan string, 2)
	quit := make(chan string)
	go func() {
		ch <- "Hello"
		time.Sleep(1 * time.Second)
		ch <- "World"
	}()
	go func() {
		quit <- "quit"
	}()

loop:
	for {
		select {
		case msg := <-ch:
			fmt.Println(msg)
		case msg := <-quit:
			fmt.Println(msg)
			break loop
		}
	}
	fmt.Println("Finish!")
}
```
