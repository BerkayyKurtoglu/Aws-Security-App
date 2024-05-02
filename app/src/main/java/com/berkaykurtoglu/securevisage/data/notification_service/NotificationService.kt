package com.berkaykurtoglu.securevisage.data.notification_service

import com.amplifyframework.core.Amplify
import com.amplifyframework.notifications.pushnotifications.NotificationContentProvider
import com.amplifyframework.notifications.pushnotifications.NotificationPayload
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("token: $token")
        // Register device with Pinpoint
        Amplify.Notifications.Push.registerDevice(token,
            {
                println("Successfully registered device, from not service")
            },
            { error ->
                println("Error registering device, from not server ${error.localizedMessage}")
            }
        )
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationPayload = NotificationPayload(NotificationContentProvider.FCM(message.data))

        // Amplify should handle notification if it is sent from Pinpoint
        val isAmplifyMessage = Amplify.Notifications.Push.shouldHandleNotification(notificationPayload)
        if (isAmplifyMessage) {
            // let Amplify handle foreground and background message received
            Amplify.Notifications.Push.handleNotificationReceived(
                notificationPayload,
                {
                    println("Successfully successfully handled device, from not service")
                },
                {
                    println("Error handling notification, from not server ${it.localizedMessage}")
                }
            )

        }
    }



}