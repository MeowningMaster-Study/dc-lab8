package dc.lab8.task3

import dc.lab8.File
import dc.lab8.Folder
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket

private lateinit var socketOut: ObjectOutputStream
private lateinit var socketIn: ObjectInputStream

/** <name, Folder> */
private val folders = HashMap<String, Folder>()

fun main() {
    val serverSocket = ServerSocket(port)
    println("Server started on $port")
    
    val socket = serverSocket.accept()
    socketIn = ObjectInputStream(socket.getInputStream()) 
    socketOut = ObjectOutputStream(socket.getOutputStream())
    println("Client connected")
    
    while(true) {
        if (!executeCommand(socketRead())) {
            break
        }
        socketWrite(done)
    }
}

private fun executeCommand(command: String): Boolean {
    when (command) {
        addFolder -> addFolder()
        removeFolder -> removeFolder()
        listFolders -> listFolders()
        addFile -> addFile()
        removeFile -> removeFile()
        editFile -> editFile()
        moveFile -> moveFile()
        copyFile -> copyFile()
        listFiles -> listFiles()
        exit -> return false
        else -> socketPrint("wrong command, try again")
    }
    return true
}

private fun socketWrite(obj: Any) {
    socketOut.writeObject(obj)
    socketOut.flush()
    println("$obj ->")
}

private inline fun <reified T> socketRead(): T {
    val obj = socketIn.readObject()
    return if (obj is T) {
        println("<- $obj")
        obj
    } else {
        throw RuntimeException()
    }
}

private inline fun <reified T> socketPrompt(topic: String): T {
    socketWrite(prompt)
    socketWrite(topic)
    return socketRead()
}

private fun socketPrint(message: String) {
    socketWrite(print)
    socketWrite(message)
}

private fun addFolder() {
    val name: String = socketPrompt("name")
    if (folders.contains(name)) {
        socketPrint(alreadyExists)
        return
    }
    val folder = Folder(name)
    folders[name] = folder
    socketPrint(success)
}

private fun removeFolder() {
    val name: String = socketPrompt("name")
    if (folders.remove(name) == null) {
        socketPrint(noSuchFolder)
        return
    }
    socketPrint(success)
}

private fun listFolders() {
    for (folder in folders) {
        socketPrint(folder.key)
    }
}

private fun promptFolder(): Folder? {
    val folderName: String = socketPrompt("folder name")
    return folders[folderName]
}

private fun addFile() {
    val folder = promptFolder()
    if (folder == null) {
        socketPrint(noSuchFolder)
        return
    }
    val name: String = socketPrompt("name")
    if (folder.files.contains(name)) {
        socketPrint(alreadyExists)
        return
    }
    val content: String = socketPrompt("content")
    val file = File(name, content)
    folder.files[name] = file
    socketPrint(success)
}

private fun removeFile() {
    val folder = promptFolder()
    if (folder == null) {
        socketPrint(noSuchFolder)
        return
    }
    val name: String = socketPrompt("name")
    if (folder.files.remove(name) == null) {
        socketPrint(noSuchFile)
        return
    }
    socketPrint(success)
}

private fun editFile() {
    val folder = promptFolder()
    if (folder == null) {
        socketPrint(noSuchFolder)
        return
    }
    val name: String = socketPrompt("name")
    val file = folder.files[name]
    if (file == null) {
        socketPrint(noSuchFile)
        return
    }
    val content: String = socketPrompt("content")
    file.content = content
    socketPrint(success)
}

private fun moveFile() {
    val folderFromName: String = socketPrompt("folder from name")
    val folderFrom = folders[folderFromName]
    if (folderFrom == null) {
        socketPrint(noSuchFolder)
        return
    }
    val nameFrom: String = socketPrompt("name from")
    val fileFrom = folderFrom.files[nameFrom]
    if (fileFrom == null) {
        socketPrint(noSuchFile)
        return
    }
    val folderToName: String = socketPrompt("folder to name")
    val folderTo = folders[folderToName]
    if (folderTo == null) {
        socketPrint(noSuchFolder)
        return
    }
    val nameTo: String = socketPrompt("name to")
    if (folderTo.files.contains(nameTo)) {
        socketPrint(alreadyExists)
        return
    }
    folderFrom.files.remove(nameFrom)
    folderTo.files[nameTo] = fileFrom
    socketPrint(success)
}

private fun copyFile() {
    val folderFromName: String = socketPrompt("folder from name")
    val folderFrom = folders[folderFromName]
    if (folderFrom == null) {
        socketPrint(noSuchFolder)
        return
    }
    val name: String = socketPrompt("name")
    val file = folderFrom.files[name]
    if (file == null) {
        socketPrint(noSuchFile)
        return
    }
    val folderToName: String = socketPrompt("folder to name")
    val folderTo = folders[folderToName]
    if (folderTo == null) {
        socketPrint(noSuchFolder)
        return
    }
    if (folderTo.files.contains(name)) {
        socketPrint(alreadyExists)
        return
    }
    folderTo.files[name] = file.copy()
    socketPrint(success)
}

private fun listFiles() {
    val folder = promptFolder()
    if (folder == null) {
        socketPrint(noSuchFolder)
        return
    }
    for (fileEntry in folder.files) {
        val file = fileEntry.value
        socketPrint("${file.name} (${file.content})")
    }
}