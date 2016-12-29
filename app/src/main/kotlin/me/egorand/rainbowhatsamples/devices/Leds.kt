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

import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import java.io.Closeable

class Leds(peripheralManagerService: PeripheralManagerService = PeripheralManagerService())
    : Closeable {

    companion object {

        val LED_RED_GPIO_PIN = "BCM6"
        val LED_GREEN_GPIO_PIN = "BCM19"
        val LED_BLUE_GPIO_PIN = "BCM26"

        val LED_RED = 0
        val LED_GREEN = 1
        val LED_BLUE = 2
        val LEDS = arrayOf(LED_RED, LED_GREEN, LED_BLUE)
    }

    private val leds: List<Gpio>

    init {
        leds = listOf(
                openGpio(peripheralManagerService, LED_RED_GPIO_PIN),
                openGpio(peripheralManagerService, LED_GREEN_GPIO_PIN),
                openGpio(peripheralManagerService, LED_BLUE_GPIO_PIN))
    }

    private fun openGpio(service: PeripheralManagerService, pin: String): Gpio {
        val led = service.openGpio(pin)
        led.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        return led
    }

    fun setLed(led: Int, on: Boolean) = with(leds[led]) {
        value = on
    }

    fun toggleLed(led: Int) = with(leds[led]) {
        value = !value
    }

    override fun close() {
        leds.forEach(Gpio::close)
    }
}