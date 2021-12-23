from os import stat
import random


# Hangman ASCII art
HANGMAN_IMG = [
"""  +---+
  |   |
      |
      |
      |
      |
=========
""", 
"""  +---+
  |   |
  O   |
      |
      |
      |
========="""
, 
"""  +---+
  |   |
  O   |
  |   |
      |
      |
========="""
,
"""  +---+
  |   |
  O   |
 /|   |
      |
      |
========="""
,
"""  +---+
  |   |
  O   |
 /|\  |
      |
      |
========="""
,
"""  +---+
  |   |
  O   |
 /|\  |
 /    |
      |
========="""
,
"""  +---+
  |   |
  O   |
 /|\  |
 / \  |
      |
========="""
]


# Functions
def listToString(s):
  str1 = ""
  for x in s:
    str1 = str1 + x
  return str1

def get_input():
  global state
  #global CHOSEN_WORD
  #global CHOSEN_WORD_LENGTH

  player_input = ""
  while len(player_input)!=1:
    player_input = input("Choose a letter: ")
  player_input.lower()
  if player_input in CHOSEN_WORD:
    for i in range(CHOSEN_WORD_LENGTH):
      if player_input == CHOSEN_WORD[i]:
        guess[i] = player_input
  elif player_input in wrongGuess or player_input in guess:
    print("You already guessed that!", end=" ")
    get_input()
  else:
    state = state + 1
    wrongGuess.append(player_input)


# Put words into a list and initialize guesses
words = []
with open('corncob.txt') as f:
    words = f.read().splitlines()
CHOSEN_WORD = words[random.randrange(0, len(words), 1)]
CHOSEN_WORD_LENGTH = len(CHOSEN_WORD)
guess = []
for i in range(CHOSEN_WORD_LENGTH):
    guess.append("_")
wrongGuess = []


# Game loop
state = 0
while state<6:
  if CHOSEN_WORD == listToString(guess):
    break
  print("------------------------------------------")
  print(listToString(guess))
  print(HANGMAN_IMG[state])
  print("Letters guessed: " + listToString(wrongGuess))
  print(listToString(guess))
  print()
  get_input()


# End of game
print("------------------------------------------")
if state>5:
  print(HANGMAN_IMG[6])
  print("Game over!" + " The correct word was: " + CHOSEN_WORD + ".")
else:
  print("Congrats, you've won! by guessing the word " + CHOSEN_WORD + "!")



  