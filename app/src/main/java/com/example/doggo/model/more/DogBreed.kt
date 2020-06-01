package com.example.doggo.model.more

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DogBreed(
    @SerializedName("id")
    val breedId: String?,
    @SerializedName("name")
    val dogBreed: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("url")
    val imageUrl: String?,
    val height: Height?,
    val weight: Weight?
): Serializable