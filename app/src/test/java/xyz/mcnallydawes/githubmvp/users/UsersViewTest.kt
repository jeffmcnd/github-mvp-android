package xyz.mcnallydawes.githubmvp.users

import android.view.View
import kotlinx.android.synthetic.main.fragment_users.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
import xyz.mcnallydawes.githubmvp.data.model.local.User
import org.robolectric.shadows.ShadowApplication
import android.content.Intent
import xyz.mcnallydawes.githubmvp.Constants
import xyz.mcnallydawes.githubmvp.userdetail.UserDetailActivity


@RunWith(RobolectricTestRunner::class)
class UsersViewTest {

    private lateinit var view: UsersFragment
    @Mock private lateinit var presenter: UsersContract.Presenter

    private val dummyUser = User(0, "", "", "")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val fragmentController = Robolectric.buildFragment(UsersFragment::class.java)

        view = fragmentController.get()
        view.setPresenter(presenter)

        fragmentController.create().start().resume()
    }

    @Test
    fun testSetupUserList() {
        view.setupUserList()
        assertNotNull(view.recyclerView.layoutManager)
        assertNotNull(view.recyclerView.adapter)
    }

    @Test
    fun testAddUser() {
        view.setupUserList()

//        expect one item in list at start, which represents the progress bar
        assertEquals(1, view.recyclerView.adapter.itemCount)

        view.addUser(dummyUser)

        assertEquals(2, view.recyclerView.adapter.itemCount)
    }

    @Test
    fun testAddUsers() {
        view.setupUserList()
        view.addUsers(arrayListOf(dummyUser, dummyUser))

        assertEquals(3, view.recyclerView.adapter.itemCount)
    }

    @Test
    fun testUpdateUser() {
        view.setupUserList()
        view.addUser(dummyUser)

        dummyUser.username = "a"

        view.updateUser(dummyUser)

        assertTrue((view.recyclerView.adapter as UserAdapter).getUser(0) == dummyUser)
    }

    @Test
    fun testRemoveUser() {
        view.setupUserList()
        view.addUser(dummyUser)
        view.removeUser(dummyUser)

        assertEquals(1, view.recyclerView.adapter.itemCount)
    }

    @Test
    fun testRemoveAllUsers() {
        view.setupUserList()
        view.addUsers(arrayListOf(dummyUser, dummyUser, dummyUser))
        view.removeAllUsers()

        assertEquals(1, view.recyclerView.adapter.itemCount)
    }

    @Test
    fun testShowErrorMessage() {
        view.showErrorMessage()
        assertTrue(ShadowToast.showedToast("An error occurred."))
    }

    @Test
    fun testShowUserView() {
        view.showUserView(dummyUser)

        val expectedIntent = Intent(view.activity, UserDetailActivity::class.java)
        expectedIntent.putExtra(Constants.EXTRA_USER_ID, dummyUser.id)
        val actual = ShadowApplication.getInstance().nextStartedActivity

        assertEquals(expectedIntent.component, actual.component)
    }

    @Test
    fun testHideRefreshLayout() {
        view.swipeRefreshLayout.isRefreshing = true
        view.hideRefreshIndicator()
        assertFalse(view.swipeRefreshLayout.isRefreshing)
    }

    @Test
    fun testShowList() {
        view.recyclerView.visibility = View.INVISIBLE
        assertEquals(View.INVISIBLE, view.recyclerView.visibility)

        view.showList()
        assertEquals(View.VISIBLE, view.recyclerView.visibility)
    }

    @Test
    fun testHideList() {
        assertEquals(View.VISIBLE, view.recyclerView.visibility)

        view.hideList()
        assertEquals(View.INVISIBLE, view.recyclerView.visibility)
    }

}