package dc.lab8.task4

import dc.lab8.File
import java.rmi.Remote
import java.rmi.RemoteException

interface RMICommandsInterface : Remote {
    @Throws(RemoteException::class)
    fun addFolder(name: String)
    
    @Throws(RemoteException::class)
    fun removeFolder(name: String)
    
    @Throws(RemoteException::class)
    fun addFile(folderName: String, file: File)
    
    @Throws(RemoteException::class)
    fun removeFile(folderName: String, name: String)
    
    @Throws(RemoteException::class)
    fun editFile(folderName: String, file: File)
    
    @Throws(RemoteException::class)
    fun moveFile(folderNameFrom: String, nameFrom: String, folderNameTo: String, nameTo: String)
    
    @Throws(RemoteException::class)
    fun copyFile(folderNameFrom: String, nameFrom: String, folderNameTo: String)
    
    @Throws(RemoteException::class)
    fun listFolders(): ArrayList<String>
    
    @Throws(RemoteException::class)
    fun listFiles(folderName: String): ArrayList<File>
}