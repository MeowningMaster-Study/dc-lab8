package dc.lab8.task5

import java.io.*

fun serialize(obj: Serializable): ByteArray {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
            objectOutputStream.writeObject(obj)
            return byteArrayOutputStream.toByteArray()
        }
    }
}

fun deserialize(bytes: ByteArray): Request {
    ByteArrayInputStream(bytes).use { 
        byteArrayInputStream -> ObjectInputStream(byteArrayInputStream).use {
            objectInputStream -> return objectInputStream.readObject() as Request 
        } 
    }
}