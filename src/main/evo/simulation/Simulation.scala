import evo.subjects.Blob
import evo.subjects.Organism
import breeze.linalg.DenseVector


class Simulation():
  private val populationSize = 5
  private var generations = 5

  def run(): Unit = 
    var population: List[Organism] = (1 to populationSize)
      .map(i => Blob(DenseVector(0, 0), f"Blob-${i}"))
      .toList
    for i <- 1 to generations do
      println(f"Generation ${i}")
      val gen = Generation(population)
      population = gen.run()
      population.foreach(o => o.clearPositionHistory())


@main def runSim() = 
  val sim = Simulation()
  sim.run()
