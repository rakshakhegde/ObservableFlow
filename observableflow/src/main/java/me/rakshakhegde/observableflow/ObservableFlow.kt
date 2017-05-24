package me.rakshakhegde.observableflow

import android.databinding.Observable
import android.databinding.ObservableField

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

inline fun <T, R> ObservableField<T>.map(crossinline f: (T) -> R): ObservableField<R> {
	val dstObsrv = ObservableField<R>(f(get()))
	bind { dstObsrv.set(f(get())) }
	return dstObsrv
}

@JvmOverloads
inline fun <T> ObservableField<T>.filter(defaultVal: T? = null,
                                         crossinline predicate: (T) -> Boolean): ObservableField<T> {
	val dstObsrv = ObservableField<T>(defaultVal)
	bind {
		get().let {
			if (predicate(it)) dstObsrv.set(it)
		}
	}
	return dstObsrv
}

inline fun bind(vararg sources: Observable, crossinline onChange: () -> Unit) {
	sources.forEach { observable ->
		observable.bind { onChange() }
	}
}
