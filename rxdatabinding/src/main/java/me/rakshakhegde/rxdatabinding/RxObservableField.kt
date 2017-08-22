package me.rakshakhegde.rxdatabinding

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * When all the callbacks are removed, the RxJava Observable is shut down.
 * Which is why share() operator is used.
 */
class RxObservableField<T>(
		source: Observable<T>,
		defaultVal: T? = null
) : ObservableField<T>(defaultVal) {

	private val source = source.doOnNext { set(it) }
			.onErrorResumeNext(Observable.empty())
			.share()
	private val subscriptions = HashMap<OnPropertyChangedCallback, Disposable>()

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
