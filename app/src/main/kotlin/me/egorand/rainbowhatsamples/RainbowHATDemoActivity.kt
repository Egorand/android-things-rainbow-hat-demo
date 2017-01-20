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

package me.egorand.rainbowhatsamples

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import me.egorand.rainbowhatsamples.devices.Buttons
import me.egorand.rainbowhatsamples.devices.Buzzer
import me.egorand.rainbowhatsamples.devices.Display
import me.egorand.rainbowhatsamples.devices.Leds
import java.io.Closeable

class RainbowHATDemoActivity : Activity() {

    companion object {

        val MESSAGES = mapOf(
                KeyEvent.KEYCODE_A to "AHOY",
                KeyEvent.KEYCODE_B to "YARR",
                KeyEvent.KEYCODE_C to "GROG")
        val DEFAULT_MESSAGE = "WJDK"

        val NOTES = listOf(
                71, 71, 71, 71, 71, 71, 71, 64, 67, 71,
                69, 69, 69, 69, 69, 69, 69, 62, 66, 69,
                71, 71, 71, 71, 71, 71, 71, 73, 74, 77,
                74, 71, 69, 66, 64, 64)
        val TIMES = listOf(
                300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
                300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
                300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
                300, 300, 300, 300, 600, 600)
    }

    private lateinit var buttons: Buttons
    private lateinit var display: Display
    private lateinit var buzzer: Buzzer
    private lateinit var leds: Leds

    private var noteIndex = 0
    private var timeIndex = 0
    private var ledIndex = 0

    private val playHandler = Handler()
    private lateinit var playbackRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Buttons()
        display = Display()
        buzzer = Buzzer()
        leds = Leds()

        display.displayMessage(DEFAULT_MESSAGE)
        playMelodyWithLeds()
    }

    private fun playMelodyWithLeds() {
        playbackRunnable = Runnable {
            buzzer.play(NOTES[noteIndex].toDouble(), TIMES[timeIndex] * 0.8)
            leds.setLed(Leds.LEDS[ledIndex], on = true)
            leds.setLed(Leds.LEDS[prevIndex(ledIndex, Leds.LEDS.size)], on = false)
            if (noteIndex == NOTES.size - 1) {
                leds.setLed(Leds.LEDS[ledIndex], on = false)
                buzzer.close()
                display.displayMessage(DEFAULT_MESSAGE)
            } else {
                playHandler.postDelayed(playbackRunnable, TIMES[timeIndex].toLong())
                timeIndex++
                noteIndex++
                ledIndex = nextIndex(ledIndex, Leds.LEDS.size)
            }
        }
        playHandler.post(playbackRunnable)
    }

    private fun prevIndex(currIndex: Int, size: Int): Int {
        val prevIndex = currIndex - 1
        return if (prevIndex >= 0) prevIndex else size - 1
    }

    private fun nextIndex(currIndex: Int, size: Int): Int {
        val nextIndex = currIndex + 1
        return if (nextIndex < size) nextIndex else 0
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?) = when (keyCode) {
        in MESSAGES.keys -> {
            display.displayMessage(MESSAGES[keyCode]!!)
            true
        }
        else -> super.onKeyUp(keyCode, event)
    }

    override fun onDestroy() {
        playHandler.removeCallbacks(playbackRunnable)
        arrayOf(leds, buttons, buzzer, display).forEach(Closeable::close)
        super.onDestroy()
    }
}