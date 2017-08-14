[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=59253d166a9d060001bb2b30&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/59253d166a9d060001bb2b30/build/latest?branch=master)

# ObservableFlow

### Show some :heart:
[![GitHub stars](https://img.shields.io/github/stars/rakshakhegde/ObservableFlow.svg?style=social&label=Star)](https://github.com/rakshakhegde/ObservableFlow) [![GitHub forks](https://img.shields.io/github/forks/rakshakhegde/ObservableFlow.svg?style=social&label=Fork)](https://github.com/rakshakhegde/ObservableFlow/fork) [![GitHub watchers](https://img.shields.io/github/watchers/rakshakhegde/ObservableFlow.svg?style=social&label=Watch)](https://github.com/rakshakhegde/ObservableFlow) [![GitHub followers](https://img.shields.io/github/followers/rakshakhegde.svg?style=social&label=Follow)](https://github.com/rakshakhegde)  
[![Twitter Follow](https://img.shields.io/twitter/follow/rakshakhegde.svg?style=social)](https://twitter.com/rakshakhegde)

## Installation

In your global `build.gradle` file:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Include any one of these in your app `build.gradle` file:
```
dependencies {
	compile 'com.github.rakshakhegde.ObservableFlow:observableflow:23731b7bed'
}
```
(or, if you need RxJava support)
```
dependencies {
	compile 'com.github.rakshakhegde.ObservableFlow:rxdatabinding:23731b7bed'
}
```

## Motivation

I personally find writing code in a functional way just makes my logic neatly encapsulated and broken up into simple parts. So I built these functional constructs as a part of my other projects where I use DataBinding and Kotlin (which is almost always), that kinda emulates RxJava's map and filter which I loved and used a lot. So now I've open sourced it hoping others might find it useful too. Cheers :)

