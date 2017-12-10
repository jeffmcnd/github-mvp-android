package xyz.mcnallydawes.githubmvp.data.repo.user

import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.mcnallydawes.githubmvp.data.model.User

@RunWith(AndroidJUnit4::class)
class UserLocalDataSourceTest {


    private lateinit var localDataSource: UserLocalDataSource

    @Before
    fun setup() {
        localDataSource = UserLocalDataSource()
    }

    @After
    fun tearDown() {
        val observer = localDataSource.removeAll().test()
        observer.awaitTerminalEvent()
    }

    @Test
    fun testPreconditions() {
        assertNotNull(localDataSource)
    }

    @Test
    fun testSave() {
        val user = User()

        with (localDataSource) {
            val observer = save(user).test()
            observer.awaitTerminalEvent()

            val getObserver = get(user.id).test()
            getObserver.awaitTerminalEvent()
            getObserver
                    .assertNoErrors()
                    .assertValue({ u ->
                        u.id == user.id
                    })
        }
    }

    @Test
    fun testSaveAll() {
        val users = arrayListOf(User(id = 1), User(id = 2), User(id = 3))

        with (localDataSource) {
            val saveObserver = saveAll(users).test()
            saveObserver.awaitTerminalEvent()

            val getObserver = getAllUsers(users[0].id - 1).test()
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
    fun testRemoveAll() {
        val user = User()

        with (localDataSource) {
            val saveObserver = save(user).test()
            saveObserver.awaitTerminalEvent()

            val deleteObserver = removeAll().test()
            deleteObserver.awaitTerminalEvent()
            deleteObserver.assertNoErrors()

            val getObserver = getAllUsers(user.id - 1).test()
            getObserver.awaitTerminalEvent()
            getObserver.assertNoErrors()
                    .assertValue({ users ->
                        users.size == 0
                    })
        }
    }

    @Test
    fun testRemove() {
        val user1 = User(id = 1)
        val user2 = User(id = 2)

        with (localDataSource) {
            var saveObserver = save(user1).test()
            saveObserver.awaitTerminalEvent()
            saveObserver = save(user2).test()
            saveObserver.awaitTerminalEvent()

            val deleteObserver = remove(user2.id).test()
            deleteObserver.awaitTerminalEvent()
            deleteObserver.assertNoErrors()

            val getObserver = getAllUsers(user1.id - 1).test()
            getObserver.awaitTerminalEvent()
            getObserver
                    .assertNoErrors()
                    .assertValue({ users ->
                        users.size == 1 && users[0].id == user1.id
                    })
        }
    }
}
