import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

import geb.Browser
import com.saucelabs.junit.Parallelized;

@RunWith(Parallelized.class)
class MultiplierTest {
	def testee
	def param
	def expectedResult

	@Parameters static data() {
		return (2..4).collect{ [it, it * 3] as Integer[] }
	}

	MultiplierTest(a, b) {
		param = a
		expectedResult = b
	}

	@Before void setUp() {
		println "${Thread.currentThread()} checking.... ${param} and ${expectedResult}"
		testee = new GroovyMultiplier()
	}

	@Test void positivesFixed() {
		println "${Thread.currentThread()} checking.... ${param} and ${expectedResult}"
		assert testee.triple(1) == 3: "+ve multiplier error"
	}

	@Test void positivesParameterized() {
		println "${Thread.currentThread()} checking.... ${param} and ${expectedResult}"
		assert testee.triple(param) == expectedResult
	}

	@Test void negativesParameterized() {
		println "${Thread.currentThread()} checking.... ${param} and ${expectedResult}"
		assert testee.triple(-param) == -expectedResult
	}

	/** /
	@Test void googleWithNumber(){
		Browser.drive {
			go "http://google.com/ncr"

			// make sure we actually got to the page
			assert title == "Google"

			// enter wikipedia into the search field
			$("input", name: "q").value("wikipedia")

			// wait for the change to results page to happen
			// (google updates the page dynamically without a new request)
			waitFor { title.endsWith("Google Search") }

			// is the first link to wikipedia?
			def firstLink = $("li.g", 0).find("a.l")
			assert firstLink.text() == "Wikipedia"

			// click the link 
			firstLink.click()

			// wait for Google's javascript to redirect to Wikipedia
			waitFor { title == "Wikipedia" }
		}
	}
	/**/
}
