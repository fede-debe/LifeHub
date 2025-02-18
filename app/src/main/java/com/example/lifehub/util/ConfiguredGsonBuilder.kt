package com.example.lifehub.util

import com.fatboyindustrial.gsonjavatime.Converters
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

class ConfiguredGsonBuilder {
    val builder: GsonBuilder
        get() {
            return Converters.registerAll(GsonBuilder())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
                .setFieldNamingPolicy(
                    FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                )
        }
}