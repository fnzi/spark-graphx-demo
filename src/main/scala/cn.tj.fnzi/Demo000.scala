package cn.tj.fnzi

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD

object Demo000 {
  def main(args: Array[String]): Unit = {
    val userGraph: Graph[(String, String), String] = null

    //--------------------------------

    // Assume the SparkContext has already been constructed
    val sc: SparkContext = null

    // Create an RDD for the vertices
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
        (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))

    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))

    // my test {
    val relationships1: RDD[Edge[(String, String)]] =
      sc.parallelize(Array(Edge(3L, 7L, ("collab","1"))))

    relationships1.take(0).head.attr._1
    //}

    // Define a default user in case there are relationship with missing user
    val defaultUser = ("John Doe", "Missing")

    // Build the initial Graph
    val graph = Graph(users, relationships, defaultUser)

    // my test {
    graph.vertices.take(0).head._2._1
    //}

    val graph1: Graph[(String, String), String] = null // Constructed from above
    // Count all users which are postdocs
    graph1.vertices.filter { case (id, (name, pos)) => pos == "postdoc" }.count
    // Count all the edges where src > dst
    graph1.edges.filter(e => e.srcId > e.dstId).count



  }
}
