package me.rakshakhegde.rxobservable

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import me.rakshakhegde.observableflow.onPropertyChanged

/**
 * Created by rakshakhegde on 01/04/17.
 */

fun <T> rx(observable: ObservableField<T>): Observable<T> {

	return Observable.create { emitter ->

		val changedCallback = observable.onPropertyChanged { emitter.onNext(get()) }

		emitter.setCancellable { observable.removeOnPropertyChangedCallback(changedCallback) }
	}
}

@JvmOverloads
fun <T> Observable<T>.toObservableField(defaultVal: T? = null,
                                        compositeDisposable: CompositeDisposable? = null)
		: ObservableField<T> {

	val observableField = ObservableField<T>(defaultVal)
	val disposable = subscribeWith(object : DisposableObserver<T>() {
		override fun onNext(item: T) {
			observableField.set(item)
		}

		override fun onComplete() {
			// ignore
		}

		override fun onError(e: Throwable) {
			e.printStackTrace()
		}
	})
	compositeDisposable?.add(disposable)
	return observableField
}
