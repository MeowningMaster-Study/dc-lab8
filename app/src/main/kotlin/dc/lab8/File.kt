package dc.lab8

import java.io.Serializable

data class File(val name: String, var content: String): Serializable {
}