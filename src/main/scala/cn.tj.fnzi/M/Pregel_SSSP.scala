package cn.tj.fnzi.M

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Pregel_SSSP {
  def main(args: Array[String]) {
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    Logger.getLogger("org.apache.spark.graphx").setLevel(Level.DEBUG)

    val conf = new SparkConf().setAppName("Pregel_SSSP").setMaster("local[4]")
    val sc = new SparkContext(conf)
    // A graph with edge attributes containing distances
    //    val graph: Graph[Long, Double] =
    //      GraphGenerators.logNormalGraph(sc, numVertices = 5).mapEdges(e => e.attr.toDouble)

    //读入数据文件
    val articles: RDD[String] = sc.textFile("data/graphx/point.txt")
    val links: RDD[String] = sc.textFile("data/graphx/path.txt")

    //装载顶点和边
    val vertices = articles.map { line =>
      val fields = line.split(' ')
      (fields(0).toLong, (fields(1), fields(2)))
    }


    val edges = links.map { line =>
      val fields = line.split(' ')
      Edge(fields(0).toLong, fields(1).toLong, fields(2).toDouble)
    }

    //cache操作
    //val graph = Graph(vertices, edges, "").persist(StorageLevel.MEMORY_ONLY_SER)
    val graph = Graph(vertices, edges, ("", ""))


//    graph.vertices.foreach(println)
//    graph.edges.foreach(println)
    println("=========================")
    //    graph.edges.foreach(println)
    val sourceId: VertexId = 1 // The ultimate source

    // Initialize the graph such that all vertices except the root have distance infinity.
    val initialGraph: Graph[(Double, List[VertexId]), Double] = graph.mapVertices((id, _) =>
      if (id == sourceId) (0.0, List[VertexId](sourceId))
      else (Double.PositiveInfinity, List[VertexId]()))

    initialGraph.vertices.foreach(println)

    val sssp = initialGraph.pregel((Double.PositiveInfinity, List[VertexId]()), Int.MaxValue, EdgeDirection.Out)(

      // Vertex Program
      (id, dist, newDist) => if (dist._1 < newDist._1) dist else newDist,

      // Send Message
      triplet => {
        if (triplet.srcAttr._1 < triplet.dstAttr._1 - triplet.attr) {
          Iterator((triplet.dstId, (triplet.srcAttr._1 + triplet.attr, triplet.srcAttr._2 :+ triplet.dstId))) //  :+ triplet.dstId
        } else {
          Iterator.empty
        }
      },
      //Merge Message
      (a, b) => if (a._1 < b._1) a else b)
    println(sssp.vertices.collect.mkString("\n"))
  }
}
