import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@GrabResolver(name='saucelabs-repository', root='http://repository-saucelabs.forge.cloudbees.com/release')
@Grapes([
	@Grab("org.gebish:geb-core:0.9.2"),
	@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.26.0"),
	@Grab("org.seleniumhq.selenium:selenium-support:2.26.0")
	@Grab("com.saucelabs:sauce_junit:1.0.19")
])
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
		testee = new GroovyMultiplier()
	}

	@Test void positivesFixed() {
		assert testee.triple(1) == 3: "+ve multiplier error"
	}

	@Test void positivesParameterized() {
		assert testee.triple(param) == expectedResult
	}

	@Test void negativesParameterized() {
		assert testee.triple(-param) == -expectedResult
	}

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
}
