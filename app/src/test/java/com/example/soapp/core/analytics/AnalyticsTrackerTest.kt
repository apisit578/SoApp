package com.example.soapp.core.analytics

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AnalyticsTrackerTest {

    @Before
    fun setup() {
        AnalyticsTracker.clear()
    }

    @Test
    fun `logEvent should append new event to events list`() = runTest {
        AnalyticsTracker.logEvent("post_liked", mapOf("postId" to "1"))

        val events = AnalyticsTracker.events.value
        assertEquals(1, events.size)
        assertEquals("post_liked", events.first().name)
        assertEquals("1", events.first().params["postId"])
    }
}
