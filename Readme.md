# GetMyStocks!
---


Starting app
---
In GetMyStocks folder:
````
docker-compose up
````
To interact with bot go to telegram and write GetMyStocks!


Usage
---
Java 16

Spring boot frameworks

Tinkoff API

Telegram API

Kafka

And other libraries

Work Logic
---

![alt text](https://github.com/temalead/GetMyStocks-/blob/master/images/logic.drawio.png?raw=true)

Got request from user -> find needed handler -> send to needed kafka topic ->
Got request by stock service -> do some work with request -> sending response

---
