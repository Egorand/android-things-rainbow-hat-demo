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

import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class ButtonsTest {

    private val buttonADriver = mock<ButtonInputDriver>()
    private val buttonBDriver = mock<ButtonInputDriver>()
    private val buttonCDriver = mock<ButtonInputDriver>()

    private lateinit var buttons: Buttons

    @Before fun setUp() {
        buttons = Buttons(listOf(buttonADriver, buttonBDriver, buttonCDriver))
    }

    @Test fun shouldCloseAllDrivers() {
        buttons.close()

        verify(buttonADriver).close()
        verify(buttonBDriver).close()
        verify(buttonCDriver).close()
    }
}