package com.instavans.instavansshippers.ObservablesKotlinUtils

import android.databinding.ObservableList

/**
 * Created by rakshakhegde on 29/08/16.
 * Not being used yet.
 */

fun <T> ObservableList<T>.onAnyChange(changed: () -> Unit):
		ObservableList.OnListChangedCallback<ObservableList<T>> {
	val callback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
		override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int,
		                                itemCount: Int) {
			changed()
		}

		override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int,
		                                 itemCount: Int) {
			changed()
		}

		override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int,
		                              itemCount: Int) {
			changed()
		}

		override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int,
		                                itemCount: Int) {
			changed()
		}

		override fun onChanged(sender: ObservableList<T>) {
			changed()
		}
	}
	addOnListChangedCallback(callback)
	return callback
}
