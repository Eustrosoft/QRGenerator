# QR code generating servlet

## Main concept:
This project allows to start server part with java server side for generating qr-images with different variations \
and settings \

All parameters need to be passed through `GET` parameters to servlet

## Project setup:
- web.xml - change config
- Makefile/pom - project compiling
- If you use makefile -> paste libraries in lib/ directory in root of the project

## Dependencies:
- google xing-core 3.4.0
- jfreesvg 3.4.3
- javax.servlet-api 4.0.0

## Generation out system QR codes:
As example this type of url: https://qr.qxyz.ru/?q=123&p=123

```ts
Parameters:
  q: String;
  p?: String;
  d?: String;
  site?:String;
```

## Generation QR by type:
`['TEXT', 'URL', 'PHONE', 'SMS', 'EMAIL', 'CONTACT', 'WIFI', 'LOCATION']`

- Text generation
```ts
Parameters:
  text: String;
  type: TEXT;
```

- URL generation
```ts
Parameters:
  url: String;
  type: URL;
```

- Phone number
```ts
Parameters:
  phone: String;
  type: PHONE;
```

- SMS
```ts
Parameters:
  phone: String; (валидатор для номеров)
  text: String;
  type: SMS;
```

- Email message
```ts
Parameters:
  email: String;
  subject: String;
  text: String;
  type: EMAIL;
```

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

## Picture Configuration Parameters:

```ts
color?: String; (формат hex)
background?: String; (формат hex)
fileType?: FileType; 
x?: Integer; // размер картинки в px (макс - 2048px)
correctionLevel?: CorrectionLevel;

FileType: [svg, png, jpg, jpeg]
CorrectionLevel: [L, M, Q, H] 
```

### CorrectionLevel explaining:

![Image](https://github.com/Eustrosoft/Documentation/assets/30436662/d2b6b346-dbc1-437a-9c24-ff0d1f4c1ce0)
