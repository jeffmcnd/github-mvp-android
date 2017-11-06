package xyz.mcnallydawes.githubmvp.userdetail

import io.reactivex.Observable
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

class UserDetailPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulers = RxImmediateSchedulerRule()
    }

    private lateinit var presenter: UserDetailPresenter
    @Mock private lateinit var view: UserDetailContract.View
    @Mock private lateinit var userRepo: UserRepository

    private val dummyUser = User(id = 0, avatarUrl = "avatarUrl", name = "name", username = "username", location = "location")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = UserDetailPresenter(view, userRepo)
    }

    @Test
    fun testConstructor() {
        verify(view).setPresenter(presenter)
    }

    @Test
    fun testInitialize() {
        whenever(userRepo.get(0)).thenReturn(Observable.just(dummyUser))

        presenter.initialize(0)
        verify(view).setAvatarIv(dummyUser.avatarUrl!!)
        verify(view).setNameTv(dummyUser.name!!)
        verify(view).setUsernameTv(dummyUser.username!!)
        verify(view).setLocationTv(dummyUser.location!!)
    }

    @Test
    fun testTerminate() {

    }

    @Test
    fun testOnBackBtnClicked() {
        presenter.onBackBtnClicked()
        verify(view).showPreviousView()
    }

}