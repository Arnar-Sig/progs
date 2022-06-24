#from calendar import c
from random import Random, randint
from time import sleep
from xml.etree.ElementPath import findtext
import selenium
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver import Keys
import bs4
import re
import json, time

# Initializing.
PATH = 'C:\Program Files (x86)\chromedriver.exe'
chromeOptions = webdriver.ChromeOptions()
chromeOptions.add_argument('user-data-dir=C:\\Users\\addi\\AppData\\Local\\Temp\\scoped_dir26440_1152909528\\Default')
driver = webdriver.Chrome(PATH, chrome_options=chromeOptions)
driver.get("https://www.facebook.com/groups/braskogbrall.is/feed")
#driver.get("https://www.visir.is")
driver.implicitly_wait(2)

# Accept cookies if needed.
try:
    cookieButton = driver.find_elements_by_xpath("//*[contains(text(), 'Only allow essential cookies')]")
    for x in cookieButton:
        x.click()
except:
    print("no cookies")

# Scroll down at random intervals.
nrOfScrolls = 150
for i in range(nrOfScrolls):
    random = (randint(0, 2000))/2000
    ActionChains(driver).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_UP).perform()
    sleep(random)


# Working out the details of results.
results = []
wordsToSearchFor = ["skjár", "tölvuskjár"]

soup = bs4.BeautifulSoup(driver.page_source, 'html.parser')
entries = soup.find_all("div", class_="du4w35lb k4urcfbm l9j0dhe7 sjgh65i0")
for entry in entries:
    entryText = entry.get_text()
    for word in wordsToSearchFor:
        entryTextLower = entryText.lower()
        if word in entryTextLower:
            linkTextFull = str(entry.find_all(href=True))
            link = re.findall('https://www.facebook.com/groups/braskogbrall.is/posts[^\s]+', linkTextFull)
            link[0] = link[0][:-1]
            results.append((entryText, link[0]))
            

    #print(entryText)
    #print(link[0])
    #print("------------------------------------")


print(results)
    

# %%
