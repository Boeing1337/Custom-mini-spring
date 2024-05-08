# RIT-WG-PROJECT
for custom Spring realisation see https://github.com/Boeing1337/Custom-mini-spring/tree/develop/src/main/java/tech/ioc
### RIT first team project

##### The project description:
Player should find out which word system guesses by choosing a character position and answer a question. 
The answer word cointains the character a player seeking for. (In the first iteration the first character of the answer is the player's target). 

##### System requirements are
- The pool of words to guess(10 words from 5 to 10 characters).
- The pool of questions on each character. (For the first iteration only one question for one  unique character)
- Keep state from uncomplited game for each user. If user starts a new game until finish previous one then he get 1 loss point.
- Win/Loss stats for each user
- Administrator/User login system. Only administrator have access to the game configuration. For normal users this feature must be hidden. Game must be ableto recognize you to reload state and calculate score in the right way.

##### Administrator features to be implemented
- Addind, Deleting and changing words in the words poll
- Addind, Deleting and changing questions for any character(remember that for the first iteration only one question needed for a unique character)
