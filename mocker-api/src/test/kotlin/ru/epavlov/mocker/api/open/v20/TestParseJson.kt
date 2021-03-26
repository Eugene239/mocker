package ru.epavlov.mocker.api.open.v20

import com.google.gson.Gson
import org.junit.Test
import java.io.File

class TestParseJson {

    val gson = Gson()

    @Test
    fun tryParse() {
        val file = File(this.javaClass.classLoader.getResource("v20/swagger.json").file)
        assert(file.exists())
        val text = file.readText()
        val model = gson.fromJson(text, OpenApiModel::class.java)
        println("$model")
    }
}