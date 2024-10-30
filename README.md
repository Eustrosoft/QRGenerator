[![keys](https://img.shields.io/badge/qxyz.ru-Try_out_our_service!-blue)](https://qxyz.ru)

* [QR code generating servlet](#qr-code-generating-servlet)
  * [Main goal of this application](#main-goal-of-this-application)
  * [Project setup & properties](#project-setup--properties)
    * [Dependencies](#dependencies)
    * [Properties](#properties)
    * [Setup](#setup)
  * [Generating QR codes](#generating-qr-codes)
    * [Generation out system QR codes](#generation-out-system-qr-codes)
    * [Generation QR by type](#generation-qr-by-type)
    * [Picture Configuration Parameters](#picture-configuration-parameters)
    * [CorrectionLevel explanation](#correctionlevel-explanation)
* [QR Code Telegram Bot](#qr-code-telegram-bot)
  * [Getting a Telegram Bot API key](#getting-a-telegram-bot-api-key)
  * [Project setup & properties](#project-setup--properties-1)
    * [Dependencies](#dependencies-1)
    * [Properties](#properties-1)
    * [Setup](#setup-1)
  * [Starting the application](#starting-the-application)
    * [Starting bot as java application](#starting-bot-as-java-application)
    * [Starting bot as demon](#starting-bot-as-demon)

# QR code generating servlet
## Main goal of this application
This project allows to start server part with java server side for generating QR images with different variations and settings
All parameters need to be passed through `GET` parameters to servlet
## Project setup & properties
### Dependencies
All dependencies, that you need are showed here:
- google xing-core 3.4.0
- jfreesvg 3.4.3
- javax.servlet-api 4.0.0
### Properties
All properties for this application are located in `web.xml`
### Setup
**Maven**
To build the application with maven, use:
```shell
mvn clean package
```
To clean the `target` folder
```shell
mvn clean
```
**Makefile**
Before using `Makefile`, make sure to place all libraries in `lib` directory in project root

To build the application with `Makefile`, use:
```shell
make all
```
To clean the working directory, use:
```shell
make clean
```

## Generating QR codes
### Generation out system QR codes
As example this type of url: https://qr.qxyz.ru/?q=123&p=123
```ts
Parameters:
  q: String;
  p?: String;
  d?: String;
  site?:String;
```
### Generation QR by type
All types in array:
`['TEXT', 'URL', 'PHONE', 'SMS', 'EMAIL', 'CONTACT', 'WIFI', 'LOCATION']`

All types exact explanation:
____
- Text generation
```ts
Parameters:
  text: String;
  type: TEXT;
```
___
- URL generation
```ts
Parameters:
  url: String;
  type: URL;
```
___
- Phone number
```ts
Parameters:
  phone: String;
  type: PHONE;
```
___
- SMS
```ts
Parameters:
  phone: String; (валидатор для номеров)
  text: String;
  type: SMS;
```
___
- Email message
```ts
Parameters:
  email: String;
  subject: String;
  text: String;
  type: EMAIL;
```
___
- Contacts
```ts
Parameters:
  firstName: String;
  lastName?: String;
  organization?: String;
  title?: String;
  email?: String;
  phone?: String;
  mobilePhone?: String; (same)
  fax?: String;
  street?: String;
  city?: String;
  region?: String;
  postcode?: String;
  country?: String;
  url?: String;
  type: CONTACT;
```
___
- WiFi Connection
```ts
Parameters:
  ssid: String;
  password?: String;
  encryption?: WEP | WPA;
  type: WIFI;
```
_____
- Location Generating
```ts
Parameters:
  latitude: float; (dot - separator)
  longitude: float;
  distance: integer;
  type: LOCATION;
```
### Picture Configuration Parameters
```ts
color?: String; (формат hex)
background?: String; (формат hex)
fileType?: FileType;
x?: Integer; // размер картинки в px (макс - 2048px)
correctionLevel?: CorrectionLevel;
FileType: [svg, png, jpg, jpeg]
CorrectionLevel: [L, M, Q, H]
```
### CorrectionLevel explanation
![Image](https://github.com/Eustrosoft/Documentation/assets/30436662/d2b6b346-dbc1-437a-9c24-ff0d1f4c1ce0)

# QR Code Telegram Bot
## Getting a Telegram Bot API key
- Write to a `BotFather` in `Telegram` to get your `API key`;
- Configure your bot as you like, you may choose name, description and other settings;
- Recommended command to add is `/types` - to add a command for showing available qr types.
## Project setup & properties
### Dependencies
All dependencies, that you need are showed here:
- java-telegram-bot-api 7.9.1
- gson-2.10.1
- logging-interceptor-4.12.0
- okhttp-4.12.0
- okio-3.6.0
- okio-jvm-3.6.0

### Properties
There are options to configure generated QR codes.
- dicrectly in code you can configure `URL` to choose options for qr code, that will be generating
- properties file provides an ability to write a token and `URL` to the generating servlet direct, also `demon` property to define will is detach from console or no.\ 
Fill `bot.token` with your telegram bot token, and qxyz.url with URL to servlet

### Setup
The main principle consists of using servlet as QR generating platform for telegram bot. 
Therefore, you should provide an ability to generate a QR code from `Telegram Bot application`

**Maven**
To build the application with maven, go to `Bots` directory:
```shell
cd Bots/
```
Then, build with maven:
```shell
mvn clean package
```
To clean the `target` folder
```shell
mvn clean
```

**Makefile**
To build with `Makefile`, stay in parent directory and use (!!make sure all dependencies are in `lib`):
```shell
make all
```
To clean the working directory, use:
```shell
make clean
```

## Starting the application
### Starting bot as java application
You might want to start this application for testing, then use starting as java application (property demon should be `false`)

Go in `work` folder, you will find `jar` files and `shell` scripts to run application as a demon
```shell
cd work/
```

To start an application as java application, use:
```shell
java -jar telegramBot.jar
```

### Starting bot as demon
To start an application as deamon, use scripts in `work` folder:
```shell
./startBot.sh
```
Running this script will create a file with `PID` of an application, to close it with `stopBot.sh` script
```shell
./stopBot.sh
```

The application will automatically try to create a log file next to the `jar` file
