# QR code generating servlet

web.xml - change config

## Parameters for servlet: 
- text - text encoded url that will be placed in qr
- color - color of qr code
- background - background color
- fileType - type of output file. Supported formats: (SVG("svg"), PNG("png"), WBMP("wbmp"), JPG("jpg"), JPEG("jpeg"))
- correctionLevel - level of correction. Supported: (L, M, Q, H)
- x - size for sqared image 

### New params for Eustrosoft part

QRDto was added as standard interface for generating qr codes params

Added QREustrosoftParams class to manipulate new parameters, as q, p, d, site

New parameter was added in web.xml BASIC_REDIRECT_URL to set url builder first path, that will connect the other

Logic to create qr:

```
If has `q` param - generation will fo through EustrosoftPath. Project has QRParamFactory to check the way to build QRParams
Other params are optional for eustrosoft and has default values:
 - Size (165)
 - Color (black)
 - Background (white)
 - Error correction (L)
 - File type (png)
```

Other way - fill `text` param.