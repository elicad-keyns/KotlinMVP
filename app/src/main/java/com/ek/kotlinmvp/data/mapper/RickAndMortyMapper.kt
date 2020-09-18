package com.ek.kotlinmvp.data.mapper

import com.ek.kotlinmvp.data.local.rickAndMorty.*
import com.ek.kotlinmvp.data.net.*

fun RickAndMorty.toLocalRAM() = LocalRAM(
    localInfo = info.toLocalInfo(),
    localResults = results.toArrayLocalResults()
)

private fun ArrayList<Results>.toArrayLocalResults() =
    this.map { it.toLocalResults() }.toCollection(ArrayList())

private fun Results.toLocalResults() = LocalResults(
    localId = id,
    localName = name,
    localStatus = status,
    localSpecies = species,
    localType = type,
    localGender = gender,
    localOrigin = origin.toLocalOrigin(),
    localLocation = location.toLocalLocation(),
    localImage = image,
    localEpisode = episode,
    localUrl = url,
    localCreated = created
)

private fun Origin.toLocalOrigin() = LocalOrigin(
    localName = name,
    localUrl = url
)

private fun Location.toLocalLocation() = LocalLocation(
    localName = name,
    localUrl = url
)

private fun Info.toLocalInfo() = LocalInfo(
    localCount = count,
    localPages = pages
)

