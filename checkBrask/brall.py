import mechanicalsoup

browser = mechanicalsoup.StatefulBrowser()
url = 'https://www.facebook.com/groups/braskogbrall.is/feed'
browser.get_current_page()
browser.select_form("div role")