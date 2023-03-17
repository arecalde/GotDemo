package ai.shield.app.shieldaichallenge.home

import ai.shield.app.shieldaichallenge.model.Episode
import ai.shield.app.shieldaichallenge.model.GOTJsonResult
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {
    val episodeList: MutableLiveData<List<Episode>> = MutableLiveData()
    private val episodeSelected = MutableLiveData<Episode>()
    val summary = MediatorLiveData("").apply {
        addSource(episodeSelected) {
            this.value = it.summary.orEmpty()
        }
    }

    val photoUrl = MediatorLiveData("").apply {
        addSource(episodeSelected) {
            this.value = it.image?.original.orEmpty()
        }
    }

    private var selectedEpisodeIndex = -1

    fun updateEpisode(newEpisodeIndex: Int) {
        if (selectedEpisodeIndex != newEpisodeIndex) {
            selectedEpisodeIndex = newEpisodeIndex
            episodeSelected.value = episodeList.value?.get(newEpisodeIndex)
        }
    }

    private val context: Context
        get() = application.applicationContext
    init {
        val gotEpisodeIS = context.assets.open("game_of_thrones_episodes.txt")

        val textBuilder = StringBuilder()
        BufferedReader(
            InputStreamReader(
                gotEpisodeIS,
                Charset.forName(StandardCharsets.UTF_8.name())
            )
        ).use { reader ->
            var c: Int
            while (reader.read().also { c = it } != -1) {
                textBuilder.append(c.toChar())
            }
        }

        val result = Gson().fromJson(textBuilder.toString(), GOTJsonResult::class.java)
        episodeList.value = result.episodes
    }
}