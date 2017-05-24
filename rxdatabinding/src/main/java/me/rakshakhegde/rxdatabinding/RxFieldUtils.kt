package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import io.reactivex.Observable
import me.rakshakhegde.observableflow.bind
import me.rakshakhegde.observableflow.onPropertyChanged

/**
 * Created by rakshakhegde on 01/04/17.
 */

/**
 * Convert a Rx Observable to Android Data Binding's ObservableField.
 * Pass in a CompositeDisposable if you want to manually dispose this off later. But for
 * a nicer API, you could use RxLifecycle by Trello.
 * @see <a href="https://github.com/trello/RxLifecycle/tree/2.x">
 * https://github.com/trello/RxLifecycle/tree/2.x</a>
 */
fun <T> rx(observable: ObservableField<T>): Observable<T> {

	return Observable.create { emitter ->

		val changedCallback = observable.bind { emitter.onNext(get()) }

		emitter.setCancellable { observable.removeOnPropertyChangedCallback(changedCallback) }
	}
}

@JvmOverloads
fun <T> Observable<T>.toField(defaultVal: T? = null) = ReadOnlyField(this, defaultVal)

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
