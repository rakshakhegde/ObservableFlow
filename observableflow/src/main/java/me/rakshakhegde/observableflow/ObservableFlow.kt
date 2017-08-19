package me.rakshakhegde.observableflow

import android.databinding.Observable
import android.databinding.ObservableField

inline fun <T, R> ObservableField<T>.map(
		dstObsrv: ObservableField<R> = ObservableField(),
		crossinline f: (T) -> R
): ObservableField<R> {
	bind { dstObsrv.set(f(get())) }
	return dstObsrv
}

inline fun <T> map(vararg sources: Observable, crossinline onChange: () -> T): ObservableField<T> {
	val dst = ObservableField<T>(onChange())
	sources.forEach { observable ->
		observable.onPropertyChanged { dst.set(onChange()) }
	}
	return dst
}

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

@JvmOverloads
inline fun <T> ObservableField<T>.filter(
		defaultVal: T? = null,
		crossinline predicate: (T) -> Boolean
): ObservableField<T> {
	val dstObsrv = ObservableField<T>(defaultVal)
	bind {
		get().let {
			if (predicate(it)) dstObsrv.set(it)
		}
	}
	return dstObsrv
}

inline fun bind(vararg sources: Observable, crossinline onChange: () -> Unit): List<Observable.OnPropertyChangedCallback> =
		onChange().let {
			onPropertyChanged(*sources) { onChange() }
		}

inline fun onPropertyChanged(vararg sources: Observable, crossinline onChange: () -> Unit): List<Observable.OnPropertyChangedCallback> =
		sources.map { observable ->
			observable.onPropertyChanged { onChange() }
		}
