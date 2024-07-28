package evo.subjects 

/**
  * Describes anything that an Organism may eat
  * Provides energy to its consumer
  * For example:
  *   Food
  *   Prey Organism
  */
trait Edible extends Objective:
  def energy: Double