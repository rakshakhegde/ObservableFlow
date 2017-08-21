[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=59253d166a9d060001bb2b30&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/59253d166a9d060001bb2b30/build/latest?branch=master)

# ObservableFlow

Functional Kotlin constructs like **map()**, **filter()** and many more for ObservableFields from Android Data Binding library. Along with **RxJava 2** support.

```kotlin
// tiny preview
val source = ObservableField("source")
val dest = source.filter { it.startsWith('s') }
		.map { it.capitalize() }
```

## Installation

In your global `build.gradle` file:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Include any one of these in your app `build.gradle` file:
```gradle
dependencies {
	compile 'com.github.rakshakhegde.ObservableFlow:observableflow:23731b7bed'
}
```
(or, if you need **RxJava 2** support)
```gradle
dependencies {
	compile 'com.github.rakshakhegde.ObservableFlow:rxdatabinding:23731b7bed'
}
```

## Motivation

I personally find writing code in a functional way just makes my logic neatly encapsulated and broken up into simple parts. So I built these functional constructs as a part of my other projects where I use DataBinding and Kotlin (which is almost always), that kinda emulates RxJava's map and filter which I loved and used a lot, but for ObservableFields. So now I've open sourced it hoping others might find it useful too. Cheers :)

> ### By the way, please show some :heart:
> [![GitHub stars](https://img.shields.io/github/stars/rakshakhegde/ObservableFlow.svg?style=social&label=Star)](https://github.com/rakshakhegde/ObservableFlow) [![GitHub forks](https://img.shields.io/github/forks/rakshakhegde/ObservableFlow.svg?style=social&label=Fork)](https://github.com/rakshakhegde/ObservableFlow/fork) [![GitHub watchers](https://img.shields.io/github/watchers/rakshakhegde/ObservableFlow.svg?style=social&label=Watch)](https://github.com/rakshakhegde/ObservableFlow) [![GitHub followers](https://img.shields.io/github/followers/rakshakhegde.svg?style=social&label=Follow)](https://github.com/rakshakhegde)
[![Twitter Follow](https://img.shields.io/twitter/follow/rakshakhegde.svg?style=social)](https://twitter.com/rakshakhegde)

## Documentation

Here are all the functions and extension functions for ObservableFields. Even though I've attempted to document how to use all the functional statements, the best source of documentation are the **tests** themselves. Here I'll just show the **function prototype** and **code snippet** in pairs. Hopefully meaning of the code becomes immediately apparent.

### [ObservableFlow](https://github.com/rakshakhegde/ObservableFlow/tree/master/observableflow)

* **map**
	```kotlin
	val text: ObservableField<String> = ObservableField("Hello World")
	val textLength = text.map { it.length }
	```
	```kotlin
	inline fun <T, R> ObservableField<T>.map(
		dstObsrv: ObservableField<R> = ObservableField(),
		crossinline f: (T) -> R
	): ObservableField<R>
	```
* **vararg map**
	```kotlin
	val totalLength = map(text1, text2) {
		text1.get().length + text2.get().length
	}
	```
	```kotlin
	inline fun <T> map(vararg sources: Observable, crossinline onChange: () -> T): ObservableField<T>
	```
* **filter**
	```kotlin
	val evenLengthTextOnly = text.filter { it.length % 2 == 0 }
	```
	```kotlin
	inline fun <T> ObservableField<T>.filter(
		defaultVal: T? = null,
		crossinline predicate: (T) -> Boolean
	): ObservableField<T>
	```
* **onPropertyChanged**
	```kotlin
	text.onPropertyChanged { // block executed for every subsequent property change
		val newVal = get()
		println("text changed to $newVal")
	}
	```
	```kotlin
	inline fun <T : Observable> T.onPropertyChanged(crossinline listener: T.(propertyId: Int) -> Unit):
		Observable.OnPropertyChangedCallback
	```
* **vararg onPropertyChanged**
	```kotlin
	onPropertyChanged(text1, text2) {
		performAction(text1.get(), text2.get())
	}
	```
	```kotlin
	inline fun onPropertyChanged(vararg sources: Observable, crossinline onChange: () -> Unit):
		List<Observable.OnPropertyChangedCallback>
	```
* **bind**
	```kotlin
	text.bind { // block executed immediately and for every subsequent property change
		val newVal = get()
		println("text changed to $newVal")
	}
	```
	```kotlin
	inline fun <T : Observable> T.bind(
		crossinline listener: T.(Int) -> Unit
	): Observable.OnPropertyChangedCallback
	```
* **vararg bind**
	```kotlin
	bind(text1, text2) {
		println(text1.get() + text2.get())
	}
	```
	```kotlin
	inline fun bind(vararg sources: Observable, crossinline onChange: () -> Unit):
		List<Observable.OnPropertyChangedCallback>
	```
* **observableListOf**
	```kotlin
	val observableInts = observableListOf(5, 6, 7, 9) // Int type inferred
	```
	```kotlin
	fun <T> observableListOf(vararg items: T): ObservableArrayList<T>
	```
* **bindToList**
	```kotlin
	observableInts.bindToList { // block executed immediately and everytime anything changes in the list
		forEach { item -> performActionOn(item) }
	}
	```
	```kotlin
	inline fun <T, S : ObservableList<T>> S.bindToList(crossinline listener: S.() -> Unit):
		ObservableList.OnListChangedCallback<S>
	```
* **onListChanged**
	```kotlin
	observableInts.onListChanged { // block executed everytime anything changes in the list
		forEach { item -> performActionOn(item) }
	}
	```
	```kotlin
	inline fun <T, S : ObservableList<T>> S.onListChanged(crossinline listener: S.() -> Unit):
		ObservableList.OnListChangedCallback<S>
	```

### [RxDatabinding](https://github.com/rakshakhegde/ObservableFlow/tree/master/rxdatabinding)

* **rx**

* **toField**

* **rxOnPropertyChanged**


## Limitations/Missing parts

* There are no extension functions for all the other primitive Observables, like `ObservableInt`, `ObservableFloat`, etc. because I didn't find these optimisations worthwhile to write code and tests for. So I just use `ObservableField<Int>`, `ObservableField<Float>`, etc even for Android projects and they are auto-boxed, well, automatically.
