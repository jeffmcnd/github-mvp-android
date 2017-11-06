package xyz.mcnallydawes.githubmvp.userdetail

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
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

        val fragmentController = Robolectric.buildFragment(UserDetailFragment::class.java)

        view = fragmentController.get()
        view.setPresenter(presenter)

        fragmentController.create().start().resume()
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
    fun testSetUsernameTv() {
        view.setUsernameTv(dummyUser.username!!)
        assertEquals(view.usernameTv.text.toString(), dummyUser.username)
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

}