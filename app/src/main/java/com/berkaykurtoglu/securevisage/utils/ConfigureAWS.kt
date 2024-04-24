package com.berkaykurtoglu.securevisage.utils

import android.content.Context
import android.widget.Toast
import aws.sdk.kotlin.runtime.AwsServiceException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.pushnotifications.pinpoint.AWSPinpointPushNotificationsPlugin
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

class ConfigureAWS(
    context: Context
) {

    init {
        try {
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSPinpointPushNotificationsPlugin())
            Amplify.configure(context)

        }
        catch (e : AwsServiceException){
            Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

}