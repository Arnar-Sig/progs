#from calendar import c
from time import sleep
from xml.etree.ElementPath import findtext
import selenium
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver import Keys
PATH = 'C:\Program Files (x86)\chromedriver.exe'
chromeOptions = webdriver.ChromeOptions()
chromeOptions.add_argument('user-data-dir=C:\\Users\\addi\\AppData\\Local\\Temp\\scoped_dir26440_1152909528\\Default')
driver = webdriver.Chrome(PATH, chrome_options=chromeOptions)

driver.get("https://www.facebook.com/groups/braskogbrall.is")
driver.implicitly_wait(4)

# Accept cookies
try:
    cookieButton = driver.find_elements_by_xpath("//*[contains(text(), 'Only allow essential cookies')]")
    for x in cookieButton:
        x.click()
except:
    print("no cookies")

# Scroll down
ActionChains(driver).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_UP).perform()
sleep(1)
ActionChains(driver).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_UP).perform()
sleep(1.3)
ActionChains(driver).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_UP).perform()
sleep(1.7)
