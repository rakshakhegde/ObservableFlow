package me.rakshakhegde.observableflow

import android.databinding.ObservableInt
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 25/05/17.
 */
class VarargMapTest {

	@Test
	fun ifMapForThreeObservablesNotifies() {
		val src1 = ObservableInt(0)
		val src2 = ObservableInt(100)
		val src3 = ObservableInt(200)

		val result = map(src1, src2, src3) {
			src1.get() + src2.get() + src3.get()
		}

		assertEquals(expected = 300, actual = result.get())

		src1.set(5)
		assertEquals(expected = 305, actual = result.get())

		src2.set(105)
		assertEquals(expected = 310, actual = result.get())

		src3.set(205)
		assertEquals(expected = 315, actual = result.get())
	}
}
