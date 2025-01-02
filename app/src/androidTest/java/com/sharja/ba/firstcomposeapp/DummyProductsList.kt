package com.sharja.ba.firstcomposeapp

import com.sharja.ba.firstcomposeapp.products.domain.Product

object DummyProductsList {
   fun getDummyProductsList() = arrayListOf(
        Product(0,"t0","d0","p0","r0","b0","i0",false),
        Product(1,"t1","d1","p1","r1","b1","i1",false),
        Product(2,"t2","d2","p2","r2","b2","i2",true),
        Product(3,"t3","d3","p3","r3","b3","i3",false),
        Product(4,"t4","d4","p4","r4","b4","i4",false),
        Product(5,"t5","d5","p5","r5","b5","i5",true),
        Product(6,"t6","d6","p6","r6","b6","i6",false),
        Product(7,"t7","d7","p7","r7","b7","i7",false),

    )
}