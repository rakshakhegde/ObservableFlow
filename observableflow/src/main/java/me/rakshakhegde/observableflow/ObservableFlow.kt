package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import me.rakshakhegde.observableflow.onPropertyChanged

/**
 * Created by rakshakhegde on 30/03/17.
 */

inline fun <T, R> ObservableField<T>.map(crossinline f: (T) -> R): ObservableField<R> {
	val dstObsrv = ObservableField<R>(f(get()))
	onPropertyChanged { dstObsrv.set(f(get())) }
	return dstObsrv
}

inline fun <T> ObservableField<T>.filter(crossinline predicate: (T) -> Boolean): ObservableField<T> {
	val dstObsrv = ObservableField<T>()
	onPropertyChanged {
		get().let {
			if (predicate(it)) dstObsrv.set(it)
		}
	}
	return dstObsrv
}
