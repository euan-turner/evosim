import evo.subjects.Blob
import evo.subjects.Organism
import breeze.linalg.DenseVector
import evo.subjects.Food
import scala.collection.mutable.ListBuffer
import evo.subjects.Edible


class Simulation():
  private val populationSize = 5
  private var generations = 1

  def run(): Unit = 
    var population: List[Organism] = (1 to populationSize)
      .map(i => Blob(DenseVector(0, 0), f"Blob-${i}"))
      .toList
    for i <- 1 to generations do
      println(f"Generation ${i}")
      val food = (1 to 8).map(i => Food(DenseVector(0,0), 5)) // TODO: A food/organism's position is Optional 
      val gen = Generation(population, Bbox(0, 0, 50, 50), food.to(ListBuffer))
      population = gen.run()
      gen.printStats
      population.foreach(o => o.clearPositionHistory())


@main def runSim() = 
  val sim = Simulation()
  sim.run()
