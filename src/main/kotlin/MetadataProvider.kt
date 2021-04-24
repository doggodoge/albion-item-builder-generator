import com.google.gson.Gson
import java.nio.file.Files
import java.nio.file.Paths

class MetadataProvider(private val gson: Gson) {
    // FIXME: The path to the metadata JSON file should be passed into the
    //  constructor rather than hard-coded here.
    fun provideItemMetadata(): Array<ItemMetadata>? {
        val path = javaClass.classLoader.getResource("items.json")!!.toURI()
        val reader = Files.newBufferedReader(Paths.get(path))

        reader.use {
            return gson.fromJson(reader, Array<ItemMetadata>::class.java)
        }
    }
}