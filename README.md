# CDSM Feed Challenge 

application is designed to extend fetching feeds from multiple feed sources. for now only nos.nl feed source implemented

### Reference Documentation

as default program will fetch feeds from sources once per every minute. cron definition must be used to change interval

``feedservice.fetch_and_store.cron=0 * * ? * *``

nosnl feed source url

``feedsource.nosnl.url=http://feeds.nos.nl/nosjournaal?format=xml``

### Swagger

http://localhost:8080/swagger-ui.html

### H2 Database Console 

http://localhost:8080/h2-console
 
### TEST

wait for a while to see "fetch and store is started" in logs, then you can see feeds on response

 ``java -jar target/news-feed-0.0.1-SNAPSHOT.jar``

curl http://localhost:8080/feeds

