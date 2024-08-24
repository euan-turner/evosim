import collection.immutable.List
import collection.mutable.ListBuffer
import evo.subjects.Organism
import breeze.linalg.DenseVector
import scala.util.Random
import scala.math.{sin, cos}
import breeze.linalg.norm
import evo.subjects.Edible

class Generation(
  private var population: List[Organism],
  private val bbox: (DenseVector[Double], DenseVector[Double]),
  private val food: List[Edible]
  ):
  private var steps: Int = 5
  private var random = Random()

  private def genAlive: Boolean = steps > 0

  private def placeOrganisms(): Unit = 
    return

  private def placeFood(): Unit =
    return

  private def moveOrganism(organism: Organism): Unit = 
    // PRE: Check organism has objective, if not add one
    // Check objective exists
    // ACTION: Move to objective
    // POST: Eat food, die if out of energy
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
    placeOrganisms()
    placeFood()
    while genAlive do step()
    population

  def printDistances: Unit = 
    population.foreach(o =>
      val history = o.getPositionHistory  
      val segments = history.zip(history.tail)
      val dist = segments
        .map((p1, p2) => norm(p2 - p1))
        .reduce((d1, d2) => d1 + d2)
      println(f"${o.getName} travelled ${dist}")
    )

