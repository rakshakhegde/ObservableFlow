package me.rakshakhegde.observableflow

import android.databinding.Observable

/**
 * Created by rakshakhegde on 18/09/16.
 */

inline fun <T : Observable> T.onPropertyChanged(crossinline listener: T.(Int) -> Unit):
		Observable.OnPropertyChangedCallback {
	// Call once in the beginning
	listener(0)
	val callback = object : Observable.OnPropertyChangedCallback() {
		override fun onPropertyChanged(sender: Observable, propertyId: Int) {
			listener(propertyId)
		}
	}
	addOnPropertyChangedCallback(callback)
	return callback
}
