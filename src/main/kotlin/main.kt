import com.google.gson.GsonBuilder
import java.lang.RuntimeException

fun main() {
    val gson = GsonBuilder()
        .serializeNulls()
        .create()

    val metadata = MetadataProvider(gson)
        .provideItemMetadata()
        ?: throw RuntimeException("Could not get ItemMetadata from JSON file.")

    val generatedKotlinClass: String = ItemBuilderGenerator(metadata)
        .generateItemBuilder()

    KotlinWriter(
        classString = generatedKotlinClass,
        classFileName = "AlbionItemBuilder.kt"
    ).writeToDisk()
}
