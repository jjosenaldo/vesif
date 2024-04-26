package org.example.core.model.examples

import org.example.core.model.*

val componentsExample01: List<Component> by lazy {
    val p1 = Pole(isPositive = true, id = "P1", isLeft = true, neighbor = Component.DEFAULT)
    val r1 = MonostableRelay(
        leftNeighbor = Component.DEFAULT,
        rightNeighbor = Component.DEFAULT,
        contacts = listOf(),
        id = "R1"
    )
    val c2 = RelayRegularContact(
        isNormallyOpen = true,
        controller = RelayContactController.DEFAULT,
        leftNeighbor = Component.DEFAULT,
        rightNeighbor = Component.DEFAULT,
        id = "C2"
    )
    val n1 = Pole(isPositive = false, id = "N1", isLeft = false, neighbor = Component.DEFAULT)
    val p2 = Pole(isPositive = true, id = "P2", isLeft = true, neighbor = Component.DEFAULT)
    val c1 = RelayRegularContact(
        isNormallyOpen = false,
        controller = RelayContactController.DEFAULT,
        leftNeighbor = Component.DEFAULT,
        rightNeighbor = Component.DEFAULT,
        id = "C1"
    )
    val r2 = MonostableRelay(
        leftNeighbor = Component.DEFAULT,
        rightNeighbor = Component.DEFAULT,
        contacts = listOf(),
        id = "R2"
    )
    val b1 = Button(leftNeighbor = Component.DEFAULT, rightNeighbor = Component.DEFAULT, id = "B1")
    val n2 = Pole(isPositive = false, id = "N2", isLeft = false, neighbor = Component.DEFAULT)

    listOf(
        p1.copyWith(neighbor = r1),
        r1.copyWith(leftNeighbor = p1, rightNeighbor = c2, contacts = listOf(c1)),
        c2.copyWith(leftNeighbor = r1, rightNeighbor = n1, controller = r2),
        n1.copyWith(neighbor = c2),
        p2.copyWith(neighbor = c1),
        c1.copyWith(leftNeighbor = p2, rightNeighbor = r2, controller = r1),
        r2.copyWith(leftNeighbor = c1, rightNeighbor = b1, contacts = listOf(c2)),
        b1.copyWith(leftNeighbor = r2, rightNeighbor = n2),
        n2.copyWith(neighbor = b1)
    )
}