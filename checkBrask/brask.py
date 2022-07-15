from random import Random, randint
from time import sleep
#from xml.etree.ElementPath import findtext
import selenium
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver import Keys
import bs4
import re
import json, time


# ### Reading previous outcome ###
# try:
#     prev_outcome = open('outcome.txt')
# except:
#     print("Unable to read previous outcome file.")
# try:
#     fylki = prev_outcome.read()
#     print("Fylki:")
#     print(fylki[3])
# except:
#     print("Unable to print fylki.")
    


### Initializing selenium browser. ###
PATH = 'C:\Program Files (x86)\chromedriver.exe'
chromeOptions = webdriver.ChromeOptions()
# BUG: profile data not working / logging in
chromeOptions.add_argument('user-data-dir=C:\\Users\\addi\\AppData\\Local\\Temp\\scoped_dir26440_1152909528\\Default')
#chromeOptions.add_argument("--headless")
driver = webdriver.Chrome(PATH, chrome_options=chromeOptions)
driver.get("https://www.facebook.com/groups/braskogbrall.is/feed")
driver.implicitly_wait(2) # TODO: wait until an element appears instead of fixed time


### Accept cookies if needed. ###
try:
    cookieButton = driver.find_elements_by_xpath("//*[contains(text(), 'Only allow essential cookies')]")
    for x in cookieButton:
        x.click()
except:
    print("no cookies")


### Scroll down at random intervals. ###
numOfScrolls = 300
for i in range(numOfScrolls):
    random = (randint(0, 2000))/2000
    ActionChains(driver).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_UP).perform()
    sleep(random)


### Working out the details of results. ###
results = []
wordsToSearchFor = ["skjár", "tölvuskjár", "monitor"]
#wordsToSearchFor = ["iphone", "mac", "laptop"]
#wordsToSearchFor = ["Bílakerra"]
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
            name = entry.find("h2", class_="gmql0nx0 l94mrbxd p1ri9a11 lzcic4wl aahdfvyu hzawbc8m").get_text()
            # title of image: "a8c37x1j ni8dbmo4 stjgntxs l9j0dhe7 ojkyduve"
            description = entry.find("span", class_="d2edcug0 hpfvmrgz qv66sw1b c1et5uql lr9zc1uh a8c37x1j fe6kdd0r mau55g9w c8b282yb keod5gw0 nxhoafnm aigsh9s9 d3f4x2em iv3no6db jq4qci2q a3bd9o3v b1v8xokw oo9gr5id hzawbc8m").get_text()
            
            # TODO: ADD IMAGES?
            # <img alt class="i09qtzwb n7fi1qx3 datstx6m pmk7jnqg j9ispegn kr520xx4 k4urcfbm" src="link-to-pic">
            # extract src!
            #results.append((name, description, link[0]))
            results.append(name)
            results.append(description)
            results.append(link[0])
            

### Print results. ###
for x in results:
    try:
        print(x)
    except:
        print("Unable to print current result")
    
#driver.close()
