import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class KotlinWriter(private val classString: String, private val classFileName: String) {
    fun writeToDisk() {
        val path = Paths.get(".", classFileName)
        val writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))

        writer.use {
            writer.write(classString)
        }
    }

    fun printClass() {
        println(classString)
    }

    fun loadClass() {
        javaClass.classLoader.loadClass(classFileName)
    }
}