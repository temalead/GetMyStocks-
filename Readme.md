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

Spring boot

Tinkoff API

Telegram API

Kafka

Work Logic
---

![Alt text](/home/tema/Downloads/logic.drawio.png)

Got request from user -> find needed handler -> send to needed kafka topic ->
Got request by stock service -> do some work with request -> sending response

---
