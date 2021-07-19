package com.dmp.simplemorty

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Custom base layer to each Fragment to log each class name to Crashlytics
 * so in the event of a crash, we can see the user's breadcrumbs i.e. how they
 * navigated inside the app before the crash took place.
 */
abstract class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseCrashlytics.getInstance().log(
            this.javaClass.simpleName
        )
    }
}