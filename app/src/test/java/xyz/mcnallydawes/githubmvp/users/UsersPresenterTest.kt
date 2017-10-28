package xyz.mcnallydawes.githubmvp.users

import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import xyz.mcnallydawes.githubmvp.RxImmediateSchedulerRule
import org.mockito.Mockito.`when` as whenever
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository

class UsersPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulers = RxImmediateSchedulerRule()
    }

    private lateinit var presenter: UsersPresenter
    @Mock private lateinit var view: UsersContract.View
    @Mock private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val user = User(1, "", "", "")
        whenever(userRepo.getUsers(0))
                .thenReturn(Single.just(arrayListOf(user)))
        presenter = UsersPresenter(view, userRepo)
        presenter.initialize()
    }

    @Test
    fun testUserClicked() {
        val user = User(1, "", "", "")
        presenter.onUserClicked(user)
        verify(view).showUserView(user)
    }

    @Test
    fun testUserRemoved() {
        val user = User(1, "", "", "")
        presenter.onUserRemoved(user)
        verify(view).removeUser(user)
    }

    @Test
    fun testOnNextPage() {
        val user = User(1, "", "", "")
        presenter.onNextPage()
        verify(view).addUsers(arrayListOf(user))
    }

}