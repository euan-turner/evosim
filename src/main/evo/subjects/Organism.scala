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
 *   isAlive, isActive, calcEnergyCost, mutate, age
 * 
 * Hooks can utilise genes:
 *   speed, size, perception, reach, lifespan
 * to customise behaviour of a species
 */

abstract class Organism(
  private var currentPosition: DenseVector[Double],
  private val name: String,
  // private var speed: Double,
  // private var size: Double,
  // private var perception: Double,
  // private var reach: Double,
  // private var lifespan: Int,
  private var energy: Double
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

  def updateEnergy(cost: Double): Unit = 
    println(f"${getName} updating energy ${energy} by ${cost}")
    energy += cost

  def moveTo(pos: DenseVector[Double]): Unit = 
    val dist = norm(pos - currentPosition)
    println(f"${getName} is moving ${dist}")
    val energyCost = calcEnergyCost(dist)
    positionHistory += pos 
    currentPosition = pos
    updateEnergy(-energyCost)

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
    println(f"${getName} is eating")
    food.eat()
    foodEaten += food
    updateEnergy(food.getEnergy)

  
  // def getLifespan: Int = lifespan
  // def getGenes: OrganismGenes = OrganismGenes(
  //   size, speed, perception, reach, lifespan
  // )


  def isAlive: Boolean
  def isActive: Boolean
  // def mutate(seed: Int): Organism = ???
  // def age: Organism = ???

  // Hooks for movement
  def calcEnergyCost(distance: Double): Double

  // // Adjust characteristics and state

  // def canSee(point: DenseVector[Double]): Boolean =
  //   norm(point - currentPosition) <= perception
  // def canReach(point: DenseVector[Double]): Boolean =
  //   norm(point - currentPosition) <= reach

  def getEnergyLeft: Double = energy



  