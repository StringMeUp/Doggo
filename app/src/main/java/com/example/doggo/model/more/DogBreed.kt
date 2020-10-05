package com.example.doggo.model.more

import androidx.room.*
import com.bumptech.glide.load.model.ByteArrayLoader
import com.example.doggo.util.Converters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@TypeConverters(Converters::class)
@Entity //(A specific table name is optional. We want it to be the same as the data class)
data class DogBreed(
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @ColumnInfo(name = "dog_temperament")
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageUrl: String?,
    //@Columninfo(This annotation changes the column name for the entry. We can choose which names to change)

    @ColumnInfo(name = "height")
    val height: Height?,

    @ColumnInfo(name = "width")
    val weight: Weight?
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}