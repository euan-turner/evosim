package evo.subjects

import breeze.linalg.DenseVector

class Food(val position: DenseVector[Double], val energy: Int) extends Edible:

  override def get_position: DenseVector[Double] = position

  override def get_energy: Int = energy


  