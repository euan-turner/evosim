import evo.subjects.Blob
import evo.subjects.Organism
import breeze.linalg.DenseVector
import evo.subjects.Food


class Simulation():
  private val populationSize = 5
  private var generations = 5

  def run(): Unit = 
    var population: List[Organism] = (1 to populationSize)
      .map(i => Blob(DenseVector(0, 0), f"Blob-${i}"))
      .toList
    for i <- 1 to generations do
      println(f"Generation ${i}")
      // val food = (1 to 5).map(i => Food()) // TODO: A food/organism's position is Optional 
      val gen = Generation(population, (DenseVector(0, 0), DenseVector(50, 50)), List())
      population = gen.run()
      gen.printDistances
      population.foreach(o => o.clearPositionHistory())


@main def runSim() = 
  val sim = Simulation()
  sim.run()
