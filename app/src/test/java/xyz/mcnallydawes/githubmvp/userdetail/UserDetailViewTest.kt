package xyz.mcnallydawes.githubmvp.userdetail

import android.view.View
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
import org.robolectric.util.FragmentTestUtil.startFragment
import xyz.mcnallydawes.githubmvp.data.model.local.User
import org.mockito.Mockito.`when` as whenever

@RunWith(RobolectricTestRunner::class)
class UserDetailViewTest {

    private lateinit var view: UserDetailFragment
    @Mock private lateinit var presenter: UserDetailContract.Presenter

    private val dummyUser = User(id = 0, avatarUrl = "avatarUrl", name = "name", username = "username", location = "location")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        view = UserDetailFragment()
        startFragment(view, UserDetailActivity::class.java)

        view.setPresenter(presenter)
    }

    @Test
    fun testSetAvatarIv() {
        view.setAvatarIv(dummyUser.avatarUrl!!)
    }

    @Test
    fun testSetNameTv() {
        view.setNameTv(dummyUser.name!!)
        assertEquals(view.nameTv.text.toString(), dummyUser.name)
    }

    @Test
    fun testTitle() {
        view.setTitle(dummyUser.username!!)
        assertEquals(
                dummyUser.username!!,
                (view.activity as UserDetailActivity).supportActionBar?.title
        )
    }

    @Test
    fun testSetLocationTv() {
        view.setLocationTv(dummyUser.location!!)
        assertEquals(view.locationTv.text.toString(), dummyUser.location)
    }

    @Test
    fun testShowPreviousView() {
        view.showPreviousView()
    }

    @Test
    fun testShowUserNotFoundError() {
        view.showUserNotFoundError()
        assertTrue(ShadowToast.showedToast("Unable to retrieve user"))
    }

    @Test
    fun testShowErrorMessage() {
        view.showErrorMessage("error message")
        assertTrue(ShadowToast.showedToast("error message"))
    }

    @Test
    fun testShowProgressbar() {
        view.progressBar.visibility = View.GONE
        assertEquals(view.progressBar.visibility, View.GONE)

        view.showProgressbar()
        assertEquals(view.progressBar.visibility, View.VISIBLE)
    }

    @Test
    fun testHideProgressbar() {
        assertEquals(view.progressBar.visibility, View.VISIBLE)

        view.hideProgressbar()
        assertEquals(view.progressBar.visibility, View.GONE)
    }

}