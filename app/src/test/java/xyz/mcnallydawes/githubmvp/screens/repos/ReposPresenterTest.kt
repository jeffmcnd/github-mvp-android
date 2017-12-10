package xyz.mcnallydawes.githubmvp.screens.repos

import io.reactivex.Observable
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import xyz.mcnallydawes.githubmvp.RxImmediateSchedulerRule
import xyz.mcnallydawes.githubmvp.data.model.Repo
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.data.repo.repo.RepoRepository
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository
import org.mockito.Mockito.`when` as whenever

class ReposPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulers = RxImmediateSchedulerRule()
    }

    private lateinit var presenter: ReposPresenter
    @Mock private lateinit var view: ReposContract.View
    @Mock private lateinit var userRepo: UserRepository
    @Mock private lateinit var repoRepo: RepoRepository

    private val dummyUser = User(id = 0, avatarUrl = "avatarUrl", name = "name", username = "username", location = "location")
    private val dummyRepo = Repo()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ReposPresenter(view, userRepo, repoRepo)
    }

    @Test
    fun testConstructor() {
        verify(view).setPresenter(presenter)
    }

    @Test
    fun testInitialize() {
        whenever(userRepo.get(dummyUser.id)).thenReturn(Observable.just(dummyUser))
        whenever(repoRepo.getUserRepos(dummyUser.id)).thenReturn(Observable.just(arrayListOf(dummyRepo)))

        presenter.initialize(dummyUser.id)
        verify(view).setTitle(dummyUser.username!!)
        verify(view).setAvatarIv(dummyUser.avatarUrl!!)
        verify(view).setNameTv(dummyUser.name!!)
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