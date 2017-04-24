package Dijkstra

/**
 * Created by kirankrishnamurthy on 4/23/17.
 */
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FunSpec

class dijkstraTest : FunSpec(){
    init {
        test("Starting location is blank or it does not exist in the graph") {
            var startLocation = "Mike's digs"
            val endLocation = "Craig's haunt"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Craig's haunt", "distance" to 19),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Craig's haunt", "distance" to 8)
            )
            var expectedResult = mapOf("distance" to -1,"error" to "cannot find $startLocation")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult

            startLocation = ""
            expectedResult = mapOf("distance" to -1,"error" to "Starting and Ending locations must be specified")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Ending location is blank or it does not exist in the graph") {
            val startLocation = "Kruthika's abode"
            var endLocation = "Mike's digs"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Craig's haunt", "distance" to 19),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Craig's haunt", "distance" to 8)
            )
            var expectedResult = mapOf("distance" to -1,"error" to "cannot find $endLocation")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult

            endLocation = ""
            expectedResult = mapOf("distance" to -1,"error" to "Starting and Ending locations must be specified")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Starting location is same as Ending location"){
            val startLocation = "Kruthika's abode"
            val endLocation = "Kruthika's abode"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Craig's haunt", "distance" to 19),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Craig's haunt", "distance" to 8)
            )
            val expectedResult = mapOf("distance" to 0, "path" to "$endLocation")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Ending location cannot be reached from Starting location") {
            val startLocation = "Kruthika's abode"
            val endLocation = "Nathan's flat"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Craig's haunt", "distance" to 19),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Craig's haunt", "distance" to 8),
                    mapOf("startLocation" to "Mike's digs", "endLocation" to "Nathan's flat", "distance" to 12)

            )
            val expectedResult = mapOf("distance" to -1, "error" to "cannot reach $endLocation from $startLocation")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Graph with exactly two nodes and one edge (from start to end location"){
            val startLocation = "Kruthika's abode"
            val endLocation = "Mark's crib"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9)
            )
            val expectedResult = mapOf("distance" to 9, "path" to "Kruthika's abode => Mark's crib")
            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Sample input with integer values for distance"){
            val startLocation = "Kruthika's abode"
            val endLocation = "Craig's haunt"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Matt's pad", "distance" to 18),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Brian's apartment", "distance" to 8),
                    mapOf("startLocation" to "Brian's apartment", "endLocation" to "Wesley's condo", "distance" to 7),
                    mapOf("startLocation" to "Brian's apartment", "endLocation" to "Cam's dwelling", "distance" to 17),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Cam's dwelling", "distance" to 13),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Mike's digs", "distance" to 19),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Matt's pad", "distance" to 14),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Kirk's farm", "distance" to 10),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Nathan's flat", "distance" to 11),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Bryce's den", "distance" to 6),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Mark's crib", "distance" to 19),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Nathan's flat", "distance" to 15),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Craig's haunt", "distance" to 14),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Kirk's farm", "distance" to 9),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Nathan's flat", "distance" to 12),
                    mapOf("startLocation" to "Bryce's den", "endLocation" to "Craig's haunt", "distance" to 10),
                    mapOf("startLocation" to "Bryce's den", "endLocation" to "Mike's digs", "distance" to 9),
                    mapOf("startLocation" to "Mike's digs", "endLocation" to "Cam's dwelling", "distance" to 20),
                    mapOf("startLocation" to "Mike's digs", "endLocation" to "Nathan's flat", "distance" to 12),
                    mapOf("startLocation" to "Cam's dwelling", "endLocation" to "Craig's haunt", "distance" to 18),
                    mapOf("startLocation" to "Nathan's flat", "endLocation" to "Kirk's farm", "distance" to 3)
            )

            val expectedResult = mapOf("distance" to 31, "path" to
                    "Kruthika's abode => Brian's apartment => Wesley's condo => Bryce's den => Craig's haunt")

            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }

        test("Sample input with decimal values for distance"){
            val startLocation = "Kruthika's abode"
            val endLocation = "Craig's haunt"
            val edges = listOf(
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Mark's crib", "distance" to 9.1),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Greg's casa", "distance" to 4.1),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Matt's pad", "distance" to 18.1),
                    mapOf("startLocation" to "Kruthika's abode", "endLocation" to "Brian's apartment", "distance" to 8.1),
                    mapOf("startLocation" to "Brian's apartment", "endLocation" to "Wesley's condo", "distance" to 7.1),
                    mapOf("startLocation" to "Brian's apartment", "endLocation" to "Cam's dwelling", "distance" to 17.1),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Cam's dwelling", "distance" to 13.1),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Mike's digs", "distance" to 19.1),
                    mapOf("startLocation" to "Greg's casa", "endLocation" to "Matt's pad", "distance" to 14.1),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Kirk's farm", "distance" to 10.1),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Nathan's flat", "distance" to 11.1),
                    mapOf("startLocation" to "Wesley's condo", "endLocation" to "Bryce's den", "distance" to 6.1),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Mark's crib", "distance" to 19.1),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Nathan's flat", "distance" to 15.1),
                    mapOf("startLocation" to "Matt's pad", "endLocation" to "Craig's haunt", "distance" to 14.1),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Kirk's farm", "distance" to 9.1),
                    mapOf("startLocation" to "Mark's crib", "endLocation" to "Nathan's flat", "distance" to 12.1),
                    mapOf("startLocation" to "Bryce's den", "endLocation" to "Craig's haunt", "distance" to 10.1),
                    mapOf("startLocation" to "Bryce's den", "endLocation" to "Mike's digs", "distance" to 9.1),
                    mapOf("startLocation" to "Mike's digs", "endLocation" to "Cam's dwelling", "distance" to 20.1),
                    mapOf("startLocation" to "Mike's digs", "endLocation" to "Nathan's flat", "distance" to 12.1),
                    mapOf("startLocation" to "Cam's dwelling", "endLocation" to "Craig's haunt", "distance" to 18.1),
                    mapOf("startLocation" to "Nathan's flat", "endLocation" to "Kirk's farm", "distance" to 3.1)
            )

            val expectedResult = mapOf("distance" to 31.4, "path" to
                    "Kruthika's abode => Brian's apartment => Wesley's condo => Bryce's den => Craig's haunt")

            dijkstra(startLocation, endLocation, edges) shouldBe expectedResult
        }
    }
}