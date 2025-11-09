package com.example.exam_4_recrete.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam_4_recrete.R
import com.example.exam_4_recrete.model.FieldItem
import com.example.exam_4_recrete.model.FormGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader

class FormViewModel(application: Application) : AndroidViewModel(application) {

    private val _formGroups = MutableStateFlow<List<FormGroup>>(emptyList())
    val formGroups = _formGroups.asStateFlow()

    private val _formData = mutableMapOf<Int, String>()
    val formData: Map<Int, String> get() = _formData

    fun updateFieldValue(fieldId: Int, value: String) {
        _formData[fieldId] = value
    }
    fun loadData() {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val inputStream = context.resources.openRawResource(R.raw.info)
            val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
            val rootArray = JSONArray(jsonText)

            val groups = mutableListOf<FormGroup>()

            for (i in 0 until rootArray.length()) {
                val groupArray = rootArray.getJSONArray(i)
                val fields = mutableListOf<FieldItem>()

                for (j in 0 until groupArray.length()) {
                    val obj = groupArray.getJSONObject(j)
                    fields.add(
                        FieldItem(
                            field_id = obj.optInt("field_id"),
                            hint = obj.optString("hint"),
                            field_type = obj.optString("field_type"),
                            keyboard = obj.optString("keyboard"),
                            required = obj.optBoolean("required"),
                            is_active = obj.optBoolean("is_active"),
                            icon = obj.optString("icon")
                        )
                    )
                }
                groups.add(FormGroup(fields))
            }
            _formGroups.value = groups
            Log.d("FormViewModel", "Loaded groups: ${groups.size}")
            groups.forEachIndexed { index, group ->
                Log.d("FormViewModel", "Group $index has ${group.fields.size} fields")
            }
        }
    }
}

