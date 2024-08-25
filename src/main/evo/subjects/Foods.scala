package evo.subjects

import breeze.linalg.DenseVector

class Food(private var position: DenseVector[Double], private val energy: Double) extends Edible:

  override def setPosition(pos: DenseVector[Double]): Unit = 
    position = pos

  override def getPosition: DenseVector[Double] = position

  override def getEnergy: Double = energy


  