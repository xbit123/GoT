package ru.skillbranch.gameofthrones.data

import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

fun HouseResToHouse(houseRes: HouseRes): House {
    val id = houseRes.name.split(" ")[1]
    return House(
        id = id,
        words = houseRes.words,
        titles = houseRes.titles,
        seats = houseRes.seats,
        region = houseRes.region,
        overlord = houseRes.overlord,
        name = houseRes.name,
        heir = houseRes.heir,
        founder = houseRes.founder,
        founded = houseRes.founded,
        diedOut = houseRes.diedOut,
        currentLord = houseRes.currentLord,
        coatOfArms = houseRes.coatOfArms,
        ancestralWeapons = houseRes.ancestralWeapons
    )
}

fun CharacterResToCharacter(characterRes: CharacterRes): Character {
    val id = characterRes.url.substring(characterRes.url.lastIndexOf("/") + 1)
    //TODO houseId
    return Character(
        id = id,
        name = characterRes.name,
        titles = characterRes.titles,
        aliases = characterRes.aliases,
        born = characterRes.born,
        culture = characterRes.culture,
        died = characterRes.died,
        father = characterRes.father,
        gender = characterRes.gender,
        houseId = "",
        mother = characterRes.mother,
        spouse = characterRes.spouse
    )
}