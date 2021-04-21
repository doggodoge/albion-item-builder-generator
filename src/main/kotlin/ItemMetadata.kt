import com.google.gson.annotations.SerializedName

/**
 * Simple DTO that encapsulates the metadata for an item based on information
 * provided in a JSON 
 */
data class ItemMetadata(
    @field:SerializedName("LocalizationNameVariable")
    val localizationNameVariable: String,

    @field:SerializedName("LocalizationDescriptionVariable")
    val localizationDescriptionVariable: String,

    @field:SerializedName("LocalizedNames")
    val localizedNames: Map<String, String>?,

    @field: SerializedName("LocalizedDescriptions")
    val localizedDescriptions: Map<String, String>?,

    @field:SerializedName("Index")
    val index: Int,

    @field:SerializedName("UniqueName")
    val uniqueName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemMetadata

        if (localizationNameVariable != other.localizationNameVariable) return false
        if (localizationDescriptionVariable != other.localizationDescriptionVariable) return false
        if (localizedNames != other.localizedNames) return false
        if (localizedDescriptions != other.localizedDescriptions) return false
        if (index != other.index) return false
        if (uniqueName != other.uniqueName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = localizationNameVariable.hashCode()
        result = 31 * result + localizationDescriptionVariable.hashCode()
        result = 31 * result + localizedNames.hashCode()
        result = 31 * result + localizedDescriptions.hashCode()
        result = 31 * result + index
        result = 31 * result + uniqueName.hashCode()
        return result
    }

    override fun toString(): String {
        return "AlbionItemMetadata(" +
                "localizationNameVariable='$localizationNameVariable', " +
                "localizationDescriptionVariable='$localizationDescriptionVariable', " +
                "localizedNames=$localizedNames, " +
                "localizedDescriptions=$localizedDescriptions, " +
                "index=$index, " +
                "uniqueName='$uniqueName'" +
                ")"
    }
}
