# currency-checker
A simple telegram bot that will send currency rates every morning and every evening or by request.
![image](https://user-images.githubusercontent.com/56763346/138606382-6b9283fb-abf1-4809-a645-9880d899b1c6.png)
<br>
Bot developed to be deployed on heroku.com.
<br>
Usage:
<br>
Change following properties in src/main/resources/telegram.properties
<br>
bot.name - bot name that you received from telegram's BotFather.
<br>
bot.token - token for HTTP API usage that you received from telegram's BotFather.
<br>
general.ids - comma separated list of telegram ids, so bot can send scheduled messages to them. Example '345634634,3463453451,787765'
<br>
access.pwd - password on which bot will react and send currency rates on demand.
<br>
zone.id - your time zone id, on image example it is set to 'Europe/Moscow'.
<br>
<br>
Also configure property in src/main/resources/application.properties
<br>
app.url - heroku application url that is used to send heartbeats and prevent heroku to put application in "sleep" mode.
<br>
'greetings' properties are as greetings for scheduled messages in evening and morning time.
<br>
Structure of 'greetings': 'chat_id_1:greeting_1|chat_id_2:greeting_2'. Example:
<br>
greetings.morning=674564564:Good morning, Vladimir.|8456345:Good morning, Bob.
<br>
greetings.evening=674564564:Good evening, Vladimir.|8456345:Good evening, Bob. 
<br>
