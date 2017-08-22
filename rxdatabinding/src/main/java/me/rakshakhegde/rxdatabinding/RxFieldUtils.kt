package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import io.reactivex.Observable
import me.rakshakhegde.observableflow.bind
import me.rakshakhegde.observableflow.onPropertyChanged

/**
 * Created by rakshakhegde on 01/04/17.
 */

fun <T> ObservableField<T>.rx(): Observable<T> = Observable.create { emitter ->

	val changedCallback = bind { emitter.onNext(get()) }

	emitter.setCancellable { removeOnPropertyChangedCallback(changedCallback) }
}

fun <T> Observable<T>.toField(defaultVal: T? = null): RxObservableField<T> = RxObservableField(this, defaultVal)

fun rxOnPropertyChange(vararg observables: android.databinding.Observable): Observable<Int> {
	return Observable.create { emitter ->

		val onPropertyChangedCallbacks = observables.map {
			it.onPropertyChanged { emitter.onNext(0) }
		}

		emitter.setCancellable {
			observables.zip(onPropertyChangedCallbacks).forEach { (observable, callback) ->
				observable.removeOnPropertyChangedCallback(callback)
			}
		}
	}
}

fun rxBind(vararg observables: android.databinding.Observable): Observable<Int> {
	return Observable.create { emitter ->

		emitter.onNext(0)

		val onPropertyChangedCallbacks = observables.map {
			it.onPropertyChanged { emitter.onNext(0) }
		}

		emitter.setCancellable {
			observables.zip(onPropertyChangedCallbacks).forEach { (observable, callback) ->
				observable.removeOnPropertyChangedCallback(callback)
			}
		}
	}
}
