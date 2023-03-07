// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.wear.whereami.complication

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.location.Address
import android.location.Location
import android.location.LocationManager
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationText
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.CountUpTimeReference
import androidx.wear.watchface.complications.data.LongTextComplicationData
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.TimeDifferenceComplicationText
import androidx.wear.watchface.complications.data.TimeDifferenceStyle
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import com.google.wear.whereami.R
import com.google.wear.whereami.WhereAmIActivity.Companion.tapAction
import com.google.wear.whereami.data.LocationResult
import com.google.wear.whereami.data.LocationViewModel
import com.google.wear.whereami.data.ResolvedLocation
import com.google.wear.whereami.getAddressDescription
import com.google.wear.whereami.kt.CoroutinesComplicationDataSourceService
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

class WhereAmIComplicationProviderService : CoroutinesComplicationDataSourceService() {
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate() {
        super.onCreate()
        locationViewModel = LocationViewModel(applicationContext)
    }

    override suspend fun onComplicationUpdate(complicationRequest: ComplicationRequest) =
        toComplicationData(
            complicationRequest.complicationType
        )

    override fun getPreviewData(type: ComplicationType): ComplicationData {
        return toComplicationData(type)
    }

    fun toComplicationData(
        type: ComplicationType
    ): ComplicationData {
        return when (type) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(
                PlainComplicationText.Builder(JulianComplicationText.getJulianDate(Instant.now()).substring(8,12)).build(),
                PlainComplicationText.Builder("Julian Date").build(),
            ).build()
            ComplicationType.LONG_TEXT -> LongTextComplicationData.Builder(
                PlainComplicationText.Builder(JulianComplicationText.getJulianDate(Instant.now())).build(),
                PlainComplicationText.Builder("Julian date").build(),
            )
                //.setTitle()
                .build()
            else -> throw IllegalArgumentException("Unexpected complication type $type")
        }
    }

    fun getTimeAgoComplicationText(fromTime: Instant): TimeDifferenceComplicationText.Builder {
        return TimeDifferenceComplicationText.Builder(
            TimeDifferenceStyle.SHORT_SINGLE_UNIT,
            CountUpTimeReference(fromTime)
        ).apply {
            setMinimumTimeUnit(TimeUnit.MINUTES)
            setDisplayAsNow(true)
        }
    }

    fun getTimeAgoComplicationText(location: LocationResult): ComplicationText {
        return if (location is ResolvedLocation) {
            getTimeAgoComplicationText(Instant.ofEpochMilli(location.location.time)).build()
        } else {
            PlainComplicationText.Builder("--").build()
        }
    }

    fun getAddressDescriptionText(location: LocationResult): ComplicationText {
        return if (location is ResolvedLocation) {
            return PlainComplicationText.Builder(getAddressDescription(location)).build()
        } else {
            PlainComplicationText.Builder(getString(R.string.no_location)).build()
        }
    }

    companion object {
        fun Context.forceComplicationUpdate() {
            if (applicationContext.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val request =
                    ComplicationDataSourceUpdateRequester.create(
                        applicationContext, ComponentName(
                            applicationContext, WhereAmIComplicationProviderService::class.java
                        )
                    )
                request.requestUpdateAll()
            }
        }
    }
}