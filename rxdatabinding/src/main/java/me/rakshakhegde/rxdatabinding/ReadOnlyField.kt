package me.rakshakhegde.rxdatabinding

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

class ReadOnlyField<T>(
		source: Observable<T>,
		defaultVal: T? = null
) : ObservableField<T>(defaultVal) {

	private val source: Observable<T>
	private val subscriptions = HashMap<OnPropertyChangedCallback, Disposable>()

	init {
		this.source = source.doOnNext { super@ReadOnlyField.set(it) }
				.doOnError { throwable ->
					Log.e("ReadOnlyField", "onError in source observable", throwable)
				}
				.onErrorResumeNext(Observable.empty())
				.share()
	}

	@Deprecated("""
	Setter of ReadOnlyField does nothing. Merge with the source Observable instead. See
	<a href="https://github.com/manas-chaudhari/android-mvvm/tree/master/Documentation/ObservablesAndSetters.md">
	Documentation/ObservablesAndSetters.md</a>
	""", level = DeprecationLevel.ERROR)
	override fun set(value: T) {
	}

	@Synchronized
	override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
		super.addOnPropertyChangedCallback(callback)
		subscriptions.put(callback, source.subscribe())
	}

	@Synchronized
	override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
		super.removeOnPropertyChangedCallback(callback)
		subscriptions.remove(callback)?.dispose()
	}
}
