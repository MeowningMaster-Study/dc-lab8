package dc.lab7.examples

interface DataDriver {
    fun readFolders(): ArrayList<Folder>
    fun createFolder(folder: Folder)
    fun updateFolder(nameForm: String, to: Folder)
    fun deleteFolder(name: String)

    fun readFiles(): ArrayList<File>
    fun createFile(file: File)
    fun updateFile(nameForm: String, to: File)
    fun deleteFile(name: String)
}