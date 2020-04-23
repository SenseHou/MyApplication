package com.example.myapplication.homework.april21

fun main() {

    val order1 = arrayOf("ITEM0001 x 1", "ITEM0013 x 2", "ITEM0022 x 1")
    val order2 = arrayOf("ITEM0013 x 4", "ITEM0022 x 1")
    val order3 = arrayOf("ITEM0013 x 4")
    bestCharge(order1);
    println() ;println();println()
    bestCharge(order2);
    println() ;println();println()
    bestCharge(order3);

}

private fun bestCharge(orders: Array<String>) {
    val orderDetailList = analysisOrder(orders)
    val discount1 = discount1(orderDetailList)
    val discount2 = discount2(orderDetailList)
    if (discount1.first > discount2.first) {
        printLog(orderDetailList, SalesPromotionEnum.DISCOUNT2, discount2)
    } else {
        printLog(orderDetailList, SalesPromotionEnum.DISCOUNT1, discount1)
    }

}

private fun analysisOrder(orders: Array<String>): Array<OrderDetail?> {

    val orderDetailList = arrayOfNulls<OrderDetail>(orders.size)
    for ((index, order) in orders.withIndex()) {
        val item = order.split(" x ")[0]
        val count = order.split(" x ")[1].toInt()
        orderDetailList[index] =
            OrderDetail(
                GoodsItemEnum.valueOf(item).id,
                count,
                GoodsItemEnum.valueOf(item).price
            )
    }
    return orderDetailList
}


private fun printLog(
    orderDetailList: Array<OrderDetail?>,
    salesPromotion: SalesPromotionEnum,
    pricePair: Pair<Int, Int>
) {
    println("=============订餐明细=============")
    for (order in orderDetailList) {
        if (order != null) {
            println(order.item + " x " + order.count + " = " + order.count * order.price)
        }
    }
    println("---------------------------------")
    if (pricePair.second != 0) {
        println("使用优惠：")
        println(salesPromotion.info + "，省" + pricePair.second)
        println("---------------------------------")
    }
    println("总计：" + pricePair.first + "元")
    println("=================================")
}

fun discount1(orders: Array<OrderDetail?>): Pair<Int, Int> {
    val halfGoods = arrayOf(GoodsItemEnum.ITEM0001.id, GoodsItemEnum.ITEM0022.id);
    var sumPrice = 0
    var defaultPrice = 0
    for (order in orders) {
        if (order != null) {
            defaultPrice += order.price * order.count
            sumPrice += if (halfGoods.contains(order.item)) {
                order.price * order.count / 2
            } else {
                order.price * order.count
            }
        }
    }
    return Pair(sumPrice, defaultPrice - sumPrice)
}

fun discount2(orders: Array<OrderDetail?>): Pair<Int, Int> {
    var sumPrice = 0
    for (order in orders) {
        if (order != null) {
            sumPrice += order.price * order.count
        }
    }
    return if (sumPrice > 30) Pair(sumPrice - 6, 6) else Pair(sumPrice, 0)
}