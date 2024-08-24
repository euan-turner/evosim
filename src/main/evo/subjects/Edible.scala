package evo.subjects 

import breeze.linalg.DenseVector

/**
  * Describes anything that an Organism may eat
  * Provides energy to its consumer
  * For example:
  *   Food
  *   Prey Organism
  */
trait Edible extends Objective:
  def getEnergy: Double
  def setPosition(pos: DenseVector[Double]): Unit