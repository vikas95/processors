package org.clulab.lm

import edu.cmu.dynet.{Expression, ParameterCollection}

class FlairLM extends LM {
  override def mkEmbeddings(words: Iterable[String]): Iterable[Expression] = {
    // TODO
    null
  }
}

object FlairLM {
  def load(modelBaseFilename:String, parameters: ParameterCollection): Unit = {
    // load inside a new task model before training
    // TODO: loadX2i, mkParams, loadParameters
  }

  def load(x2iStream:Iterator[String], parameters: ParameterCollection): Unit = {
    // load inside a task model after training
    // TODO: loadX2i, mkParams
  }
}
