package com.example.exam_4

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.exam_4.common.BaseFragment
import com.example.exam_4.databinding.FragmentDynamicFormBinding
import com.google.android.material.card.MaterialCardView
import org.json.JSONArray
import com.example.exam_4.model.Field

class DynamicFormFragment :
    BaseFragment<FragmentDynamicFormBinding>(FragmentDynamicFormBinding::inflate) {
    private val fieldValues = mutableMapOf<Int, String>()
    private lateinit var container: LinearLayout
    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() {

        val inputStream = resources.openRawResource(R.raw.info_json)
        val json = inputStream.bufferedReader().use { it.readText() }

        val sections = JSONArray(json)
        for (i in 0 until sections.length()) {
            val sectionArray = sections.getJSONArray(i)


            val card = MaterialCardView(requireContext()).apply {
                setCardBackgroundColor(Color.WHITE)
                radius = 16f
                cardElevation = 6f
                setContentPadding(24, 24, 24, 24)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 16, 0, 0)
                }
            }

            val innerLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
            }

            for (j in 0 until sectionArray.length()) {
                val obj = sectionArray.getJSONObject(j)
                val field = Field(
                    obj.getInt("field_id"),
                    obj.getString("hint"),
                    obj.getString("field_type"),
                    obj.optString("keyboard"),
                    obj.optBoolean("required"),
                    obj.optBoolean("is_active"),
                    obj.optString("icon")
                )

                val editText = EditText(requireContext()).apply {
                    hint = field.hint
                    setPadding(20, 40, 20, 40)
                    setBackgroundResource(android.R.color.transparent)
                    setTextColor(Color.BLACK)
                    textSize = 16f
                    inputType = when (field.keyboard) {
                        "number" -> InputType.TYPE_CLASS_NUMBER
                        "text" -> InputType.TYPE_CLASS_TEXT
                        else -> InputType.TYPE_CLASS_TEXT
                    }


                    addTextChangedListener {
                        fieldValues[field.fieldId] = it.toString()
                    }
                }

                innerLayout.addView(editText)
            }

            card.addView(innerLayout)
            container.addView(card)
        }

        val registerButton = Button(requireContext()).apply {
            text = "Register"
            setOnClickListener {
                onRegisterClicked()
            }
        }

        container.addView(registerButton)
    }

    private fun onRegisterClicked() {

        for ((id, value) in fieldValues) {
            Log.d("DynamicForm", "Field $id = $value")
        }
        Toast.makeText(requireContext(), "Values saved!", Toast.LENGTH_SHORT).show()
    }

}
