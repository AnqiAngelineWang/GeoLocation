# GeolocApiAssessment

Using the OpenAPI contract in 'src/main/resources', it will generate the assessment API:

- Entrypoint:  /login
- Post body: 
    - username
    - password - at least 8 char long, with upper and lower case char, with digit, with specific char among '_#$%.'
    - ipAddress - valid IPv4 address
- Expected result:
    - HTTP code 200 with a generated uuid and a message including username and city corresponding to IP address
    - HTTP code 400 if a parameter is missing or invalid
    - HTTP code 403 if city correspondig to IP address is outside Canada 