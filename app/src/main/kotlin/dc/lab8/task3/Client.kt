package dc.lab8.task3

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

private lateinit var socketOut: ObjectOutputStream
private lateinit var socketIn: ObjectInputStream

fun main() {
    val socket = Socket(host, port)
    socketOut = ObjectOutputStream(socket.getOutputStream())
    socketIn = ObjectInputStream(socket.getInputStream())
    println("Connected to $host:$port")
    
    println("use \"help\" command for commands list")
    while(true) {
        when(val command = prompt(command)) {
            help -> printHelp()
            exit -> {
                socketWrite(exit)
                break
            }
            else -> executeCommand(command)
        }
    }
}

private fun executeCommand(command: String) {
    socketWrite(command)
    while(true) {
        when (socketRead<String>()) {
            prompt -> {
                val topic = socketRead<String>()
                val value = prompt(topic)
                socketWrite(value)
            }
            print -> {
                val message = socketRead<String>()
                println(message)
            }
            done -> break
            else -> throw RuntimeException()
        }
    }
}

private fun prompt(topic: String?): String {
    print("$topic > ")
    return readln().trim()
}

private fun socketWrite(obj: Any) {
    socketOut.writeObject(obj)
    socketOut.flush()
}

private inline fun <reified T> socketRead(): T {
    val obj = socketIn.readObject()
    return if (obj is T) {
        obj
    } else {
        throw RuntimeException()
    }
}

private fun printHelp() {
    val commands = arrayOf(
        addFolder,
        removeFolder,
        addFile,
        removeFile,
        editFile,
        moveFile,
        copyFile,
        listFolders,
        listFiles,
        help
    )
    for (command in commands) {
        println(command)
    }
}