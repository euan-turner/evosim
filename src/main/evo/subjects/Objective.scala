package evo.subjects

import breeze.linalg.DenseVector

/**
  * Describes anything that an Organism may move towards
  * For example:
  *   Food Source (Edible extends Objective)
  *   Edge of map (for end of generation)
  */
sealed trait Objective:
  def getPosition: DenseVector[Double] 
  def exists: Boolean

/**
* Describes anything that an Organism may eat
* Provides energy to its consumer
* For example:
*   Food
*   Prey Organism
*/
trait Edible extends Objective:
  private var eaten = false

  def getEnergy: Double
  def setPosition(pos: DenseVector[Double]): Unit
  def eat(): Unit = 
    eaten = true
  override def exists: Boolean = !eaten

class Position(val pos: DenseVector[Double]) extends Objective:
  override def getPosition = pos
  override def exists = true