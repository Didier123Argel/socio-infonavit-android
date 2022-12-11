package com.nextia.socioinfonavit.data.dto

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("display_text") val displayText: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("path") val path: String,
    @SerializedName("primary_color") val primaryColor: String,
    @SerializedName("secondary_color") val secondaryColor: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("display_index") val displayIndex: Int,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("mobile_cover_url") val mobileCoverUrl: String,
    @SerializedName("desktop_cover_url") val desktopCoverUrl: String,
    @SerializedName("max_level") val maxLevel: Int,
    @SerializedName("max_level_phase_2") val maxLevelPhase2: Int,
    @SerializedName("max_level_phase_3") val maxLevelPhase3: Int,
    @SerializedName("max_level_phase_4") val maxLevelPhase4: Int,
    @SerializedName("max_level_phase_5") val maxLevelPhase5: Int,
) {
    var benevit = mutableListOf<Benevit>()
}