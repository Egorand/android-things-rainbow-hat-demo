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

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class DisplayTest {

    private val mockDisplay = mock<AlphanumericDisplay>()

    private lateinit var display: Display

    @Before fun setUp() {
        display = Display(mockDisplay)
    }

    @Test fun shouldSetupDisplay() {
        verify(mockDisplay).setEnabled(true)
        verify(mockDisplay).clear()
    }

    @Test fun shouldDisplayMessage() {
        display.displayMessage("Hello!")

        verify(mockDisplay).display("Hello!")
    }

    @Test fun shouldCloseDisplay() {
        reset(mockDisplay)

        display.close()

        verify(mockDisplay).clear()
        verify(mockDisplay).setEnabled(false)
        verify(mockDisplay).close()
    }
}
