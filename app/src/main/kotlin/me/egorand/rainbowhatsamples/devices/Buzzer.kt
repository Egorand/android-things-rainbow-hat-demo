/*
 * Copyright 2016 Egor Andreevici
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package me.egorand.rainbowhatsamples.devices

import android.os.Handler
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import java.io.Closeable

class Buzzer(private val speaker: Speaker = Speaker(Buzzer.SPEAKER_PWM_PIN),
             private val stopHandler: Handler = Handler()) : Closeable {

    companion object {
        val SPEAKER_PWM_PIN = "PWM1"
    }

    private var stopRunnable: Runnable? = null

    init {
        stopRunnable = Runnable { stop() }
    }

    fun play(frequency: Double) {
        speaker.play(frequency)
    }

    fun play(frequency: Double, duration: Double) {
        speaker.play(frequency)
        stopHandler.postDelayed(stopRunnable, duration.toLong())
    }

    fun stop() {
        speaker.stop()
    }

    override fun close() {
        stopHandler.removeCallbacks(stopRunnable)
        speaker.stop()
        speaker.close()
    }
}