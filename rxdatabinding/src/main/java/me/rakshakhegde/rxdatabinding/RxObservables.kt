package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import me.rakshakhegde.observableflow.bind

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
fun <T> Observable<T>.toObservableField(defaultVal: T? = null,
                                        compositeDisposable: CompositeDisposable? = null
): ObservableField<T> {

	val observableField = ObservableField<T>(defaultVal)
	val disposable = subscribe(observableField::set)
	compositeDisposable?.add(disposable)
	return observableField
}

@JvmOverloads
fun <T> Single<T>.toObservableField(defaultVal: T? = null,
                                    compositeDisposable: CompositeDisposable? = null
): ObservableField<T> {

	val observableField = ObservableField<T>(defaultVal)
	val disposable = subscribe { item -> observableField.set(item) }
	compositeDisposable?.add(disposable)
	return observableField
}
