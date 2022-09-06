package com.sdercolin.vlabeler.repository.update.model

import androidx.compose.runtime.Immutable
import com.sdercolin.vlabeler.env.Version
import com.sdercolin.vlabeler.env.appVersion
import com.sdercolin.vlabeler.env.isDebug
import com.sdercolin.vlabeler.env.isLinux
import com.sdercolin.vlabeler.env.isMacOS
import com.sdercolin.vlabeler.env.isWindows
import com.sdercolin.vlabeler.util.getLocalDate
import com.sdercolin.vlabeler.util.parseIsoTime
import com.sdercolin.vlabeler.util.runIf

@Immutable
data class Update(
    val version: Version,
    val date: String,
    val assetUrl: String,
    val diff: List<Summary>,
) {

    @Immutable
    data class Summary(
        val version: Version,
        val pageUrl: String,
        val date: String,
    )

    val fileName: String
        get() = assetUrl.substringAfterLast('/')

    companion object {
        fun from(releases: List<Release>): Update? {
            val newReleases = releases.mapNotNull {
                val version = Version.from(it.tagName) ?: return@mapNotNull null
                version to it
            }
                .filter { it.first > appVersion }
                .sortedBy { it.first }
                .runIf(!isDebug) { filterNot { it.second.prerelease } }
                .filterNot { it.second.draft }

            if (newReleases.isEmpty()) return null

            val latest = newReleases.last()

            val suffix = when {
                isWindows -> "-win64.zip"
                isMacOS -> "-mac.dmg"
                isLinux -> "-amd64.deb"
                else -> return null
            }

            val asset = latest.second.assets.find { it.name.endsWith(suffix) } ?: return null

            val diff = newReleases.reversed().map { (version, release) ->
                Summary(version, release.htmlUrl, parseLocalDate(release.publishedAt))
            }
            return Update(
                version = latest.first,
                assetUrl = asset.browserDownloadUrl,
                diff = diff,
                date = parseLocalDate(latest.second.publishedAt),
            )
        }

        private fun parseLocalDate(isoTime: String) = getLocalDate(parseIsoTime(isoTime))
    }
}