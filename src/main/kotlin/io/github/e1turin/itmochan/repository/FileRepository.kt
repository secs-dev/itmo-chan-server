package io.github.e1turin.itmochan.repository

import org.postgresql.PGConnection
import org.postgresql.largeobject.LargeObjectManager
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileInputStream

@Repository
class FileRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveFile(file: File) : Long {
        val pgConn = (jdbcTemplate.dataSource!!.connection).apply {
            autoCommit = false
        }
        val lobj: LargeObjectManager = pgConn.unwrap(PGConnection::class.java).largeObjectAPI

        val oid = lobj.createLO(LargeObjectManager.READWRITE)
        val obj = lobj.open(oid, LargeObjectManager.WRITE)

        val fis = FileInputStream(file)

        val buf = ByteArray(2048)
        var s: Int
        var tl = 0
        while ((fis.read(buf, 0, 2048).also { s = it }) > 0) {
            obj.write(buf, 0, s)
            tl += s
        }

        obj.close()
        fis.close()
        pgConn.commit()
        pgConn.close()
        return oid
    }

    fun getFileByteArray(oid: Long) : ByteArray {
        val pgConn = (jdbcTemplate.dataSource!!.connection).apply {
            autoCommit = false
        }
        val lobj: LargeObjectManager = pgConn.unwrap(PGConnection::class.java).largeObjectAPI
        val obj = lobj.open(oid, LargeObjectManager.READ)

        val buf = ByteArray(obj.size())
        obj.read(buf, 0, obj.size())
        obj.close()
        pgConn.commit()
        pgConn.close()
        return buf
    }

    fun deleteFile(oid: Long) {
        val pgConn = (jdbcTemplate.dataSource!!.connection).apply {
            autoCommit = false
        }
        val lobj: LargeObjectManager = pgConn.unwrap(PGConnection::class.java).largeObjectAPI
        lobj.unlink(oid)

        pgConn.commit()
        pgConn.close()
    }
}