package dc.lab8.task5

import lombok.AllArgsConstructor
import lombok.Getter
import java.io.Serializable

@Getter
@AllArgsConstructor
class Data : Serializable {
    private val command = 0
    private val data: Any? = null
}