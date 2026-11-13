package com.example.soapp.core.locale

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocaleManagerTest {

    @Test
    fun `setLanguage should update currentLanguage state`() = runTest {
        LocaleManager.setLanguage(AppLanguage.ENGLISH)

        assertEquals(AppLanguage.ENGLISH, LocaleManager.currentLanguage.value)
    }
}
