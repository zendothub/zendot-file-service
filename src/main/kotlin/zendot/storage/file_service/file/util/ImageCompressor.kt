package zendot.storage.file_service.file.util

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

class ImageCompressor {

    companion object {
        fun compress(inputStream: InputStream): InputStream {
            return compress(inputStream, 0.4f)
        }

        fun compress(inputStream: InputStream, quality: Float): InputStream {
            val originalImage = ImageIO.read(inputStream)
            val targetWidth = 1024
            val targetHeight =
                (targetWidth.toDouble() * originalImage.height / originalImage.width).toInt()

            val resultingImage = originalImage.getScaledInstance(
                targetWidth, targetHeight, Image.SCALE_FAST
            )
            val outputImage = BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB
            )
            outputImage.graphics.drawImage(resultingImage, 0, 0, null)

            val writer = ImageIO.getImageWritersByFormatName("jpg").next()
            val output: ByteArrayOutputStream = object : ByteArrayOutputStream() {
                @Synchronized
                override fun toByteArray(): ByteArray {
                    return buf
                }
            }
            writer.setOutput(ImageIO.createImageOutputStream(output))

            val params = writer.defaultWriteParam
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
            params.setCompressionQuality(quality)

            writer.write(null, IIOImage(outputImage, null, null), params)

            output.close()
            writer.dispose()

            return ByteArrayInputStream(output.toByteArray(), 0, output.size())
        }
    }
}