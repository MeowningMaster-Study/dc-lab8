package dc.lab8.task4

import java.rmi.registry.LocateRegistry

fun main() {
    val commands = RMICommands()
    val registry = LocateRegistry.createRegistry(port)
    registry.bind(registryName, commands)
    println("Server started on $port ($registryName)")
}