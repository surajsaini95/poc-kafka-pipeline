package com.knoldus

import java.io.{BufferedWriter, File, FileWriter}

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write

object ProductUtil {

  def getProductJsonData: String = {
    """  [
      {
        "productId": "1357",
        "productName": "Shirt",
        "productBrand": "Mufti"
      }
    ]
  """
  }

  def getProductDataFromJsonToList(productJson: String): List[Product] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val parsedJsonData = net.liftweb.json.parse(productJson)
    parsedJsonData.children.map { data =>
      data.extract[Product]
    }
  }

  def writeProductToFile(productData: List[Product]): Unit = {
    productData.foreach(data => {
      val writer = new BufferedWriter(new FileWriter(new File("./src/main/resources/productData.txt")
        , true))
      writer.write("\n" + data.toString)
      writer.close()
    })
  }

  def getProductJsonDataFromObj(product: Product): String = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    write(product)
  }
}
