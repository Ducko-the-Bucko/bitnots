package com.benjishults.bitnots.model.unifier

import com.benjishults.bitnots.model.terms.Term
import com.benjishults.bitnots.model.terms.TermConstructor
import com.benjishults.bitnots.model.terms.Variable
import kotlin.text.String

sealed class Substitution {

    abstract fun compose(other: Substitution): Substitution
    abstract fun <C : TermConstructor> applyToVar(v: Variable<C>): Term<*>

    abstract override fun equals(other: Any?): Boolean

    abstract override fun toString(): String
}

/**
 * The result of an attempted unification of un-unifiable terms.
 */
object NotUnifiable : Substitution() {

    override fun <C : TermConstructor> applyToVar(v: Variable<C>) = error("Attempt made to apply a non-existent substitution.")

    override fun compose(other: Substitution): Substitution = this

    override fun equals(other: Any?): Boolean = other === this
    override fun toString(): String = "\u22A5"
}

object EmptySub : Substitution() {

    override fun <C : TermConstructor> applyToVar(v: Variable<C>) = v;

    override fun compose(other: Substitution) = other

    override fun equals(other: Any?): Boolean = this === other
    override fun toString(): String = "{}"

}

class Sub private constructor(private val map: Map<Variable<*>, Term<*>>) : Substitution() {

    constructor(vararg pairs: Pair<Variable<*>, Term<*>>) : this(mapOf(*pairs))

    override fun <C : TermConstructor> applyToVar(v: Variable<C>): Term<*> {
        map.get(v)?.let {
            return it
        } ?: return v
    }

    override fun compose(other: Substitution): Substitution {
        return when (other) {
            NotUnifiable -> NotUnifiable
            EmptySub -> this
            is Sub -> {
                val keys = map.keys.toSet()
                val newMap = mutableMapOf<Variable<*>, Term<*>>()

                map.entries.map { (v, term) ->
                    // apply arg to value in receiver
                    term.applySub(other).let {
                        if (it !== v)
                            newMap.put(v, it)
                        else
                            newMap.remove(v)
                    }
                }

                other.map.entries.map { (v, term) ->
                    // if arg covers more variables, add them
                    if (!keys.contains(v)) {
                        newMap.put(v, term)
                    }
                }

                Sub(newMap)
            }
        }
    }

    override fun equals(other: Any?): Boolean // = this === other
    {
        // return true if each is more general than the other... i.e. if they are *equivalent*.
        other?.let {
            return (other is Sub &&
                    other.map.size == map.size &&
                    other.map.keys.containsAll(map.keys) &&
                    other.map.entries.all { map.get(it.key) == it.value })
        }
        return false
    }

    override fun toString(): String =
            "{" + map.entries.fold(mutableListOf<String>())
            {
                s, t ->
                s.also { it.add("${t.value}/${t.key}") }
            }.joinToString(", ") + "}"

}
