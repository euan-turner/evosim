import collection.immutable.List
import collection.mutable.ListBuffer
import evo.subjects.Organism
import breeze.linalg.DenseVector
import scala.util.Random
import scala.math.{sin, cos}

class Generation(private var population: List[Organism]):
  private var steps: Int = 5
  private var random = Random()

  private def genAlive: Boolean = steps > 0

  private def moveOrganism(organism: Organism): Unit = 
    val current: DenseVector[Double] = organism.getPosition
    val dist = random.nextInt(10).toDouble
    val theta = random.nextGaussian()
    val vector: DenseVector[Double] = DenseVector(dist * cos(theta), dist * sin(theta))
    val next = current + vector 
    organism.moveTo(next)

  private def step(): Unit =
    // for each alive
    population.foreach(moveOrganism)
    // shuffle population
    population = Random.shuffle(population)
    steps -= 1

  def run(): List[Organism] = 
    while genAlive do step()
    population

