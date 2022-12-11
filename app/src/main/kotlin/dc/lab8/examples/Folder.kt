package dc.lab7.examples

import org.w3c.dom.Element
import org.w3c.dom.Node

class Folder(val name: String): XMLTo {
    override fun fill(element: Element) {
        element.setAttribute("name", name)
    }

    override fun toString(): String {
        return name
    }
    
    companion object: XMLFrom<Folder> {
        override fun parse(node: Node): Folder {
            val attributes = node.attributes
            val name = attributes.getNamedItem("name").nodeValue
            return Folder(name)
        }
    }
}
