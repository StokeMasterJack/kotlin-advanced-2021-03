package kws.c_coroutines.d_flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun main(args: Array<String>): Unit = runBlocking {

    val viewModel = ViewModelB()

    viewModel.start(this)

    val ui: UI = UI(viewModel)
    launch {
        ui.startCollecting()
    }

    launch {
        ui.startSimulatedIdUpdates()
    }


}


interface MyScopeB : CoroutineScope {

    fun dispatcher(): CoroutineDispatcher {
        val dDefault = Dispatchers.Default  //primary goto
        val dMain = Dispatchers.Main        //for updating the ui
        val dIO = Dispatchers.IO            //io intensive work
        return dDefault
    }

    override val coroutineContext: CoroutineContext get() = dispatcher()
}

class ViewModelB() : MyScopeB {

    private val userIdChannel: Channel<Int> = Channel<Int>()

    private val _userStateFlow: MutableStateFlow<User?> = MutableStateFlow<User?>(value = null)

    val userStateFlow: StateFlow<User?> = _userStateFlow.asStateFlow()

    private fun CoroutineScope.fetchUserFromNetwork(id: Int) = launch {
        delay(1000)
        val user = users.first { it.id == id }
        _userStateFlow.value = user
    }

    fun start(coroutineScope: CoroutineScope) {
        coroutineScope.startInternal()
    }

    private fun CoroutineScope.startInternal() {
        startReceivingUserIds()
    }


    suspend fun setUserIdFromUi(id: Int) {
        userIdChannel.send(id)
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



class UI(private val viewModel: ViewModelB) {

    private var user: User? = null

    suspend fun startCollecting() {
        viewModel.userStateFlow.collect {
            user = it
            render()
        }
    }

    suspend fun startSimulatedIdUpdates() {
        while (true) {
            val id = getRandomUserId()
            delay(1000)
            onUserIdUpdated(id)
        }
    }

    fun render(): String {
        val txtFirstName: String = user?.firstName ?: ""
        val txtLastName: String = user?.lastName ?: ""
        val r = txtFirstName + txtLastName
        println(r)
        return r
    }

    suspend fun onUserIdUpdated(id: Int) {
        viewModel.setUserIdFromUi(id)
    }


}

