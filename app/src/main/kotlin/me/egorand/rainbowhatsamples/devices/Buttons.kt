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

import android.view.KeyEvent
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import java.io.Closeable

class Buttons(private val buttonDrivers: List<ButtonInputDriver> = listOf(
        registerButtonDriver(Buttons.BUTTON_A_GPIO_PIN, KeyEvent.KEYCODE_A),
        registerButtonDriver(Buttons.BUTTON_B_GPIO_PIN, KeyEvent.KEYCODE_B),
        registerButtonDriver(Buttons.BUTTON_C_GPIO_PIN, KeyEvent.KEYCODE_C))) : Closeable {

    companion object {

        val BUTTON_A_GPIO_PIN = "BCM21"
        val BUTTON_B_GPIO_PIN = "BCM20"
        val BUTTON_C_GPIO_PIN = "BCM16"

        private fun registerButtonDriver(pin: String, keycode: Int): ButtonInputDriver {
            val driver = ButtonInputDriver(pin, Button.LogicState.PRESSED_WHEN_LOW, keycode)
            driver.register()
            return driver
        }
    }

    override fun close() {
        buttonDrivers.forEach(ButtonInputDriver::close)
    }
}