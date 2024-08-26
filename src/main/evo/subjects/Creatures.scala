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
  name: String,
  energy: Double
  ) extends Organism(
    currentPosition,
    name,
    // Blob.speed,
    // Blob.size,
    // Blob.perception,
    // Blob.reach,
    // Blob.lifespan,
    energy
  ):
    override def calcEnergyCost(distance: Double): Double = 
      distance
    
    override def isAlive: Boolean = getEnergyLeft > 0

    // TODO: Change to include FINISHED state
    override def isActive: Boolean = isAlive

    // override def mutate(seed: Int): Organism = age
    // override def age: Organism = this
