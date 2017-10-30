package xyz.mcnallydawes.githubmvp.data.source.user

import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.mcnallydawes.githubmvp.data.model.local.User

@RunWith(AndroidJUnit4::class)
class UserLocalDataSourceTest {

    private lateinit var localDataSource: UserLocalDataSource

    @Before
    fun setup() {
        localDataSource = UserLocalDataSource.getInstance()
    }

    @After
    fun tearDown() {
        val observer = localDataSource.removeAllUsers().test()
        observer.awaitTerminalEvent()
    }

    @Test
    fun testPreconditions() {
        assertNotNull(localDataSource)
    }

    @Test
    fun testSaveUser() {
        val user = User()

        with (localDataSource) {
            val observer = saveUser(user).test()
            observer.awaitTerminalEvent()

            val getObserver = getUser(user.username).test()
            getObserver.awaitTerminalEvent()
            getObserver
                    .assertNoErrors()
                    .assertValue({ u ->
                        u.id == user.id
                    })
        }
    }

    @Test
    fun testSaveUsers() {
        val users = arrayListOf(User(id = 1), User(id = 2), User(id = 3))

        with (localDataSource) {
            var saveObserver = saveUsers(users).test()
            saveObserver.awaitTerminalEvent()

            val getObserver = getUsers(users[0].id - 1).test()
            getObserver.awaitTerminalEvent()
            getObserver
                    .assertNoErrors()
                    .assertValue({ usersResult ->
                        var assertion = true
                        if (usersResult.size != users.size) assertion = false

                        if (assertion) {
                            for (i in 0 until users.size) {
                                if (users[i].id != usersResult[i].id) {
                                    assertion = false
                                    break
                                }
                            }
                        }

                        assertion
                    })
        }
    }

    @Test
    fun testRemoveAllUsers() {
        val user = User()

        with (localDataSource) {
            val saveObserver = saveUser(user).test()
            saveObserver.awaitTerminalEvent()

            val deleteObserver = removeAllUsers().test()
            deleteObserver.awaitTerminalEvent()
            deleteObserver.assertNoErrors()

            val getObserver = getUsers(user.id - 1).test()
            getObserver.awaitTerminalEvent()
            getObserver.assertNoErrors()
                    .assertValue({ users ->
                        users.size == 0
                    })
        }
    }

    @Test
    fun testRemoveUser() {
        val user1 = User(id = 1)
        val user2 = User(id = 2)

        with (localDataSource) {
            var saveObserver = saveUser(user1).test()
            saveObserver.awaitTerminalEvent()
            saveObserver = saveUser(user2).test()
            saveObserver.awaitTerminalEvent()

            val deleteObserver = removeUser(user2.id).test()
            deleteObserver.awaitTerminalEvent()
            deleteObserver.assertNoErrors()

            val getObserver = getUsers(user1.id - 1).test()
            getObserver.awaitTerminalEvent()
            getObserver
                    .assertNoErrors()
                    .assertValue({ users ->
                        users.size == 1 && users[0].id == user1.id
                    })
        }
    }
}
