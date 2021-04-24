import org.ainslec.picocog.PicoWriter
import java.lang.StringBuilder

enum class ItemType {

}

class ItemBuilderGenerator(private val itemMetadata: Array<ItemMetadata>) {

    // Determine which type of item we are dealing with, and with that
    // information pass the `ItemMetadata` class to the corresponding generator
    // function.
    //
    // Item Types will have to be inferred from strings in the JSON file. This
    // may not be reliable long-term.

    fun generateItemBuilder(): String {
        val w = PicoWriter()

        w.writeln("package me.mrgarymoore.albiondatakotlin.builders\n")
        w.writeln("import me.mrgarymoore.albiondatakotlin.model.Tier\n")
        w.writeln("class AlbionItemBuilder {")
        w.indentRight()
        w.writeln("private val stringBuilder = StringBuilder()")
        w.writeln()

        // FIXME: At the end of each iteration, flush string builder to disk,
        //  and create a new string builder.
        itemMetadata.forEach {
            if (doesUniqueNameContainTierPrefix(it.uniqueName)
                && !(it.localizedNames?.get("EN-US") ?: "").isEmpty()) {
                /* We explicitly choose english as the function names we deal
                   with should consistently be the same. */
                val englishLocalizedName = it.localizedNames?.get("EN-US") ?: ""
                val camelCaseName = convertItemNameToCamelCase(englishLocalizedName)

                w.writeln(generateDocStringFromItemDescription(it.localizedDescriptions?.get("EN-US") ?: ""))
                w.writeln("fun $camelCaseName(tier: Tier): AlbionItemBuilder {")
                w.indentRight()
                val applicableTiers = getListOfApplicableTiers(it.uniqueName, itemMetadata)
                w.writeln("when (tier) {")

                w.indentRight()
                applicableTiers.forEach { (tier, name) ->
                    w.writeln("Tier.$tier -> stringBuilder.append(\"$name,\")")
                }

                w.indentLeft()
                w.writeln("}")

                w.writeln("return this")
                w.indentLeft()
                w.writeln("}")
                w.writeln()
            }
        }

        w.indentLeft()
        w.writeln("}\n")

        w.writeln(
            """/**
     * Build the internal state of the builder object into a [String].
     *
     * @return [String] containing comma separated list of qualities
     */"""
        )
        w.writeln("fun build(): String {")
        w.indentRight()
        w.writeln("return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()")
        w.indentLeft()
        w.writeln("}")

        return w.toString()
    }

    private fun generateDocStringFromItemDescription(itemDescription: String): String {
        val writer = PicoWriter()
        writer.indentRight()
        writer.writeln("/**");
        val lines = itemDescription.trim().split("\n")
        lines.forEach {
            writer.writeln(" * $it")
        }
        writer.writeln(" */")
        return writer.toString()
    }

    private fun doesUniqueNameContainTierPrefix(itemName: String): Boolean {
        return when (itemName.substring(0..2)) {
            "T1_", "T2_", "T3_", "T4_", "T5_", "T6_", "T7_", "T8_" -> true
            else -> false
        }
    }

    private fun getListOfApplicableTiers(
        uniqueName: String,
        itemMetadata: Array<ItemMetadata>
    ): Map<Tier, String> {
        val uniqueNameWithoutTier = uniqueName.substring(3 until uniqueName.length)
        val tierMap = mutableMapOf<Tier, String>()

        val filteredItemMetadata = itemMetadata.filter {
            it.uniqueName.contains(uniqueNameWithoutTier)
        }

        filteredItemMetadata.forEach {
            when (it.uniqueName.substring(0..2)) {
                "T1_" -> tierMap[Tier.ONE] = it.uniqueName
                "T2_" -> tierMap[Tier.TWO] = it.uniqueName
                "T3_" -> tierMap[Tier.THREE] = it.uniqueName
                "T4_" -> tierMap[Tier.FOUR] = it.uniqueName
                "T5_" -> tierMap[Tier.FIVE] = it.uniqueName
                "T6_" -> tierMap[Tier.SIX] = it.uniqueName
                "T7_" -> tierMap[Tier.SEVEN] = it.uniqueName
                "T8_" -> tierMap[Tier.EIGHT] = it.uniqueName
            }
        }

        // Make map immutable.
        return tierMap.toMap()
    }

    private fun convertItemNameToCamelCase(itemTitle: String): String {
        // We don't want to include any special characters such as apostrophes
        // as these would be illegal characters in function names.
        val regex = Regex("[^A-Za-z0-9 ]")

        // TODO: Figure out exactly what this is doing.
        return regex.replace(itemTitle, "")
            .replace(" ", "")
            .decapitalize()
    }
}