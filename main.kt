import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.helper.Validate
import org.jsoup.select.Elements
import java.io.*
import java.lang.Exception

fun main(args: Array<String>){
    val mainLink = "https://w3.cs.jmu.edu/kirkpams/"
    val doc = Jsoup.connect(mainLink + "index.shtml").get()
    val links = doc.select("a[href]")
    val media = doc.select("[src]")
    val imports = doc.select("link[href]")
    var writer:BufferedWriter? = null

    try {
        FileWriter("KirkPatrick/index.html").write(doc.html())
       // getCSS(imports)
        //getIcon(imports)
        //getImage(media)
        //println(media)
        getLinks(links)
       // println(links)

    }catch (e: IOException) {
        println("Couldn't write to a file")
    }

}

/**
 * to get CSS file from the website
 */
fun getCSS(imports: Elements) {
    val stuDomain = "https://w3.cs.jmu.edu"
    for (css in imports) {
        var linkOfCSS = css.attr("href")
        try {
            if(linkOfCSS.contains(".css")) {

                val doc = Jsoup.connect(stuDomain + linkOfCSS).get()
                //println(linkOfCSS.removePrefix("/"))
                //println(doc.wholeText())

                FileWriter("KirkPatrick$linkOfCSS").use { it.write(doc.wholeText()) }
                // because of "/" in css path in index css won't work.
            } else {
                println("CSS file didn't exist")
            }

        } catch (e: IOException) {
            println("couldn't get " + css.attr("href"))
        }
    }
}

/**
 * To get icon file from the website.
 * Use fileoutputstream to get icon.
 */
fun getIcon(imports: Elements) {
    val stuDomain = "https://w3.cs.jmu.edu"
    for(ico in imports) {
        var linkOfIco = ico.attr("href")
        try {
            println(linkOfIco)
            if(linkOfIco.contains(".ico")) {
                println(stuDomain + linkOfIco)
                val doc: Connection.Response = Jsoup.connect(stuDomain + linkOfIco).ignoreContentType(true).execute()
                var out = (FileOutputStream(java.io.File("KirkPatrick$linkOfIco")))
                out.write(doc.bodyAsBytes())
            }
        }catch (e: Exception) {
            println(e.message)
        }
    }
}

/**
 * To get Images from the website
 * Use fileoutputstream to get image it is similer to icon.
 */
fun getImage(imports: Elements) {
    val stuDomain = "https://w3.cs.jmu.edu/kirkpams/"
    for(img in imports) {
        var linkOfImg = img.attr("src")
        try {
            if(linkOfImg.contains(".jpg") || linkOfImg.contains(".jpeg")) {
                println(stuDomain + linkOfImg)          // it gets only half of the picture
                val doc: Connection.Response = Jsoup.connect(stuDomain + linkOfImg).ignoreContentType(true).execute()
                var out = (FileOutputStream(java.io.File("KirkPatrick/$linkOfImg")))
                out.write(doc.bodyAsBytes())
            }
        }catch (e: Exception) {
            print(e.message)
        }
    }
}

fun getLinks(links: Elements) {
    val stuDomain = "https://w3.cs.jmu.edu"

    for(link in links) {
        try {

            var linkOfLinks = link.attr("href")
            if(linkOfLinks.startsWith('/') && linkOfLinks.contains("/kirkpams")) {
                var doc = Jsoup.connect(stuDomain + linkOfLinks).ignoreContentType(true).execute()
                var out = (FileOutputStream(java.io.File("KirkPatrick/$linkOfLinks")))
                out.write(doc.bodyAsBytes())
            }
        }catch (e: Exception) {
            println(e.message)
        }
    }
}