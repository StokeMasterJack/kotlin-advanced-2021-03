# Coroutines

## Threads

### Threads aren't cheap
### Threads are finite
### Threads aren't available on some platforms (JavaScript)
### Threads are tricky: Race conditions, dead-locks
### Async programming can be ugly


# Coroutine Builders (launch,async)
The only thing yopu can call from a Coroutine Builders (launch,async):
    suspend function
    another coroutine 

### CoroutineScope.launch - starts a new coroutine and returns a job (but no return value)
    Fire and forget - does not have a result
    returns Job 

### CoroutineScope.async - starts a new coroutine and returns a job (but no return value)
    If you need to compute a result


launch and async must be called from a coroutineScope


## Structured Concurrency

### Coroutine Scopes - hierarchy

    coroutineScope1{
        
        coroutineScope2a{
            
        }

        coroutineScope2b{

        }

    }

coroutineScope1 will not complete until it's inner coroutineScopes (coroutineScope2a and coroutineScope2b) are complete
    

## Suspend 


## Dispatchers
Dispatchers.Default  //primary goto
Dispatchers.Main        //for updating the ui
Dispatchers.IO       // lots of blocking IO (like reading from a socket)    


block:  CoroutineScope.() -> Unit
block: suspend CoroutineScope.() -> Unit

## Channels
pub/sub

channel.send(user)

val user = channel.receive()







    What coroutines are/What problems they solve
    Coroutine Builders/Adapting APIs that use callbacks
    Launch vs async
    Exception Handling
    Structured concurrency
    Dispatchers
    Flow
    Coroutine testing