package xyz.mcnallydawes.githubmvp.users

import kotlinx.android.synthetic.main.fragment_users.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UsersViewTest {

    private lateinit var view: UsersFragment
    @Mock private lateinit var presenter: UsersContract.Presenter

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

}