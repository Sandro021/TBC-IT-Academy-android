package com.example.exam_1

fun main() {
    val lst = mutableListOf<String>()
    lst.add("skami")
    lst.add("mikas")
    lst.add("vashli")
    println(findAnagrams(lst))
    println(findAnagrams(lst).size)
}

fun findAnagrams(words : List<String> ) : List<List<String>> {
    val res : MutableList<MutableList<String>> = ArrayList()

    for(word in words) {
        val characters = word.toCharArray()
        characters.sort()

        val wordAfterSort = String(characters)

        var bool = false

        for(groups in res) {
            val firstGroup = groups[0]
            val firstWordCharacters = firstGroup.toCharArray()
            firstWordCharacters.sort()
            val firstSort = String(firstWordCharacters)

            if(firstSort == wordAfterSort) {
                groups.add(word)
                bool = true
                break
            }
        }
        if(!bool) {
            val newAnagrams : MutableList<String> = ArrayList()
            newAnagrams.add(word)
            res.add(newAnagrams)
        }
    }
    return res
}
