merchant microservice
=====================

Project generated with help of [http://start.spring.io/]()

Port was `8080` by default but after adding remote configuration
 it is provided by that configuration.
 In result port is `9004`.

run
---

1. Go to `../configuration` directory and run app according to `README.md`
2. Go to `../registry` directory and run app according to `README.md`
2. Go to `../customer` directory and run app according to `README.md`
2. Go to `../merchant` directory and run app according to `README.md`
3. `./mvnw clean package exec:java` in this directory
4. Go to [http://registry1:9009/]() and check that `payback` is registered.
5. Go to [http://localhost:9004/env]()
6. Go to [http://localhost:9004/paybacks]()
7. Go to [http://localhost:9004/discover/homepage/config]() (or '/merchant' or '/customer')
8. Go to [http://localhost:9004/receivers]()
9. Go to [http://localhost:9004/receivers/123abc]()
10. Go to [http://localhost:9004/receivers/123abc]()
11. Go to [http://localhost:9004/givers]()
12. Go to [http://localhost:9004/givers/1]()
13. Add some purchase to see stored paybacks:
    * make POST to [http://localhost:9004/purchases/123abc/1/150]()
    * go again to [http://localhost:9004/paybacks]() to see new paybacks
    
Hystrix integration
-------------------

1. Add purchase: POST [http://localhost:9004/purchases/123abc/1/150]()
2. See paybacks: GET [http://localhost:9004/paybacks]()
3. Kill `customer` service
4. Add purchase: POST [http://localhost:9004/purchases/123abc/1/150]()
   Should take a while because of timeouts
5. Add purchase: POST [http://localhost:9004/purchases/123abc/1/150]()
   Should response immediately because Hystrix has broken circuit
6. See paybacks: GET [http://localhost:9004/paybacks]()
   You should see 2 more paybacks for dummy customer `99999`. It's thanks to fallback methods provoded by us to Hystrix
7. Run `customer` service
8. Wait for some time becuase Hystrix will try to close circuit after longer timeout (30 or 60 seconds?)
9. Add purchase: POST [http://localhost:9004/purchases/123abc/1/150]()
10. See paybacks: GET [http://localhost:9004/paybacks]()
    You should see 1 more proper payback
11. See logs at [http://localhost:9004/hystrix.stream]()
