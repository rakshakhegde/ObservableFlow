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

inline fun <T, R> ObservableField<T>.map(
		dstObsrv: ObservableField<R> = ObservableField<R>(),
		crossinline f: (T) -> R
): ObservableField<R> {
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

inline fun bind(vararg sources: Any, crossinline onChange: () -> Unit) {
	onChange()
	sources.forEach { observable ->
		when (observable) {
			is Observable -> observable.onPropertyChanged { onChange() }
			else -> throw IllegalArgumentException(
					"Only android.databinding.Observable & android.databinding.ObservableList allowed"
			)
		}
	}
}
