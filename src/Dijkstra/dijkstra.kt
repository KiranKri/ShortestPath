package Dijkstra

/**
 * Created by kirankrishnamurthy on 4/22/17.
 */
import java.util.*
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.collections.HashMap

/**
 * Name         : Edge
 * Description  : internal data class to represent an edge between two vertices in the graph
 * Members      :
 *      "destination" : represents the destination node for an edge from source to destination
 *      "distance"    : represents the weight for the edge
 */
internal data class Edge(val destination: Vertex, val distance: Double)

/**
 * Name         : Vertex
 * Description  : internal data class to represent a node in the graph
 * Members      :
 *      "edges"         : a list of all Edges from the current node
 *      "minDistance"   : distance from the "startingLocation" node
 *      "previous"      : represents the parent of the current node in the shortest path
 */
internal data class Vertex(val name: String) {
    var edges: MutableList<Edge> = mutableListOf()
    var previous: Vertex? = null
    var minDistance: Double = Double.MAX_VALUE
    override fun toString(): String {
        return name
    }
}
/**
 * Name         : dijkstra
 * Description  : priority queue (min heap) implementation of Dijkstra's shortest path algorithm
 *              : for a graph represented using an adjacency list
 * Parameters   :
 *      "startLocation" : starting node
 *      "endLocation"   : destination node
 *      "edges"         : represents all edges in the graph
 * Returns      : a Map containing the shortest distance and the shortest path if it exists, error message otherwise.
 */
fun dijkstra(startLocation: String, endLocation: String, edges: List<Map<String, Any>>) : Map<String, Any>
{

    if(startLocation == "" || endLocation == "")    //either startingLocation or endingLocation is blank
        return mapOf("distance" to -1,"error" to "Starting and Ending locations must be specified")

    //if both starting location and ending location are same then we can return shortestDistance as 0
    if(startLocation == endLocation)
        return mapOf("distance" to 0, "path" to startLocation)

    //build the graph for given list of edges
    val graph = buildGraph(edges)

    //if either starting location or ending location does not exist in the graph then we can return with an error
    if(!graph.containsKey(startLocation) || ! graph.containsKey(endLocation))
        return if(!graph.containsKey(startLocation)) mapOf("distance" to -1,"error" to "cannot find $startLocation")
        else mapOf("distance" to -1,"error" to "cannot find $endLocation")

    val start = graph[startLocation]!!  //vertex for startLocation
    val end = graph[endLocation]!!      //vertex for endLocation
    start.minDistance = 0.0              //since the starting location is 0 distance from itself

    //min heap that is sorted based on minDistance
    val heap = PriorityQueue(graph.size, Comparator<Vertex> { a, b -> a.minDistance.compareTo(b.minDistance)})
    heap.offer(start)   //add start node to heap to begin

    while(!heap.isEmpty())
    {
        val cur = heap.poll()   //cur node will have the minDistance from start(i.e the shortest path from start to cur

        if(cur == end)  break   //if we reach the end then we can break since this will represent the shortest path from start to cur

        for( edge in cur.edges) //loop through all the edges starting from cur location
        {
            val neighbor = edge.destination
            //update neighboring locations if we can reach them more efficiently using the current location
            if(neighbor.minDistance > (edge.distance + cur.minDistance)) {
                neighbor.previous = cur
                neighbor.minDistance = edge.distance + cur.minDistance
                heap.offer(neighbor)
            }

        }
    }

    if(end.previous == null)    //if we cannot reach the end location from starting location
        return mapOf("distance" to -1, "error" to "cannot reach $endLocation from $startLocation")

    if(edges[0].getValue("distance") is Int)
        return mapOf("distance" to end.minDistance.toInt(), "path" to getShortestPath(end))
    else
        return mapOf("distance" to end.minDistance, "path" to getShortestPath(end))
    /*
    val result = mapOf("distance" to end.minDistance, "path" to getShortestPath(end))

    return result
    */
}
/**
 * Name         : buildGraph
 * Description  : builds a graph represented by a map whose key is the the location name
 *              :   and the value is a vertex object representing the location
 * Parameters   :
 *      "edges" : represents all edges in the graph
 * Returns      : a Map whose key is location name (string) and the value is its corresponding vertex object
 */
internal fun buildGraph(edges: List<Map<String, Any>>) : Map<String,Vertex>
{
    val graph = HashMap<String,Vertex>()
    //loop through all the edges in the input
    edges.forEach {
        //get source location, destination location and the weight for each edge
        val source = it.getValue("startLocation").toString()
        val destination = it.getValue("endLocation").toString()
        //val distance = if(it.getValue("distance") is Int) it.getValue("distance") as Int else it.getValue("distance") as Double
        val distance = it.getValue("distance").toString().toDouble()
        //add it to graph only if source and destination are not blank and its weight is positive
        if (source.length > 0 && destination.length > 0 && distance.compareTo(0) > 0) {
            graph.putIfAbsent(source,Vertex(source))
            graph.putIfAbsent(destination,Vertex(destination))
            val E = Edge(destination = graph[destination]!!, distance = distance)
            graph[source]!!.edges.add(E)
        }
    }
    return graph
}

/**
 * Name         : getShortestPath
 * Description  : populates the shortest path from startLocation to target
 *              :   Starting from the target (last location) we traverse back to the previous location in the shortest path
 *              :   (using the "previous" field in vertex) and finally return the reverse to get the shortest path
 * Parameters   :
 *      "target" : represents the endLocation vertex edges
 * Returns      : String representing the shortest path from start location to end location
 */
internal fun getShortestPath(target :Vertex?) : String
{
    val path = LinkedList<String>()
    //starting from the end traverse back to the start
    var location : Vertex? = target
    while(location != null)
    {
        path.add(location.toString())
        location = location.previous

    }
    //since we traverse starting the from the end and back we need to reverse it to get the shortest path from start to end
    path.reverse()

    return path.joinToString(" => ") // adds " ==> " as delimiter after each location in the return string
}