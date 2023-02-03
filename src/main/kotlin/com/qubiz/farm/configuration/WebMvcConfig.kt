package com.qubiz.farm.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebMvcConfig: WebMvcConfigurer {

    val resourcesLocation = arrayOf("classpath:/resource/", "classpath:/public/")

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations(*resourcesLocation)
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/home", "/")
    }

}
