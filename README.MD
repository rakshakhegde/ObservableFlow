# ObservableFlow
### Pt 1/3

[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=59253d166a9d060001bb2b30&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/59253d166a9d060001bb2b30/build/latest?branch=master)
[![Minimum API 9](https://img.shields.io/badge/API-9+-blue.svg)](https://upscri.be/b00b87/)
[![Release](https://jitpack.io/v/rakshakhegde/ObservableFlow.svg)](https://jitpack.io/#rakshakhegde/ObservableFlow)
[![rakshakhegde newsletter](https://img.shields.io/badge/rakshakhegde-newsletter-red.svg)](https://upscri.be/b00b87/)

Functional Kotlin constructs like **map()**, **filter()** and 12 more functions built for Android Data Binding library + **RxJava 2** support

```kotlin
// tiny preview
val source = ObservableField("source")
val dest = source.filter { it.startsWith('s') }
		.map { it.capitalize() }
```

### Please sign up for my newsletter **[rakshakhegde](https://upscri.be/b00b87/)** 📧

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
	compile 'com.github.rakshakhegde.ObservableFlow:observableflow:0.1-alpha'
}
```
(or, if you need **RxJava 2** support. This includes the above ObservableFlow core library.)
```gradle
dependencies {
	compile 'com.github.rakshakhegde.ObservableFlow:rxdatabinding:0.1-alpha'
}
```

## Motivation

I personally find writing code in a functional way just makes my logic neatly encapsulated and broken up into simple parts. So I built these functional constructs as a part of my other projects where I use DataBinding and Kotlin (which is almost always), that kinda emulates RxJava's map and filter which I loved and used a lot, but for ObservableFields. So now I've open sourced it hoping others might find it useful too. Cheers :)

> ### By the way, if you like my work please show some :heart: and star this project above
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
	```kotlin
	val nameObservable = ObservableField("First")
	
	nameObservable.rx()
		.subscribe(onNext)
	```
	```kotlin
	fun <T> ObservableField<T>.rx(): Observable<T>
	```
* **toField** - `share()`s the RxJava Observable so that when the last propertyChangedCallback is removed, the Observable is also finished
	```kotlin
	val dest = Observable.create<String> { emitter = it }
			.toField()
	```
	```kotlin
	fun <T> Observable<T>.toField(defaultVal: T? = null): RxObservableField<T>
	```
* **rxOnPropertyChanged**
	```kotlin
	rxOnPropertyChange(src1, src2, src3)
		.map { src1.get() + src2.get() + src3.get() }
		.subscribe(onNext)
	```
	```kotlin
	fun rxOnPropertyChange(vararg observables: android.databinding.Observable): io.reactivex.Observable<Int>
	```
* **rxBind**
	```kotlin
	rxBind(src1, src2, src3)
		.map { src1.get() + src2.get() + src3.get() }
		.subscribe(onNext)
	```
	```kotlin
	fun rxBind(vararg observables: android.databinding.Observable): io.reactivex.Observable<Int>
	```

## Miscellaneous

* There are no extension functions for all the other primitive Observables, like `ObservableInt`, `ObservableFloat`, etc. because I didn't find these optimisations worthwhile to write code and tests for. So I just use `ObservableField<Int>`, `ObservableField<Float>`, etc even for Android projects and they are auto-boxed, well, automatically.
* For the RxJava **toField()** binding, it was initially non-explicitly-writable, i.e., set() function was made useless and deprecated, so as to avoid side-effects, one of guiding principles of Functional Programming. But I once found myself desperately needing to change the value of that ObservableField imperatively. So I have reverted it back to a normal ObservableField whose value can be imperatively mutated anywhere in your code. But just don't go bonkers using this side-effect. Try and use it functionally as much as possible (:

## Created & Maintained By

[Rakshak R.Hegde](https://github.com/rakshakhegde) ([@rakshakhegde](https://twitter.com/rakshakhegde)) Wanna contribute to this project? Pull requests welcome ❤️

> **Note:** This project is a part of an ongoing series of open-source projects I'm working on. If you liked my work, please consider following me on **[Twitter @rakshakhegde](https://twitter.com/rakshakhegde)** and **[Github](https://github.com/rakshakhegde)**. Also please **share** this project with fellow developers. I'm also starting a newsletter of my own called **[rakshakhegde 📧](https://upscri.be/b00b87/)**. It would be great if you could fill in your email address and subscribe to my new project announcements, releases or blog posts. Subscribe [**here**](https://upscri.be/b00b87/) :)

## License

```txt
Copyright 2017 Rakshak Hegde

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
