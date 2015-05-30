package com.szadowsz.io.write.xml

import scala.collection.mutable.LinkedHashMap
import javax.xml.transform.{TransformerException, OutputKeys, TransformerFactory}
import java.io._
import org.w3c.dom.Element
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.dom.DOMSource

object XMLWriter {

  protected val _defaults: LinkedHashMap[String, String] = LinkedHashMap(OutputKeys.OMIT_XML_DECLARATION -> "no", OutputKeys.METHOD -> "xml", OutputKeys.INDENT -> "yes",
    OutputKeys.ENCODING -> "Cp1252", "{http://xml.apache.org/xslt}indent-amount" -> "4")

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param filePath - the file path string to print to
   * @throws IOException
   * @throws TransformerException
   */
  @throws(classOf[IOException])
  @throws(classOf[TransformerException])
  def writeDocumentToFile(element: Element, filePath: String) {
    writeDocumentToFile(element, new File(filePath))
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param filePath - the file path string to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  @throws(classOf[IOException])
  @throws(classOf[TransformerException])
  def writeDocumentToFile(element: Element, filePath: String, outputProperties: LinkedHashMap[String, String]) {
    writeDocumentToFile(element, new File(filePath), outputProperties)
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param file - the file path to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  def writeDocumentToFile(element: Element, file: File) {
    writeDocumentToStream(element, new FileOutputStream(file, false), _defaults)
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param file - the file to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  def writeDocumentToFile(element: Element, file: File, outputProperties: LinkedHashMap[String, String]) {
    writeDocumentToStream(element, new FileOutputStream(file, false), outputProperties)
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param out - the outstream to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  def writeDocumentToStream(element: Element, out: OutputStream) {
    writeDocumentToStream(element, out, _defaults)
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param out - the outstream to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  @throws(classOf[IOException])
  @throws(classOf[TransformerException])
  def writeDocumentToStream(element: Element, out: OutputStream, outputProperties: LinkedHashMap[String, String]) {
    writeDocumentToStream(element, out, outputProperties.getOrElse(OutputKeys.ENCODING, "Cp1252"), outputProperties)
  }

  /**
   * Method to print Elements out to a stream
   *
   * @param element - the root element to print
   * @param out - the outstream to print to
   * @throws IOException TODO
   * @throws TransformerException TODO
   */
  @throws(classOf[IOException])
  @throws(classOf[TransformerException])
  protected def writeDocumentToStream(element: Element, out: OutputStream, encoding: String, outputProperties: LinkedHashMap[String, String]) {
    val tf = TransformerFactory.newInstance()
    val transformer = tf.newTransformer()

    outputProperties.foreach(pair => transformer.setOutputProperty(pair._1, pair._2))

    transformer.transform(new DOMSource(element), new StreamResult(new OutputStreamWriter(out, encoding)))
  }

}