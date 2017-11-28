package scala

import java.util

/**
  * This class processes the data , which was extracted from xml and produce a
  * list which can be used to store as data frame
  *
  * @author Debasish.chakrabarty
  *
  */
class DataProcessor {

  def processForInsert(resultMap: util.Map[String, util.List[String]]): Unit = {
    val keyset = resultMap.keySet // get the keyset  they will be used as column names

    val headerList = getHeaderList(keyset)


    // get the length of any list in resultmap
    val valueList = getDataList(resultMap, keyset, headerList)


    ////  print part

    import scala.collection.JavaConversions._
    for (header <- headerList) {
      System.out.print(header + " , ")
    }
    System.out.println("-------------------------------------------------------------")

    for (util.List < String > values
    : valueList
    )
    {
      // each row
      for (String value
      : values
      )
      {
        System.out.print(value + " , ")
      }
      System.out.println("")
    }

    private def getDataList(resultMap: util.Map[String, util.List[String]], keyset: util.Set[String],
                            headerList: util.List[String]): util.List[util.List[String]] = {
      val valueList = new util.LinkedList[util.List[String]]

      val length = getLengthOfData(headerList, resultMap)
      var i = 0
      while ( {
        i < length
      }) {
        val row_data = new util.LinkedList[String]
        import scala.collection.JavaConversions._
        for (key <- keyset) {
          val values = resultMap.get(key)
          row_data.add(values.get(i))
        }
        valueList.add(row_data)

        {
          i += 1; i - 1
        }
      }
      return valueList
    }
  }

  def getLengthOfData(headerList: util.List[String], resultMap: util.Map[String, util.List[String]]): Int = {
    // get the first element of the keyset
    //find the length of the value list
    val value = resultMap.get(headerList.get(0))

    return value.size
  }

  def getHeaderList(keyset: util.Set[String]): util.List[String] = {
    val headerList = new util.LinkedList[String]

    import scala.collection.JavaConversions._
    for (key <- keyset) {
      headerList.add(key)
    }
    return headerList
  }
}