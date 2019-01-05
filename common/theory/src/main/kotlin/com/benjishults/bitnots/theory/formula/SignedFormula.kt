package com.benjishults.bitnots.theory.formula

import com.benjishults.bitnots.model.formulas.Formula
import java.lang.reflect.Constructor

// NOTE these should be immutable
abstract class SignedFormula<out F : Formula<*>>(val formula: F, val sign: Boolean = false) {

    abstract fun generateChildren(): List<SignedFormula<Formula<*>>>

    override fun toString() = (if (sign) "Suppose: " else "Show: ") + formula

    override fun equals(other: Any?): Boolean {
        if (other === null)
            return false
        if (other::class === this::class)
            return (other as SignedFormula<*>).formula == formula
        return false
    }
}

/**
 * For each concrete subtype "T" of Formula, there must be subclasses of SignedFormula with names NegativeT and PositiveT.
 * These SignedFormula classes must have a constructor that takes no arguments or one that takes a single Formula argument.
 */
fun <T : Formula<*>> T.createSignedFormula(sign: Boolean = false): SignedFormula<T> {
    var leastParameters = Int.MAX_VALUE
    var const: Constructor<out SignedFormula<T>>? = null
    val clazz = Class.forName("com.benjishults.bitnots.inference.rules.concrete.${if (sign) "Positive" else "Negative"}${this::class.simpleName}") // as Class<out SignedFormula<T>>
    @Suppress("UNCHECKED_CAST")
    for (constructor in clazz.constructors as Array<Constructor<out SignedFormula<T>>>) {
        if (leastParameters > constructor.parameterCount) {
            leastParameters = constructor.parameterCount
            const = constructor //  as Constructor<out SignedFormula<T>>
        }
    }
    @Suppress("UNCHECKED_CAST")
    when (leastParameters) {
        0 -> return const?.newInstance() ?: throw ClassNotFoundException()
        1 -> return const?.newInstance(this) ?: throw ClassNotFoundException()
        else -> return clazz.kotlin.objectInstance as SignedFormula<T>
    }
}