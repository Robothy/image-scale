import java.io.File
import javax.imageio.ImageIO

fun main() {

    println("Width, Height, w * h, File Size")
    val tiffFolder = File("C:\\tmp\\tiff-images")
    tiffFolder.listFiles()?.forEach {
        val imageInputString = ImageIO.createImageInputStream(it.inputStream())
        val reader = ImageIO.getImageReaders(imageInputString).next()
        reader.input = imageInputString
        val w = reader.getWidth(0)
        val h = reader.getHeight(0)
        println("$w, $h, ${w * h}, ${it.length()}")
    }



}