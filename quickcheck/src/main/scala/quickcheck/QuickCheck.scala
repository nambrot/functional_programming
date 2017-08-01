package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    value <- arbitrary[Int]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(value, heap)
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int, b: Int) =>
    val heap = insert(b, insert(a, empty))
    findMin(heap) == (if (a > b) b else a)
  }

  property("removal") = forAll { (a: Int) => deleteMin(insert(a, empty)) == empty && isEmpty(deleteMin(insert(a, empty))) }

  property("sort") = forAll { (h: H, g: H) =>
    def isSortedAcc(h: H, acc: A): Boolean = h match {
      case empty => true
      case x => {
        val min = findMin(x)
        if (min < acc) false else isSortedAcc(deleteMin(x), acc)
      }
    }

    val min = findMin(meld(h,g))
    isSortedAcc(deleteMin(meld(h, g)), min)
  }

  property("meld") = forAll { (h: H, g: H) =>
    val minH = findMin(h)
    val minG = findMin(g)

    val cumMin = findMin(meld(h, g))

    cumMin == minH || cumMin == minG
  }

  property("removal leads to higher min") = forAll { (h: H, g: H) => findMin(meld(h, g)) <= findMin(deleteMin(meld(h, g))) }

  property("melding sequentially") = forAll { (h: H, g: H) =>
    val cumMin = findMin(meld(h, g))
    var testMin = true
    var iter = meld(h, g)

    while (!isEmpty(iter)) {
      val newMin = findMin(iter)
      testMin = testMin && newMin >= cumMin
      iter = deleteMin(iter)
    }

    testMin
  }

  property("insert") = forAll { (h: H, g: H) =>
    def toList(h: H) : List[A] = {
      if (isEmpty(h)) Nil else findMin(h) :: toList(deleteMin(h))
    }

    val list1 = toList(meld(h, g))
    val min = findMin(h)
    val list2 = toList(meld(deleteMin(h), insert(min, g)))

    list1 == list2
  }

}
