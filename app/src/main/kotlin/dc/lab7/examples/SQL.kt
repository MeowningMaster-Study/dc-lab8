package dc.lab7.examples

import java.sql.Connection
import java.sql.DriverManager

@Suppress("SpellCheckingInspection")
class SQL: DataDriver {
    private val connection: Connection
    
    init {
        Class.forName("com.mysql.cj.jdbc.Driver")
        val url = "jdbc:mysql://eu-central.connect.psdb.cloud/ror?sslMode=VERIFY_IDENTITY"
        val user = "yhncmo470d7eyaz99aj7"
        val password = "pscale_pw_F7KjpvGL9WsVW17alMyFpMIOpXq5SoQ4siGgjKlfJsl"
        connection = DriverManager.getConnection(url, user, password)
    }
    
    override fun readFolders(): ArrayList<Folder> {
        val statement = connection.createStatement()
        val recordset =  statement.executeQuery("select * from folders;")
        val folders = ArrayList<Folder>()
        while (recordset.next()) {
            val name = recordset.getString("name")
            val folder = Folder(name)
            folders.add(folder)
        }
        return folders
    }

    override fun createFolder(folder: Folder) {
        val statement = connection.prepareStatement("insert into folders (name) values (?)")
        statement.setString(1, folder.name)
        statement.executeUpdate()
    }

    override fun updateFolder(nameForm: String, to: Folder) {
        val statement = connection.prepareStatement("update folders set name = ? where name = ?")
        statement.setString(1, to.name)
        statement.setString(2, nameForm)
        statement.executeUpdate()
    }

    override fun deleteFolder(name: String) {
        val statement = connection.prepareStatement("delete from folders where name = ?")
        statement.setString(1, name)
        statement.executeUpdate()
    }

    override fun readFiles(): ArrayList<File> {
        val statement = connection.createStatement()
        val recordset =  statement.executeQuery("select * from files;")
        val files = ArrayList<File>()
        while (recordset.next()) {
            val name = recordset.getString("name")
            val size = recordset.getInt("size")
            val folderName = recordset.getString("folderName")
            val file = File(name, size, folderName)
            files.add(file)
        }
        return files
    }

    override fun createFile(file: File) {
        val statement = connection.prepareStatement("insert into files (name, size, folderName) values (?, ?, ?)")
        statement.setString(1, file.name)
        statement.setInt(2, file.size)
        statement.setString(3, file.folderName)
        statement.executeUpdate()
    }

    override fun updateFile(nameForm: String, to: File) {
        val statement = connection.prepareStatement("update files set name = ?, size = ?, folderName = ? where name = ?")
        statement.setString(1, to.name)
        statement.setInt(2, to.size)
        statement.setString(3, to.folderName)
        statement.setString(4, nameForm)
        statement.executeUpdate()
    }

    override fun deleteFile(name: String) {
        val statement = connection.prepareStatement("delete from files where name = ?")
        statement.setString(1, name)
        statement.executeUpdate()
    }
}