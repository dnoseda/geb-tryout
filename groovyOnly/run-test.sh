if [ ! -f chromedriver ]; then

  wget "http://chromedriver.storage.googleapis.com/2.4/chromedriver_mac32.zip" -O temp.zip; unzip temp.zip; rm temp.zip
fi
groovy -Dwebdriver.chrome.driver=$PWD/chromedriver MultiplierTest.groovy
