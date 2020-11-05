package dev.filipebezerra.android.aboutme

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dev.filipebezerra.android.aboutme.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = setContentView<MainActivityBinding>(this, R.layout.main_activity)
            .apply {
    //                var currentName = nameHeadlineText.text
    //
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

    //                with(nameHeadlineInput) {
    //                    setEndIconOnClickListener {
    //                        if (!editText!!.text.isNullOrEmpty()) {
    //                            editText!!.text.clear()
    //                        } else {
    //                            nameHeadlineInput.visibility = View.GONE
    //                        }
    //                        nameHeadlineText.text = currentName
    //                    }
    //                }

                with(nameHeadlineInput.editText!!) {
    //                    doOnTextChanged { text, _, _, _ -> nameHeadlineText.text = text }

                    setOnEditorActionListener { _, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
    //                            currentName = text
                            if (!text.isNullOrEmpty()) {
                                nameHeadlineText.text = text
                            }
                            nameHeadlineInput.visibility = View.GONE
                            return@setOnEditorActionListener true
                        }
                        false
                    }
                }
            }
    }

    private fun hideKeyboardOn(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}