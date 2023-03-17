package ai.shield.app.shieldaichallenge.helpers

import android.content.Context

object SharedPreferencesHelper {
    fun setEpisodeId(context: Context, newEpisodeIndex: Int) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("selectedEpisode", "$newEpisodeIndex")
        editor.apply()
    }

    fun getEpisodeId(context: Context) = getSharedPreferences(context).getString("selectedEpisode", "-1")?.toInt() ?: -1


    private fun getSharedPreferences(context: Context) =
        context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
}