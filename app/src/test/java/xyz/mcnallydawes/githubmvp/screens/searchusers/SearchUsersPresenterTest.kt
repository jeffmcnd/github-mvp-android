package xyz.mcnallydawes.githubmvp.screens.searchusers

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.ClassRule
import org.junit.Ignore
import org.junit.Test
import xyz.mcnallydawes.githubmvp.RxImmediateSchedulerRule
import xyz.mcnallydawes.githubmvp.data.model.User
import xyz.mcnallydawes.githubmvp.data.repo.user.UserRepository

class SearchUsersPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val rxSchedulers = RxImmediateSchedulerRule()
    }

    private lateinit var presenter: SearchUsersPresenter
    private val view: SearchUsersContract.View = mock()
    private val userRepo: UserRepository = mock()
    private val user = User()
    private val q = "jeff"

    @Before
    fun setUp() {
//        doNothing().`when`(view).setPresenter(any())
        presenter = SearchUsersPresenter(view, userRepo)
    }

    @Test
    fun testInitialize() {
        presenter.initialize()
        verify(view).setUpRecyclerView()
        verify(view).setUpClearBtn()
        verify(view).setUpSearchEditText()
    }

    @Ignore @Test
    fun terminate() {
//    TODO: no logic to test
    }

    @Test fun onBackBtnClicked() {
        presenter.onBackBtnClicked()
        verify(view).goBack()
    }

    @Test fun onClearBtnClicked() {
        presenter.onClearBtnClicked()
        verify(view).clearSearchEditText()
        verify(view).clearRecyclerView()
        verify(view).hideRecyclerView()
        verify(view).showEmptyView()
    }

    @Test fun onSearchActionSuccess() {
        whenever(userRepo.searchUsers(q, null, null))
                .thenReturn(Observable.just(arrayListOf(user)))
        presenter.onSearchAction(q, null, null)
        verify(view).updateUsers(arrayListOf(user))
        verify(view).hideEmptyView()
        verify(view).showRecyclerView()
    }

    @Test fun onSearchActionEmpty() {
        whenever(userRepo.searchUsers(q, null, null))
                .thenReturn(Observable.just(arrayListOf()))
        presenter.onSearchAction(q, null, null)
        verify(view).clearRecyclerView()
        verify(view).showEmptyView()
        verify(view).hideRecyclerView()
    }

    @Test fun onUserClicked() {
        presenter.onUserClicked(user)
        verify(view).goToRepo(user.id)
    }

}