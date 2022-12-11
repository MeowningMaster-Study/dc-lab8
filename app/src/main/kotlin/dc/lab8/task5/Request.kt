package dc.lab8.task5

import lombok.AllArgsConstructor
import lombok.Getter
import java.io.Serializable

@Getter
@AllArgsConstructor
class Request(val command: String, val data: Any?) : Serializable