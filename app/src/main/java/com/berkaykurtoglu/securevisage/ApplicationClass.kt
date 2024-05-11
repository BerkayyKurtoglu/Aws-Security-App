package com.berkaykurtoglu.securevisage

import android.app.Application
import android.widget.Toast
import aws.sdk.kotlin.runtime.AwsServiceException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.pushnotifications.pinpoint.AWSPinpointPushNotificationsPlugin
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSPinpointPushNotificationsPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(this.applicationContext)
        }
        catch (e : AwsServiceException){
            Toast.makeText(this.applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }


}