package evo.subjects

import breeze.linalg.DenseVector
import evo.subjects.{Edible, Objective}
import breeze.linalg.norm
import collection.mutable.ListBuffer
import evo.util.ObjectiveError

// TODO: Do these need to be Double?
case class OrganismGenes(
  size: Double, 
  speed: Double, 
  perception: Double, 
  reach: Double, 
  lifespan: Int)

/* 
TODO: Rethink this iteratively, and as abstract class with template methods
1. Organism moves randomly, tracks history, until out of energy
  Needs energy, templates for energy consumption
  All organisms need a speed and to track their energy
2. Organism moves toward a destination
3. Organism moves toward food
  Needs perception and reach
4. Reproduction/aging
  Needs to mutate and replicate
5. All states, active, alive, dead, asleep etc.
 */
/* 
  Role of an organism within the simulation:
    1. Simulation creates organisms at the start
    2. Simulation tracks population of organisms over generations
    3. In a generation, an organism is placed on the boundary of the arena
    4. During a generation, an organism moves towards objectives
    5. At the end of a generation, an organism ages, evolves, and/or dies
 */
/**
 * The template with which specific organisms can be implemented.
 * 
 * Provides standard:
 *
 *   Current position, position history, current energy, food history
 *   speed, size, perception, reach, and lifespan in number of generations.
 * 
 * Hooks to implement:
 *
 *   isAlive, isActive, calcEnergyCost, applyEnergyCost, mutate, age
 * 
 */

abstract class Organism(
  private var currentPosition: DenseVector[Double],
  private val name: String
  // private var speed: Double,
  // private var size: Double,
  // private var perception: Double,
  // private var reach: Double,
  // private var lifespan: Int,
  // private var energy: Double
):
  private val positionHistory = ListBuffer.empty[DenseVector[Double]]
  private var objective: Option[Objective] = None
  private val foodEaten = ListBuffer.empty[Edible]

  def getPosition: DenseVector[Double] = currentPosition
  def setPosition(pos: DenseVector[Double]): Unit =
    currentPosition = pos
  def getPositionHistory: List[DenseVector[Double]] = positionHistory.toList
  def clearPositionHistory(): Unit = positionHistory.clear()
  def getName: String = name

  def getFoodEaten: List[Edible] = foodEaten.toList
  // def getSpeed: Double = speed 
  // def getSize: Double = size 
  // def getPerception: Double = perception
  // def getReach: Double = reach 

  // def applyEnergyCost(cost: Double): Double = ???

  def moveTo(pos: DenseVector[Double]): Unit = 
    // val dist = norm(pos - currentPosition)
    // val energyCost = calcEnergyCost(dist)
    positionHistory += pos 
    currentPosition = pos
    // applyEnergyCost(energyCost)

  def getObjective: Option[Objective] = objective
  def getObjectivePosition: DenseVector[Double] = 
    objective match
      case None => throw ObjectiveError("Organism does not have objective in getObjectivePosition")
      case Some(obj) => obj.getPosition
    
  def setObjective(newObjective: Objective): Unit = 
    objective = Some(newObjective)
  def clearObjective(): Unit =
    objective = None
  
  def eat(food: Edible): Unit = 
    food.eat()
    foodEaten += food

  
  // def getLifespan: Int = lifespan
  // def getGenes: OrganismGenes = OrganismGenes(
  //   size, speed, perception, reach, lifespan
  // )


  // def isAlive: Boolean = energy > 0
  // def isActive: Boolean = ???
  // def mutate(seed: Int): Organism = ???
  // def age: Organism = ???

  // // Hooks for movement
  // def calcEnergyCost(distance: Double): Double = ???
  // // Adjust characteristics and state

  // def canSee(point: DenseVector[Double]): Boolean =
  //   norm(point - currentPosition) <= perception
  // def canReach(point: DenseVector[Double]): Boolean =
  //   norm(point - currentPosition) <= reach
  
  // def eat(food: Edible): Unit =
  //   foodEaten += food 
  //   applyEnergyCost(food.getEnergy)
  // def getEnergyLeft: Double = energy



  