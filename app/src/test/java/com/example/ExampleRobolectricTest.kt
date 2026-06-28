package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("NutriTrack", appName)
  }

  @Test
  fun `test send notification placing in manager`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    com.example.utils.NotificationHelper.sendNotification(context, "Test Title", "Test Message")
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
    
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val channel = notificationManager.getNotificationChannel("nutritrack_reminders")
        org.junit.Assert.assertNotNull(channel)
        assertEquals("NutriTrack Pengingat", channel?.name)
    }
  }
}
