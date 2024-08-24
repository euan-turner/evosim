package evo.subjects

import breeze.linalg.DenseVector

/**
  * Describes anything that an Organism may move towards
  * For example:
  *   Food Source (Edible extends Objective)
  *   Edge of map (for end of generation)
  */
trait Objective:
  def getPosition: DenseVector[Double] 