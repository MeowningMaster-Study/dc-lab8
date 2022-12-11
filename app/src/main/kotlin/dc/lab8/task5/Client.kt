package dc.lab8.task5

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory

private lateinit var channel: Channel

fun main() {
    org.apache.log4j.BasicConfigurator.configure()
    
    val factory = ConnectionFactory()
    val connection = factory.newConnection()
    channel = connection.createChannel()
    channel.queueDeclare(queueName, false, false, false, null)

    pub(Request(addFolder, "Image"))
    pub(Request(listFolders, null))
}

fun pub(request: Request) {
    channel.basicPublish("", queueName, null, serialize(request))
}
