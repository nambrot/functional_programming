import forcomp.Anagrams._

val string = "string"
//string.toList.groupBy(x=>x).toList.map(((char: Char, charList: List[Char])) => (char, charList.length)  )
string.toList.groupBy(x=>x).toList.map(x => (x._1, x._2.length))
string.toList.groupBy(x=>x).toList.map{case (x, y) => (x, y.length)}
Map('s' -> List('s'), 'n' -> List('n')).toList
sentenceAnagrams(List("linux"))