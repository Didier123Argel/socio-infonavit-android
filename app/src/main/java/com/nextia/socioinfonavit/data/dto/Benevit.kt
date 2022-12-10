package com.nextia.socioinfonavit.data.dto

import com.google.gson.annotations.SerializedName

data class Benevit(
    @SerializedName("id") val id: Long,
    @SerializedName("description") var description: String,
    @SerializedName("title") var title: String,
    @SerializedName("instructions") val instructions: String,
    @SerializedName("expiration_date") val expiration_date: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("primary_color") val primary_color: String,
    @SerializedName("has_coupons") val has_coupons: Boolean,
    @SerializedName("vector_file_name") val vector_file_name: String,
    @SerializedName("vector_full_path") var vector_full_path: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("wallet") val wallet: Wallet,
    @SerializedName("territories") var territories: List<Territories>,
    @SerializedName("ally") val ally: Ally,
    @SerializedName("is_available_in_all_territories") val is_available_in_all_territories: Boolean,
    @SerializedName("is_available_in_ecommerce") val is_available_in_ecommerce: Boolean,
    @SerializedName("is_available_in_physical_store") val is_available_in_physical_store: Boolean

    ) {
    var isLocked: Boolean = false
    var expiration = ""
    var territory = ""

}

data class Territories(
    @SerializedName("id") val id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("clave") val clave: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
    )

data class Ally(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("mini_logo_file_name") val mini_logo_file_name: String,
    @SerializedName("mini_logo_full_path") val mini_logo_full_path: String,
    @SerializedName("description") val description: String

    )


data class BenevitResponse(
    @SerializedName("locked") val locked: List<Benevit>,
    @SerializedName("unlocked") val unlocked: List<Benevit>,

    ) {

    companion object {
        fun empty() = BenevitResponse(emptyList(), emptyList())
    }
}