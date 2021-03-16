package kws.c_coroutines.c_channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

fun getRandomUserId():Int{
    return Random.nextInt(3) + 1
}

fun main(args: Array<String>) = runBlocking {

    val viewModel = ViewModel()
    viewModel.start(this)

    while (true) {
        val id = Random.nextInt(3) + 1
        viewModel.setUserIdFromUi(id)
        delay(1000)
    }



}

class ViewModel() {

    private val userIdChannel: Channel<Int> = Channel<Int>()

    private val userChannel: Channel<User> = Channel<User>()

    fun start(coroutineScope: CoroutineScope) {
        coroutineScope.startInternal()
    }

    private fun CoroutineScope.startInternal() {
        startReceivingUserIds()
        startReceivingUsers()
    }

    private fun CoroutineScope.fetchUserFromNetwork(id: Int) = launch {
        delay(1000)
        val user: User = users.first { it.id == id }
        userChannel.send(user)
    }

//    suspend fun sendUser(user: User) = coroutineScope {
//        channel.send(user)
//    }

    suspend fun setUserIdFromUi(id: Int) {
        userIdChannel.send(id)
    }

    private fun CoroutineScope.startReceivingUsers() {
        launch {
            while (true) {
                val user: User = userChannel.receive()
                println(user)
            }
        }
    }

    private fun CoroutineScope.startReceivingUserIds() {
        launch {
            while (true) {
                val id: Int = userIdChannel.receive()
                fetchUserFromNetwork(id)
            }
        }
    }


}

