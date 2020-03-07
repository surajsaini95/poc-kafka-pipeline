package com.knoldus

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class ProductProducer {

  def writeProductToKafkaTopic(topic: String): Unit = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val productJsonData: String = ProductUtil.getProductJsonData
    val productList: List[Product] = ProductUtil.getProductDataFromJsonToList(productJsonData)

    productList.foreach(product => {
      val productRecord = new ProducerRecord[String, String](topic, "key", product.toString)
      producer.send(productRecord)
      println("product launched")
    })


    ProductUtil.writeProductToFile(productList)

    producer.close()

  }
}

object EmployeeProducerOb extends App {
  val employeeProducer = new ProductProducer
  employeeProducer.writeProductToKafkaTopic("product-launch")
}