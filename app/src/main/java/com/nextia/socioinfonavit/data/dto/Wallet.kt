package com.nextia.socioinfonavit.data.dto

import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("id") val id: Long,
    @SerializedName("display_index") val display_index: Int,
    @SerializedName("display_text") val display_text: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("path") val path: String,
    @SerializedName("max_level") val max_level: Int,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("name") val name: String,
    @SerializedName("benevit_count") val benevit_count: Int,
    @SerializedName("mobile_cover_url") val mobile_cover_url: String,
    @SerializedName("desktop_cover_url") val desktop_cover_url: String,
    @SerializedName("primary_color") val primary_color: String,
    @SerializedName("max_level_phase_2") val max_level_phase_2: Int,
    @SerializedName("max_level_phase_3") val max_level_phase_3: Int,
    @SerializedName("max_level_phase_4") val max_level_phase_4: Int,
    @SerializedName("max_level_phase_5") val max_level_phase_5: Int,
) {
    var benevit = mutableListOf<Benevit>()
}

data class MemberLevel(
    @SerializedName("id") val id: Long,
    @SerializedName("member_id") val member_id: Long,
    @SerializedName("wallet_id") val wallet_id: Long,
    @SerializedName("level") val level: Long,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("wallet_level") val wallet_level: WalletLevel

    )

data class WalletLevel(
    @SerializedName("id") val id: Long,
    @SerializedName("level") val level: Long,
    @SerializedName("display_text") val display_text: String,
    @SerializedName("wallet_id") val wallet_id: Long,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String

    )