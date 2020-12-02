package com.example.beatbox

import android.widget.SeekBar
import androidx.databinding.BaseObservable

class SpeedSeekBarAdapter(beatBox: BeatBox, seekBar: SeekBar) : BaseObservable() {


                var mBeatBox=beatBox



      var mMinSeekBar:Int = 0
    var mMaxSeekBar:Int=0
      var mRangeSeekBar:Int=0;
      var mMinSpeed=1.1;
      var mMaxSpeed=1.1;
      var mRangeSpeed=1.1;

init {
    SpeedSeekBarAdapter( mBeatBox,  seekBar)
}

    fun SpeedSeekBarAdapter( beatBox:BeatBox,  seekBar: SeekBar) {
        mBeatBox = beatBox;
        mMinSeekBar = 0;
        mMaxSeekBar = seekBar.getMax();
        mRangeSeekBar = mMaxSeekBar - mMinSeekBar;
        mMinSpeed= beatBox.MIN_PLAYBACK_SPEED.toDouble()

        mMaxSpeed = beatBox.MAX_PLAYBACK_SPEED.toDouble();
        mRangeSpeed = mMaxSpeed - mMinSpeed;
    }
fun getSpeed():Float {
        return mBeatBox.getPlaybackSpeed();
    }

  fun getSeekBarValue(): Int {
      var  seekBarValue = Math.round((mBeatBox.getPlaybackSpeed() - mMinSpeed) / mRangeSpeed * mRangeSeekBar + mMinSeekBar);

        if (seekBarValue > mMaxSeekBar) {
            return mMaxSeekBar;
        } else if (seekBarValue < mMinSeekBar) {
            return mMinSeekBar;
        } else {
            return seekBarValue.toInt();
        }
    }

  fun setSpeed( seekBarValue:Int) {
     var  speed =  seekBarValue - (mMinSeekBar) / mRangeSeekBar * mRangeSpeed + mMinSpeed;
        mBeatBox.setPlaybackSpeed(speed);
        notifyChange();
    }

   fun changeSpeed( seekBar:SeekBar, progress:Int,  fromUser:Boolean) {
        setSpeed(progress);
    }
}


