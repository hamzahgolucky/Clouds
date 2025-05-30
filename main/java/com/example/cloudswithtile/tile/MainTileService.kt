package com.example.cloudswithtile.tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.ButtonColors
import androidx.wear.protolayout.material.Colors
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.example.cloudswithtile.R
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.tiles.SuspendingTileService

private const val RESOURCES_VERSION = "0"

/**
 * Skeleton for a tile with no images.
 */
@OptIn(ExperimentalHorologistApi::class)
class MainTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val singleTileTimeline = TimelineBuilders.Timeline.Builder().addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder().setLayout(
                LayoutElementBuilders.Layout.Builder().setRoot(tileLayout(clickable = true)).build()
            ).build()
        ).build()

        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }
}

private fun tileLayout(clickable: Boolean): LayoutElementBuilders.LayoutElement {
    return LayoutElementBuilders.Box.Builder()
        .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
        .setWidth(DimensionBuilders.expand())
        .setHeight(DimensionBuilders.expand())
        .addContent(
            LayoutElementBuilders.Text.Builder()
                .setText("yo")
                .build()
        )
        .build()
}

private fun searchLayout(
    context: Context,
    clickable: ModifiersBuilders.Clickable,
) = Button.Builder(context, clickable)
    .setContentDescription(context.getString(R.string.tile_messaging_search))
    .setIconContent(R.drawable.cat2.toString())
    .setButtonColors(ButtonColors.secondaryButtonColors(Colors.DEFAULT))
    .build()

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreview() {
    val Context = LocalContext.current
    LayoutRootPreview(root = tileLayout(clickable = true))

}