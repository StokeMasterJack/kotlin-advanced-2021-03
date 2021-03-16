package kws.c_coroutines.d_flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

data class User(
    val id: Int,
    val userName: String,
    val firstName: String,
    val lastName: String
)

val users: List<User> = listOf(
    User(1, "dford", "David", "Ford"),
    User(2, "kford", "Kellie", "Ford"),
    User(3, "jblow", "Joe", "Blow")
)

fun getRandomUserId(): Int {
    return Random.nextInt(3) + 1
}

fun main(args: Array<String>): Unit = runBlocking {

    val viewModel = ViewModel()

    viewModel.start(this)

    launch {
        while (true) {
            val id = getRandomUserId()
            viewModel.setUserIdFromUi(id)
            delay(1000)
        }
    }

    viewModel.processUserFlow()


}

interface MyScope : CoroutineScope {

    fun dispatcher(): CoroutineDispatcher {
        val dDefault = Dispatchers.Default  //primary goto
        val dMain = Dispatchers.Main        //for updating the ui
        val dIO = Dispatchers.IO            //io intensive work
        return dDefault
    }

    override val coroutineContext: CoroutineContext get() = dispatcher()
}

class ViewModel() : MyScope {

    val userFlow = flow<User> {
        while (true) {
            val user: User = userChannel.receive()
            emit(user)
        }
    }

    private fun CoroutineScope.fetchUserFromNetwork(id: Int) = launch {
        delay(1000)
        val user = users.first { it.id == id }
        userChannel.send(user)
    }


    fun processUserFlow() = launch {
        userFlow
            .debounce(100)
            .map {
                """
                <div>
                    <div>${it.firstName}</div>
                    <div>${it.lastName}</div>
                </div>
            """.trimIndent()
            }
            .collect {
                println(it)
            }
    }


    private val userIdChannel: Channel<Int> = Channel<Int>()

    private val userChannel: Channel<User> = Channel<User>()

    fun start(coroutineScope: CoroutineScope) {
        coroutineScope.startInternal()
    }

    private fun CoroutineScope.startInternal() {
        startReceivingUserIds()
//        startReceivingUsers()
    }


//    suspend fun sendUser(user: User) = coroutineScope {
//        channel.send(user)
//    }

    suspend fun setUserIdFromUi(id: Int) {
        userIdChannel.send(id)
    }

//    private fun CoroutineScope.startReceivingUsers() {
//        launch {
//            while (true) {
//                val user: User = userChannel.receive()
//                println(user)
//            }
//        }
//    }

    private fun CoroutineScope.startReceivingUserIds() {
        launch {
            while (true) {
                val id: Int = userIdChannel.receive()
                fetchUserFromNetwork(id)
            }
        }
    }


}

