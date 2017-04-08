package me.rakshakhegde.observableflow

import android.databinding.Observable

/**
 * Created by rakshakhegde on 18/09/16.
 */

inline fun <T : Observable> T.onPropertyChanged(crossinline listener: T.(propertyId: Int) -> Unit):
		Observable.OnPropertyChangedCallback {
	val callback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable, propertyId: Int) {
			listener(propertyId)
		}
	}
	addOnPropertyChangedCallback(callback)
	return callback
}

inline fun <T : Observable> T.bind(crossinline listener: T.(Int) -> Unit):
		Observable.OnPropertyChangedCallback {
	// Call once in the beginning
	listener(0)
	return onPropertyChanged(listener)
}
