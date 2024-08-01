package evo.subjects 

import evo.subjects.Organism
import breeze.linalg.DenseVector
import evo.util.MutateError
import breeze.linalg.norm

// object Blob {
//   val size: Double = 3
//   val speed: Double = 3
//   val perception: Double = 10
//   val reach: Double = 1
//   val lifespan: Int = Int.MaxValue
// }
/**
  * A Blob is the simplest organism
  * It moves directly towards its objective
  * It does not evolve
  * It does not die from aging
  */
class Blob(
  currentPosition: DenseVector[Double], 
  name: String
  // energy: Double
  ) extends Organism(
    currentPosition,
    // Blob.speed,
    // Blob.size,
    // Blob.perception,
    // Blob.reach,
    // Blob.lifespan,
    // energy
  ):
    override def moveTo(pos: DenseVector[Double]): Unit = 
      super.moveTo(pos)
      println(s"${name}: ${super.getPositionHistory.length}") 
    // private var active = true
    // override def isAlive: Boolean = energy > 0
    // def deactivate(): Unit = 
    //   active = false
    // override def isActive: Boolean = active
    // override def mutate(seed: Int): Organism = age
    // override def age: Organism = this
    // override def calcEnergyCost(distance: Double): Double = distance / 10
    // override def applyEnergyCost(cost: Double): Double = 
    //   energy = energy - cost
    //   if energy < 0
