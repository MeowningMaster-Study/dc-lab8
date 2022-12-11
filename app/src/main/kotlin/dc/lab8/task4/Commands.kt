package dc.lab8.task4

import dc.lab8.File
import dc.lab8.Folder
import java.rmi.server.UnicastRemoteObject


class RMICommands: UnicastRemoteObject(), RMICommandsInterface {
    /** <name, Folder> */
    private val folders = HashMap<String, Folder>()

    override fun addFolder(name: String) {
        folders[name] = Folder(name)
    }

    override fun removeFolder(name: String) {
        folders.remove(name)
    }

    override fun addFile(folderName: String, file: File) {
        folders[folderName]?.files?.set(file.name, file)
    }

    override fun removeFile(folderName: String, name: String) {
        folders[folderName]?.files?.remove(name)
    }

    override fun editFile(folderName: String, file: File) {
        folders[folderName]?.files?.set(file.name, file)
    }

    override fun moveFile(folderNameFrom: String, nameFrom: String, folderNameTo: String, nameTo: String) {
        val file = folders[folderNameFrom]?.files?.remove(nameFrom)
        if (file != null) {
            folders[folderNameTo]?.files?.set(nameTo, file)
        }
    }

    override fun copyFile(folderNameFrom: String, nameFrom: String, folderNameTo: String) {
        val file = folders[folderNameFrom]?.files?.get(nameFrom)
        if (file != null) {
            folders[folderNameTo]?.files?.set(file.name, file)
        }
    }

    override fun listFolders(): ArrayList<String> {
        return ArrayList(folders.values.map { x -> x.name })
    }

    override fun listFiles(folderName: String): ArrayList<File> {
        val folder = folders[folderName]
        if (folder != null) {
            return ArrayList(folder.files.values)
        }
        return ArrayList()
    }

}
