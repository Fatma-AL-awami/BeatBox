package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5
 public val  MIN_PLAYBACK_SPEED = 0.5f;
 public val MAX_PLAYBACK_SPEED = 2.0f;

class BeatBox (private val assets: AssetManager){
    val sounds: List<Sound>
    var mPlaybackSpeed :Float= 1.0f;
    public val  MIN_PLAYBACK_SPEED = 0.5f;
    public val MAX_PLAYBACK_SPEED = 2.0f;

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()
    init {
        sounds = loadSounds()
    }

   fun  getPlaybackSpeed():Float {
        return mPlaybackSpeed;
    }

    fun   setPlaybackSpeed(playbackSpeed: Double) {
        if (playbackSpeed > MAX_PLAYBACK_SPEED) {
            mPlaybackSpeed = MAX_PLAYBACK_SPEED;
        } else if (playbackSpeed < MIN_PLAYBACK_SPEED) {
            mPlaybackSpeed = MIN_PLAYBACK_SPEED;
        } else {
            mPlaybackSpeed = playbackSpeed.toFloat();
        }

    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, mPlaybackSpeed)
        }
    }

    fun release() {
        soundPool.release()
    }

    fun loadSounds(): List<Sound> {
        val soundNames: Array<String>
        try {
             soundNames = assets.list(SOUNDS_FOLDER)!!
             //Log.d(TAG, "Found ${soundNames.size} sounds")
          //  return soundNames.asList()
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

    val sounds = mutableListOf<Sound>()
    soundNames.forEach { filename ->
        val assetPath = "$SOUNDS_FOLDER/$filename"
        val sound = Sound(assetPath)
        //sounds.add(sound)
        try {
            load(sound)
            sounds.add(sound)
        } catch (ioe: IOException) {
            Log.e(TAG, "Cound not load sound $filename", ioe)
        }
    }
    return sounds
}

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}