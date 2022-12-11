package dc.lab7.examples

import org.w3c.dom.Element
import org.w3c.dom.Node

class File(val name: String, val size: Int, val folderName: String): XMLTo {
    override fun fill(element: Element) {
        element.setAttribute("name", name)
        element.setAttribute("size", size.toString())
        element.setAttribute("folderName", folderName)
    }
    
    override fun toString() = String.format("%s: %s (%d)", folderName, name, size)

    companion object: XMLFrom<File> {
        override fun parse(node: Node): File {
            val attributes = node.attributes
            val name = attributes.getNamedItem("name").nodeValue
            val size = attributes.getNamedItem("size").nodeValue.toInt()
            val folderName = attributes.getNamedItem("folderName").nodeValue
            return File(name, size, folderName)
        }
    }
}
