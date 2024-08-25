import collection.immutable.List
import collection.mutable.ListBuffer
import evo.subjects.Organism
import breeze.linalg.DenseVector
import scala.util.Random
import scala.math.{sin, cos}
import breeze.linalg.norm
import evo.subjects.Edible
import evo.subjects.Position
import evo.util.ObjectiveError
import breeze.numerics.sqrt

class Generation(
  private var population: List[Organism],
  private val bbox: Bbox,
  private val foods: ListBuffer[Edible]
  ):
  private var steps: Int = 20
  private var random = Random()

  private def genAlive: Boolean = steps > 0

  private def placeOrganisms(): Unit = 
    val Bbox(left, top, width, height) = bbox
    val norganisms = population.size

    if norganisms > 0 then
      val perimeter = 2 * (width + height)
      val spacing = perimeter / norganisms

      population.zipWithIndex.foreach { (organism, index) =>
        val distance = index * spacing

        // Determine which edge the position falls on
        val (x, y) = 
          if distance < width then // Top edge (moving right)
            (left + distance, top)
          else if distance < width + height then // Right edge (moving down)
            (left + width, top + (distance - width))
          else if distance < 2 * width + height then // Bottom edge (moving left)
            (left + (2 * width + height - distance), top + height)
          else // Left edge (moving up)
            (left, top + (perimeter - distance))

        // Set the organism's position
        organism.setPosition(DenseVector(x,y))
      }

  private def placeFood(): Unit =
    val Bbox(left, top, width, height) = bbox
    foods.foreach(f => {
      val xrand = random.nextDouble()
      val yrand = random.nextDouble()
      val x = left + xrand * width
      val y = top + yrand * height
      f.setPosition(DenseVector(x,y))
    })
    
    
  private def preMove(organism: Organism): Unit = 
    // TODO: Once organisms have perception, filter
    // objectives by those they can see, otherwise wander
    if foods.isEmpty then 
      // TODO: Find position on edge that is closest to 
      organism.setObjective(Position(DenseVector(0,0)))
    else
      organism.getObjective match
        case None => {
          val obj = foods.minBy(f => {
            norm(organism.getPosition - f.getPosition)
          })
          organism.setObjective(obj)
        }
        case Some(obj) => {
          if !obj.exists then 
            val obj = foods.minBy(f => {
            norm(organism.getPosition - f.getPosition)
            })
            organism.setObjective(obj)
        }    

  private def doMove(organism: Organism): Unit =
    // TODO: Once organisms have a different speed
    // use their speed instead of moveMaxDist
    val currPos = organism.getPosition
    val objPos = organism.getObjectivePosition
    val vec = objPos - currPos
    val objDist = norm(vec)
    val moveMaxDist: Double = 1
    if objDist < moveMaxDist then 
      organism.moveTo(objPos)
    else
        val move = (vec / norm(vec)) * sqrt(moveMaxDist)
        organism.moveTo(currPos + move)

  private def postMove(organism: Organism): Unit = 
    // TODO: Once organisms have reach
    // use reach instead of hardcoded
    val reach = 0.25
    // If objective is food, then eat it
    // If it's a position, must reach the position
    organism.getObjective match
      case None => throw ObjectiveError("Organism does not have objective in postMove")
      case Some(obj) => obj match
        case food: Edible => 
          if norm(food.getPosition - organism.getPosition) <= reach then 
            organism.eat(food)
            foods -= food
            organism.clearObjective()
        case pos: Position =>
          if norm(pos.getPosition - organism.getPosition) <= 0.001 then 
            organism.clearObjective()
    // TODO: Die if out of energy
    

  private def moveOrganism(organism: Organism): Unit = 
    preMove(organism)
    doMove(organism)
    postMove(organism)

  private def step(): Unit =
    population.foreach(moveOrganism)
    population = Random.shuffle(population)
    steps -= 1

  def run(): List[Organism] = 
    placeOrganisms()
    placeFood()
    while genAlive do step()
    population

  def printStats: Unit = 
    population.foreach(o =>
      val history = o.getPositionHistory  
      val foodEaten = o.getFoodEaten
      val segments = history.zip(history.tail)
      val dist = segments
        .map((p1, p2) => norm(p2 - p1))
        .reduce((d1, d2) => d1 + d2)
      println(f"${o.getName} travelled ${dist} and ate ${foodEaten.size} items")
      println(f"\t Final Position: ${o.getPosition}")
    )
    println(f"${foods.size} left")
