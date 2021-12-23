from tkinter.constants import CENTER, END
import mechanicalsoup
import tkinter as tk
from tkinter import messagebox
import re


#window
root = tk.Tk()
INPUT_WORD = ""
INPUT_WEBSITE = ""
root.title = "WordCheck"
def openAndLook():
    list_of_outcomes.delete(0,END)
    browser = mechanicalsoup.StatefulBrowser()
    INPUT_WEBSITE = textbox_site.get()
    INPUT_WORD = textbox_word.get()
    if "http" not in INPUT_WEBSITE:
        INPUT_WEBSITE = "https://" + INPUT_WEBSITE
    browser.open(INPUT_WEBSITE)
    headlines = ("h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "a", "p", "a class", "p class")
    response = browser.page.find_all(headlines)
    texti = [value.text for value in response]
    safn = []
    for x in texti:
        if str.upper(INPUT_WORD) in str.upper(x):
            safn.append(x)
    safn = list(dict.fromkeys(safn))
    for x in safn:
        x = x + "\n"
        list_of_outcomes.insert(END, x)


    browser.close()

# Canvas
canvas = tk.Canvas(root, width=600, height=600)
canvas.grid(columnspan=3, rowspan=4)


# Label for word
label_word = tk.Label(root, text="Type the word to search for")
label_word.grid(row=0, column=0)

# Label for site
label_word = tk.Label(root, text="Type the website in which to search")
label_word.grid(row=1, column=0)

# Textbox for the word to search for
textbox_word = tk.Entry(root, justify=CENTER)
textbox_word.grid(row=0, column=1)

# Textbox for the website in which to search
textbox_site = tk.Entry(root, justify=CENTER)
textbox_site.grid(row=1, column=1)

# Button to run the function
button_check = tk.Button(root, background="lightblue", text="Check", width=15, height=3, highlightbackground="black", command=lambda:openAndLook(), justify=CENTER)
button_check.grid(row=2, column=0, columnspan=3)

# View of the results

#list_of_outcomes = tk.Listbox(root,highlightbackground="black", highlightthickness=1 ,yscrollcommand=scrollbar.set, width=95,height=20)
list_of_outcomes = tk.Listbox(root,highlightbackground="black", highlightthickness=1, width=90)
list_of_outcomes.grid(row=3,column=0, columnspan=3)





root.mainloop()
