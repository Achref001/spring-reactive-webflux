**~~Remarks:**~~ 


We are using an embedded database, this means everytime we restart the application we need to populate the db with new entries 

-----------------
**Error Handling:**
_Mono.error(new SomeException())_
_Flux.error(new SomeException())_

_.onErrorReturn()_ --> can be used to provide a fallback object when onError signal is emitted
_.onErrorResume(ex -> )_ --> has similar functionality but it takes the exception that has been thrown as an argument in the lambda expression, using onErrorResume we can analyze the exception and return the fallback object based on it
_.onErrorMap(ex -> otherException)_ --> can be used to transform from one exception type to another. ex: we can take any generic exception and throw a custom one that can be called by the exception handler

_.doFinally(signalType -> doStuff())_ --> acts just like the finally block of try catch exception and it will be executed whether the data stream completes successfully or with errors  

-----------------
**Peek Operators:** 

they are useful for debugging purposes as well as for preparing resources before processing and closing them
They are a total of 12 peek operators (most of them can be used for mono and flux types whereas some can be applied for either mono of flux types)
Peek operators can be call at various stages depending on their use cases. ex:
- doFirst() --> can be called before the subscription happens
- doOnSubscribe() --> called immediately after the subscriber initiate a subscription 
- doOnRequest() --> can be used to execute a long consumer when the subscriber is sending request 
- doOnNext() 
following event are executed when a onCancel, onError, onComplete and onSuccess signal is emitted
- doOnCancel()
- doOnError()
- doOnComplete() --> Specific for Flux type
- doOnSuccess() --> Specific for Mono type

- doOnterminate() --> it will be execute everytime when a reactive stream end, either it is completion or  error
- doAfterTerminate() --> executed after the last element is propagated to the data stream
- doFinally() --> Always be executed at the end of the chain

---------------------

- UserService will call github api to retrieve some data
- While saving into the database we might think about annotating the method with @transactional, so that the save action will be rolled back if an error occur.
- .block() --> it forces our mono influx to wait for the completion of the async request by effectively transforming it into a synchronous block of code 
- one of the most popular use case of a filter is adding or changing headers of a request 