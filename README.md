# currency-checker
A simple telegram bot that will send currency rates every morning and every evening or by request.
![image](https://user-images.githubusercontent.com/56763346/138606382-6b9283fb-abf1-4809-a645-9880d899b1c6.png)

Bot developed to be deployed on heroku.com.
Usage:
Change following properties in src/main/resources/telegram.properties
bot.name - bot name that you received from telegram's BotFather.
bot.token - token for HTTP API usage that you received from telegram's BotFather.
general.id - your telegram id, so bot can send scheduled messages.
access.pwd - password on which bot will react and send currency rates on demand.
zone.id - your time zone id, on image example it is set to 'Europe/Moscow'.

Also configure property in src/main/resources/application.properties
app.url - heroku application url that is used to send heartbeats and prevent heroku to put application in "sleep" mode.
'greetings' properties are as greetings for scheduled messages in evening and morning time.
