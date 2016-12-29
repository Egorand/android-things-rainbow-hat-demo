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
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

@Suppress("UsePropertyAccessSyntax")
class LedsTest {

    private val peripheralManagerService = mock<PeripheralManagerService>()
    private val redLedGpio = mock<Gpio>()
    private val greenLedGpio = mock<Gpio>()
    private val blueLedGpio = mock<Gpio>()

    private lateinit var leds: Leds

    @Before fun setUp() {
        `when`(peripheralManagerService.openGpio(Leds.LED_RED_GPIO_PIN))
                .thenReturn(redLedGpio)
        `when`(peripheralManagerService.openGpio(Leds.LED_GREEN_GPIO_PIN))
                .thenReturn(greenLedGpio)
        `when`(peripheralManagerService.openGpio(Leds.LED_BLUE_GPIO_PIN))
                .thenReturn(blueLedGpio)

        leds = Leds(peripheralManagerService)
    }

    @Test fun shouldSetupLeds() {
        verify(redLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        verify(greenLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        verify(blueLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    @Test fun shouldLightLedOn() {
        leds.setLed(Leds.LED_GREEN, on = true)

        verify(greenLedGpio).setValue(true)
    }

    @Test fun shouldToggleLed() {
        `when`(greenLedGpio.getValue()).thenReturn(true)

        leds.toggleLed(Leds.LED_GREEN)

        verify(greenLedGpio).setValue(false)
    }

    @Test fun shouldCloseLeds() {
        leds.close()

        verify(redLedGpio).close()
        verify(greenLedGpio).close()
        verify(blueLedGpio).close()
    }
}