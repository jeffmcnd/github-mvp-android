package xyz.mcnallydawes.githubmvp.users

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import xyz.mcnallydawes.githubmvp.RxImmediateSchedulerRule
import xyz.mcnallydawes.githubmvp.data.model.local.User
import xyz.mcnallydawes.githubmvp.data.source.user.UserRepository
import org.mockito.Mockito.`when` as whenever

class UsersPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulers = RxImmediateSchedulerRule()
    }

    private lateinit var presenter: UsersPresenter
    @Mock private lateinit var view: UsersContract.View
    @Mock private lateinit var userRepo: UserRepository

    private val dummyUser = User()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(userRepo.getAllUsers(0)).thenReturn(Single.just(arrayListOf(dummyUser)))
        whenever(userRepo.removeAll()).thenReturn(Completable.complete())
        presenter = UsersPresenter(view, userRepo)
    }

    @Test
    fun testConstructor() {
        verify(view).setPresenter(presenter)
    }

    @Test
    fun testInitialize() {
        presenter.initialize(0)
        verify(view).setupUserList()
    }

    @Test
    fun testTerminate() {
        presenter.terminate()
    }

    @Test
    fun testUserClicked() {
        presenter.onUserClicked(dummyUser)
        verify(view).showUserView(dummyUser)
    }

    @Test
    fun testUserRemoved() {
        presenter.onUserRemoved(dummyUser)
        verify(view).removeUser(dummyUser)
    }

    @Test
    fun testOnNextPage() {
        presenter.onNextPage()
        verify(view).addUsers(arrayListOf(dummyUser))
    }

    @Test
    fun testOnRefreshList() {
        presenter.onRefreshList()
        verify(view).hideList()
        verify(view).removeAllUsers()
    }

}