package dc.lab8.task5

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Delivery
import dc.lab8.Folder

/** <name, Folder> */
private val folders = HashMap<String, Folder>()

fun main() {
    org.apache.log4j.BasicConfigurator.configure()
    
    val factory = ConnectionFactory()
    val rabbitmqConnection = factory.newConnection()
    val channel = rabbitmqConnection.createChannel()
    channel.queueDeclare(queueName, false, false, false, null)
    channel.basicConsume(
        queueName, true, 
        { _: String?, delivery: Delivery -> handleRequest(delivery.body) }
    ) { _: String? -> }
}

private fun handleRequest(data: ByteArray) {
    val request = deserialize(data)
    
    when (request.command) {
        addFolder -> {
            val name = request.data as String
            folders[name] = Folder(name)
        }
        listFolders -> {
            print(folders.values.map { x -> x.name })
        }
    }
}