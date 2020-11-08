package dev.filipebezerra.android.aboutme

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil.setContentView
import dev.filipebezerra.android.aboutme.data.AboutPerson
import dev.filipebezerra.android.aboutme.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: MainActivityBinding

    private lateinit var aboutPerson: AboutPerson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<MainActivityBinding>(this, R.layout.main_activity)
            .apply {
                viewBinding = this
                setupEditMode()
            }
            .also { initViewBindingData() }
    }

    private fun MainActivityBinding.setupEditMode() {
        with(nameHeadlineText) {
            setOnClickListener {
                if (nameHeadlineInput.visibility == View.GONE) {
                    nameHeadlineInput.visibility = View.VISIBLE
                    nameHeadlineInput.editText!!.setText(text)
                    nameHeadlineInput.requestFocus()
                    nameHeadlineInput.editText!!.selectAll()
                } else {
                    nameHeadlineInput.editText!!.text.clear()
                    nameHeadlineInput.visibility = View.GONE
                }
            }
        }

        with(nameHeadlineInput) {
            setEndIconOnClickListener {
                if (editText!!.text != nameHeadlineText.text) {
                    aboutPerson?.let {
                        nameHeadlineText.text = it.fullName
                        editText!!.setText(it.fullName)
                        nameHeadlineInput.requestFocus()
                        nameHeadlineInput.editText!!.selectAll()
                    }
                }
            }
        }

        with(nameHeadlineInput.editText!!) {
            doOnTextChanged { text, _, _, _ -> nameHeadlineText.text = text }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!text.isNullOrEmpty()) {
                        aboutPerson?.let {
                            it.fullName = text.toString()
                            viewBinding.invalidateAll()
                        }
                    }
                    nameHeadlineInput.visibility = View.GONE
                    hideKeyboardOn(this@with)
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    private fun initViewBindingData() {
        aboutPerson = AboutPerson(
            fullName = "Filipe Bezerra de Sousa",
            roles = "Owner, Software Engineer, and Android Engineer in Goiânia, State of Goiás, Brazil",
            bio = """
                I am the Owner and CTO at Agência ByITs working with partners archtecting and developing mobile solutions.
                
                Also I am the Project leader and Mobile Developer at Ponto Doméstico App.
                
                I'm currently living in Goiânia, State of Goiás, Brazil.
                
                My interests range from entrepreneurship to technology. I am also interested in martial
                arts as blue belt competitor, innovation, and programming with python and kotlin.
                
                You can click the button above to hire me. If you’d like to get in touch, feel free to 
                say hello through any of the social links below.
                """.trimIndent()
        )
        viewBinding.aboutPerson = aboutPerson
    }

    private fun hideKeyboardOn(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}