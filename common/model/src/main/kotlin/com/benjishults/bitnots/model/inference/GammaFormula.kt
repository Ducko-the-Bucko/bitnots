package com.benjishults.bitnots.model.inference

import com.benjishults.bitnots.model.formulas.Formula
import com.benjishults.bitnots.model.formulas.fol.VarBindingFormula
import com.benjishults.bitnots.model.terms.FV
import com.benjishults.bitnots.model.terms.FreeVariable
import com.benjishults.bitnots.model.unifier.EmptySub
import com.benjishults.bitnots.model.unifier.Sub
import com.benjishults.bitnots.model.unifier.Substitution

abstract class GammaFormula<F : VarBindingFormula>(formula: F, sign: Boolean) : SignedFormula<F>(formula, sign) {
    var numberOfApplications = 0
    override fun generateChildren(): List<SignedFormula<out Formula>> {
        val boundToFree = formula.variables.fold(EmptySub) { s: Substitution, t ->
            s.compose(Sub(t.to(
                    if (FreeVariable.exists(t.cons.name))
                        FreeVariable.new(t.cons.name)
                    else
                        FV(t.cons.name))))
        }
        return listOf(formula.formula.applySub(boundToFree).createSignedFormula(sign))
    }
}