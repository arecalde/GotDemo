package ai.shield.app.shieldaichallenge.model

import com.google.gson.annotations.SerializedName


data class GOTJsonResult (

  @SerializedName("episodes" ) var episodes : ArrayList<Episode> = arrayListOf()

)