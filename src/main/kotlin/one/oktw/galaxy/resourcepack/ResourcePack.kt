/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2019
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.oktw.galaxy.resourcepack

import com.google.common.hash.Hashing
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.URI


class ResourcePack(url: String) {
    private var uri = URI(url)
    private var hash = ""

    init {
        this.hash = getHashFromUri(uri)
    }

    fun updateHash(url: String) {
        this.uri = URI(url)
        this.hash = getHashFromUri(uri)
    }

    fun getHash(): String {
        return this.hash
    }

    @Throws(FileNotFoundException::class)
    private fun getHashFromUri(uri: URI): String {
        try {
            val hasher = Hashing.sha1().newHasher()
            openStream(uri).use { input ->
                val buf = ByteArray(256)
                while (true) {
                    val read = input.read(buf)
                    if (read <= 0) {
                        break
                    }
                    hasher.putBytes(buf, 0, read)
                }
            }
            return hasher.hash().toString()
        } catch (e: IOException) {
            val ex = FileNotFoundException(e.toString())
            ex.initCause(e)
            throw ex
        }
    }

    @Throws(IOException::class)
    private fun openStream(uri: URI): InputStream {
        return uri.toURL().openStream()
    }
}