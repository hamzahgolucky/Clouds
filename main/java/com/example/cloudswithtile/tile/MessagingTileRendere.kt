package com.example.cloudswithtile.tile

import android.content.Context
import androidx.wear.protolayout.DeviceParametersBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.ButtonColors
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.layouts.MultiButtonLayout
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import com.example.cloudswithtile.R

private fun searchLayout(
    context: Context,
    clickable: ModifiersBuilders.Clickable,
) = Button.Builder(context, clickable)
    .setContentDescription(context.getString(R.string.tile_messaging_search))
    .setIconContent(R.drawable.cat2.toString())
    .setButtonColors(ButtonColors.secondaryButtonColors(Colors.DEFAULT))
    .build()
