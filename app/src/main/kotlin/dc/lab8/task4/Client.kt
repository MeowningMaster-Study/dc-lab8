package dc.lab8.task4

import dc.lab8.File
import java.rmi.registry.LocateRegistry

fun main() {
    val registry = LocateRegistry.getRegistry(port)
    val rmi = registry.lookup(registryName) as RMICommandsInterface
    
    rmi.addFolder("Images")
    rmi.addFolder("Downloads")
    rmi.addFile("Images", File("pwd.txt", "324apple"))
    println(rmi.listFolders())
    println(rmi.listFiles("Images"))
}