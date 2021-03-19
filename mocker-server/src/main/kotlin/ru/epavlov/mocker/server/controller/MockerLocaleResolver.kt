package ru.epavlov.mocker.server.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import ru.epavlov.mocker.server.config.MockConfig
import java.util.*


@Component
class MockerLocaleResolver(@Autowired val config: MockConfig) : AcceptHeaderLocaleResolver() {

    override fun getSupportedLocales(): MutableList<Locale> {
        return config.locale!!.getLocales()
    }

    override fun getDefaultLocale(): Locale? {
        return config.locale!!.getDefault()
    }

}