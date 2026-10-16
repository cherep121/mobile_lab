package com.example.topics_lab4.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataSourceTest {
    @Test
    fun topicsContainFourteenPositiveCourseCounts() {
        assertEquals(14, DataSource.topics.size)
        assertTrue(DataSource.topics.all { it.availableCourses > 0 })
    }
}
