package xyz.mcnallydawes.githubmvp.screens.repos

import android.view.View
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.activity_repos.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
import xyz.mcnallydawes.githubmvp.data.model.User
import org.mockito.Mockito.`when` as whenever

@RunWith(RobolectricTestRunner::class)
class ReposViewTest {

    private lateinit var view: ReposActivity
    @Mock private lateinit var presenter: ReposContract.Presenter

    private val dummyUser = User(id = 0, avatarUrl = "avatarUrl", name = "name", username = "username", location = "location")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val activityController = Robolectric.buildActivity(ReposActivity::class.java)
        view = activityController.get()

        view.setPresenter(presenter)
        activityController.create().start().resume()
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
                view.supportActionBar?.title
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
        view.userProgressBar.visibility = View.GONE
        assertEquals(view.userProgressBar.visibility, View.GONE)

        view.showUserProgressbar()
        assertEquals(view.userProgressBar.visibility, View.VISIBLE)
    }

    @Test
    fun testHideProgressbar() {
        view.userProgressBar.visibility = View.VISIBLE
        assertEquals(view.userProgressBar.visibility, View.VISIBLE)

        view.hideUserProgressbar()
        assertEquals(view.userProgressBar.visibility, View.GONE)
    }

}