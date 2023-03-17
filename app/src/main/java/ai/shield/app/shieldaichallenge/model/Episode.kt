package ai.shield.app.shieldaichallenge.model

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso


class Episode (

  @SerializedName("id"       ) var id       : Int?    = null,
  @SerializedName("url"      ) var url      : String? = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("season"   ) var season   : Int?    = null,
  @SerializedName("number"   ) var number   : Int?    = null,
  @SerializedName("airdate"  ) var airdate  : String? = null,
  @SerializedName("airtime"  ) var airtime  : String? = null,
  @SerializedName("airstamp" ) var airstamp : String? = null,
  @SerializedName("runtime"  ) var runtime  : Int?    = null,
  @SerializedName("image"    ) var image    : Image?  = Image(),
  @SerializedName("summary"  ) var summary  : String? = null,
  @SerializedName("_links"   ) var Links    : Links?  = Links()
) {

  fun getTitle() = "S$season Ep$number: $name"

  fun getImageUrl() = image?.medium
}

@BindingAdapter("imageUrl")
fun loadImage(view : View, url : String?){
  Log.i("ShieldAIApp", "url: $url")
  Log.i("ShieldAIApp", "view: $view")

  if (url.isNullOrEmpty()) return
  Picasso
    .get()
    .load(url)
    .into((view as ImageView))
}
