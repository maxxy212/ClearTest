package com.uk.clearscore

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 02/06/2022
 */
object FileReader {
    fun readStringFromFile(fileName: String): String {
        val br = BufferedReader(InputStreamReader(FileInputStream("../app/src/main/res/raw/" + fileName)))
        val sb = java.lang.StringBuilder()
        var line = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }

        return sb.toString()
    }
}