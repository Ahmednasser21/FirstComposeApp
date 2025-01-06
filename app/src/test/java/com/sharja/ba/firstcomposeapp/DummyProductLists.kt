package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.data.remote.RemoteProduct
import com.sharja.ba.firstcomposeapp.products.domain.Product

object DummyProductLists {
    fun getDummyRemoteProductList() = arrayListOf(
        RemoteProduct(0, "t0", "d0", "p0", "0", "b0", listOf("i0")),
        RemoteProduct(1, "t1", "d1", "p1", "1", "b1", listOf("i1")),
        RemoteProduct(2, "t2", "d2", "p2", "2", "b2", listOf("i2")),
        RemoteProduct(3, "t3", "d3", "p3", "3", "b3", listOf("i3")),
        RemoteProduct(4, "t4", "d4", "p4", "4", "b4", listOf("i4")),
        RemoteProduct(5, "t5", "d5", "p5", "5", "b5", listOf("i5")),
        RemoteProduct(6, "t6", "d6", "p6", "6", "b6", listOf("i6")),
        RemoteProduct(7, "t7", "d7", "p7", "7", "b7", listOf("i7")),
        )
    fun getDummyProductsList() = arrayListOf(
        Product(0,"t0","d0","p0","0","b0","i0",false),
        Product(1,"t1","d1","p1","1","b1","i1",false),
        Product(2,"t2","d2","p2","2","b2","i2",false),
        Product(3,"t3","d3","p3","3","b3","i3",false),
        Product(4,"t4","d4","p4","4","b4","i4",false),
        Product(5,"t5","d5","p5","5","b5","i5",false),
        Product(6,"t6","d6","p6","6","b6","i6",false),
        Product(7,"t7","d7","p7","7","b7","i7",false),

        )
}