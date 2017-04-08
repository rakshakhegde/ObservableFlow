package me.rakshakhegde.observableflow

import android.databinding.ObservableField

/**
 * Created by rakshakhegde on 30/03/17.
 */

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
