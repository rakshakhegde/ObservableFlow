package me.rakshakhegde.observableflow

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.instavans.instavansshippers.ObservablesKotlinUtils.onAnyChange

/**
 * Created by rakshakhegde on 31/03/17.
 */

inline fun <T, R> ObservableList<T>.mapTo(crossinline f: (T) -> R): ObservableList<R> {
	val dstList = ObservableArrayList<R>()
	onAnyChange { map { } }
	return dstList
}

inline fun <T> ObservableList<T>.filter(crossinline predicate: (T) -> Boolean): ObservableList<T> {
	val dstObsrv = ObservableArrayList<T>()
	return dstObsrv
}
