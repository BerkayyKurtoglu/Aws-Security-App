package com.berkaykurtoglu.securevisage.utils

import android.content.Context
import android.widget.Toast
import aws.sdk.kotlin.runtime.AwsServiceException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class ConfigureAWS(
    context: Context
) {

    init {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(context)
        }
        catch (e : AwsServiceException){
            Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

}