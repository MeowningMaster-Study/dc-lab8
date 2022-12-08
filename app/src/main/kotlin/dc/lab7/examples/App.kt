package dc.lab7.examples

fun main() {
    val sql = SQL()
    val xml = XML()
    xml.load()
    val drivers = ArrayList<DataDriver>()
    drivers.add(sql)
    drivers.add(xml)
    
    for (driver in drivers) {
        val folders = driver.readFolders()
        println(folders)
        driver.createFolder(Folder("Documents"))
        driver.updateFolder("Documents", Folder("Films"))
        driver.deleteFolder("Films")

        val files = driver.readFiles()
        println(files)
        driver.createFile(File("logo.png", 5, "Images"))
        driver.updateFile("logo.png", File("logo.ico", 10, "Images"))
        driver.deleteFile("logo.ico")
    }

    xml.save()
}
