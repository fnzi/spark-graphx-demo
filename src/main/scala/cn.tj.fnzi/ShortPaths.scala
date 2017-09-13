package cn.tj.fnzi

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.graphx.lib.ShortestPaths
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

import scala.reflect.ClassTag

object ShortPaths {

  def main(args: Array[String]): Unit = {
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //设置运行环境
    val conf = new SparkConf().setAppName("PageRankAboutBerkeleyWiki").setMaster("local")
    val sc = new SparkContext(conf)

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
      Edge(fields(0).toLong, fields(1).toLong, fields(2).toLong)
    }

    //cache操作
    //val graph = Graph(vertices, edges, "").persist(StorageLevel.MEMORY_ONLY_SER)
    val graph = Graph(vertices, edges, ("_", "_"))
    //graph.unpersistVertices(false)

    // 要求最短路径的点集合
    val landmarks = Seq(1).map(_.toLong)

    // 计算最短路径

//    val shortestPath1 = ShortestPaths.run(graph, landmarks)
    val shortestPath1 = dijkstra(graph, 4L)

    // 与真实结果对比
    println("\ngraph edges");
    println("edges:");
    graph.edges.collect.foreach(println)
    println("vertices:");
    graph.vertices.collect.foreach(println)
    println();

    println("\n shortestPath1");
    println("edges:");
    shortestPath1.edges.collect.foreach(println)
    println("vertices:");
    shortestPath1.vertices.collect.foreach(println)
    println("end");

    sc.stop()
  }

  def dijkstra[VD, ED: ClassTag](g: Graph[VD, ED], origin: VertexId) = {
    var g2 = g.mapVertices((vid, vd) => (false, if (vid == origin) 0 else Double.MaxValue)) //修改点的属性，如果是特殊顶点属性为0，其他为无穷，false标志着是否被访问过
    //每次循环都会访问一个点并标记为true，共循环vertices.count-1次（特定节点除外）
    for (i <- 1L to g.vertices.count - 1) {
      val currentVertexId =
        g2.vertices.filter(!_._2._1)
          .fold((0L, (false, Double.MaxValue)))((a, b) =>
            if (a._2._2 < b._2._2) a else b)._1

      //迭代每一个顶点，取出所有顶点的属性最小值（路径长度），currentVertexId,是属性最小值的顶点ID
      val newDistances = g2.aggregateMessages[Double](
        ctx => if (ctx.srcId == currentVertexId) //找到srcId是最小属性的那条关系
          ctx.sendToDst(ctx.attr.##), //把最小路径和边的路径合在一起发给dstId
        (a, b) => math.min(a, b)) //收到消息取最小

      g2 = g2.outerJoinVertices(newDistances)((vid, vd, newSum) =>
        (vd._1 || vid == currentVertexId, math.min(vd._2, newSum.getOrElse(Double.MaxValue))))
      //这里走过的点这设为true，把每个点的路径长度属性更新
    }
    //g2里面存的都是每个顶点到指定顶点的最小距离
    g.outerJoinVertices(g2.vertices)((vid, vd, dist) =>
      (vd, dist.getOrElse((false, Double.MaxValue))._2))
  }
}

