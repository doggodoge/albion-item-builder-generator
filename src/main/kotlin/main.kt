import com.google.gson.GsonBuilder

fun main(args: Array<String>) {
    val gson = GsonBuilder()
        .serializeNulls()
        .create()

    val metadata = MetadataProvider(gson)
        .provideMetadata()

    val kotlinClassString = ItemBuilderGenerator(metadata!!)
        .generateItemBuilder()

    KotlinWriter(
        classString = kotlinClassString,
        classFileName = "AlbionItemBuilder.kt"
    ).writeKotlinClassToDisk()
}
