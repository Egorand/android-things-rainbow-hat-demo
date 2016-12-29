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
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class BuzzerTest {

    private val speaker = mock<Speaker>()
    private val stopHandler = mock<Handler>()

    private lateinit var buzzer: Buzzer

    @Before fun setUp() {
        buzzer = Buzzer(speaker, stopHandler)
    }

    @Test fun shouldPlayNote() {
        buzzer.play(30.0)

        verify(speaker).play(30.0)
    }

    @Test fun shouldPlayNoteAndPostStopRunnable() {
        buzzer.play(30.0, 100.0)

        verify(speaker).play(30.0)
        verify(stopHandler).postDelayed(any(), eq(100L))
    }

    @Test fun shouldStopPlayback() {
        buzzer.stop()

        verify(speaker).stop()
    }

    @Test fun shouldCloseSpeaker() {
        buzzer.close()

        verify(stopHandler).removeCallbacks(any())
        verify(speaker).stop()
        verify(speaker).close()
    }
}