import com.google.gson.GsonBuilder

fun main() {
    val gson = GsonBuilder()
        .serializeNulls()
        .create()

    val metadata = MetadataProvider(gson)
        .provideJson()

    val generatedKotlinClass: String = ItemBuilderGenerator(metadata!!)
        .generateItemBuilder()

    KotlinWriter(
        classString = generatedKotlinClass,
        classFileName = "AlbionItemBuilder.kt"
    ).writeToDisk()
}
