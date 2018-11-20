package org.clulab.processors.clu

import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source
import org.clulab.embeddings.word2vec.Word2Vec
import org.clulab.utils.StringUtils

/**
  * An interactive shell for CluProcessor
  * User: mihais
  * Date: 8/2/17
  */
object Embeds {
  def main(args: Array[String]): Unit = {

    val props = StringUtils.argsToProperties(args)

    val inputFolder = props.getProperty("in")
    val outputFolder = props.getProperty("out")

    val file = new File(outputFolder)
    val bw = new BufferedWriter(new FileWriter(file))




    lazy val proc = new PortugueseCluProcessor()
    lazy val w2v = Word2Vec

    // get list of files
    val files = getListOfFiles(inputFolder)
    // filter off non-txt files
    val txtFiles = files.filter(f => f.getName.endsWith(".txt"))
    // for each document
    for (file <- txtFiles){
      val filePointer = Source.fromFile(file.getAbsolutePath)
      // for each line in the document
      for(line <- filePointer.getLines()){
        // tokenize sentences
        val tokenizedLine = proc.tokenizer.tokenize(line)
        // for each sentence
        for(sentence <- tokenizedLine) {
          //sentence.words.foreach(println)
          // for each token
          for (word <- sentence.words) {
            // sanitize token
            val wordSanitized = w2v.sanitizeWord(word, true)
            if (wordSanitized.nonEmpty) {
              bw.write(wordSanitized + " ")
              //println(wordSanitized)
            }
            // add the token to the end of the file
          }
        }
      }
    }
    // close file
    bw.close()

  }

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
}
