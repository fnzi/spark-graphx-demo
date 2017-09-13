package cn.tj.fnzi.L


// Import random graph generation library

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.util.GraphGenerators

/**
  * Created by jack on 3/4/14.
  */
object Pregel {
  def main(args: Array[String]) {
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("Pregel").setMaster("local[4]")
    val sc = new SparkContext(conf)
    // A graph with edge attributes containing distances
    //初始化一个随机图，节点的度符合对数正态分布,边属性初始化为1
    val graph: Graph[Long, Double] =
    GraphGenerators.logNormalGraph(sc, numVertices = 5).mapEdges(e => e.attr.toDouble)
    graph.edges.foreach(println)
    val sourceId: VertexId = 4 // The ultimate source

    // Initialize the graph such that all vertices except the root have distance infinity.
    //初始化各节点到原点的距离
    val initialGraph = graph.mapVertices((id, _) => if (id == sourceId) 0.0 else Double.PositiveInfinity)

    val sssp = initialGraph.pregel(Double.PositiveInfinity)(
      // Vertex Program，节点处理消息的函数，dist为原节点属性（Double），newDist为消息类型（Double）
      (id, dist, newDist) => math.min(dist, newDist),

      // Send Message，发送消息函数，返回结果为（目标节点id，消息（即最短距离））
      triplet => {
        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
        } else {
          Iterator.empty
        }
      },
      //Merge Message，对消息进行合并的操作，类似于Hadoop中的combiner
      (a, b) => math.min(a, b)
    )

    println(sssp.vertices.collect.mkString("\n"))
  }
}