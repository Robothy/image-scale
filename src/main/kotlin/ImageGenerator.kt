import java.awt.image.BufferedImage
import java.nio.file.Paths
import javax.imageio.ImageIO

fun main() {

    val w = 9000
    val h = 9000
    val format = "tif"

    val image = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
    val g = image.graphics
    g.fillRect(0, 0, w, h)
    g.drawLine(0, 0, w, h)
    g.drawLine(0, h, w, 0)
    g.drawString("Hello World", w / 2, h / 2)
    g.dispose()

    ImageIO.write(image, format, Paths.get("C:\\tmp\\large-${w}x${h}.$format").toFile())
}