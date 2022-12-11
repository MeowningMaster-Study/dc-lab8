package dc.lab8.examples

import org.w3c.dom.Document
import org.w3c.dom.Node
import java.lang.RuntimeException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XML: DataDriver, Storage {
    private val resourcesPath = "./app/src/dc.lab7.task3.main/resources"
    private val storageUri = "$resourcesPath/storage.xml"
    private lateinit var document: Document
    
    override fun load() {
        val domFactory = DocumentBuilderFactory.newInstance()
        val builder = domFactory.newDocumentBuilder()
        document = builder.parse(storageUri)
    }

    override fun save() {
        val transformer = TransformerFactory.newInstance().newTransformer()
        val output = StreamResult(java.io.File(storageUri))
        val input = DOMSource(document)
        transformer.transform(input, output)
    }
    
    private fun <T> read(companion: XMLFrom<T>, tag: String): ArrayList<T> {
        val elements = document.getElementsByTagName(tag)
        val folders = ArrayList<T>()
        for (i in 0 until elements.length) {
            val item = elements.item(i)
            val folder = companion.parse(item)
            folders.add(folder)
        }
        return folders
    }
    
    private fun getSingle(tag: String): Node {
        return document.getElementsByTagName(tag).item(0)
    }
    
    override fun readFolders(): ArrayList<Folder> {
        return read(Folder, "folder")
    }

    override fun readFiles(): ArrayList<File> {
        return read(File, "file")
    }
    
    private fun <T: XMLTo> create(obj: T, tag: String, parentTag: String) {
        val folders = getSingle(parentTag)
        val element = document.createElement(tag)
        obj.fill(element)
        folders.appendChild(element)
    }

    override fun createFolder(folder: Folder) {
        create(folder, "folder","folders")
    }

    override fun createFile(file: File) {
        create(file, "file","files")
    }

    private fun find(name: String, tag: String): Node {
        val folders = document.getElementsByTagName(tag)
        for (i in 0 until folders.length) {
            val item = folders.item(i)
            val folder = Folder.parse(item)
            if (folder.name == name) {
                return item
            }
        }
        throw RuntimeException()
    }
    
    private fun <T: XMLTo> update(nameFrom: String, to: T, tag: String, parentTag: String) {
        val folders = getSingle(parentTag)
        val item = find(nameFrom, tag)
        val newItem = document.createElement(tag)
        to.fill(newItem)
        folders.replaceChild(newItem, item)
    }
    
    override fun updateFolder(nameForm: String, to: Folder) {
        update(nameForm, to, "folder", "folders")
    }

    override fun updateFile(nameForm: String, to: File) {
        update(nameForm, to, "file", "files")
    }
    
    private fun delete(name: String, tag: String, parentTag: String) {
        val folders = getSingle(parentTag)
        val item = find(name, tag)
        folders.removeChild(item)
    }

    override fun deleteFolder(name: String) {
        delete(name, "folder", "folders")
    }

    override fun deleteFile(name: String) {
        delete(name, "file", "files")
    }
}